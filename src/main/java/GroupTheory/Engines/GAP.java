package GroupTheory.Engines;

import GroupTheory.Structs.*;
import Parser.NestedParser;
import Parser.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GAP implements GroupTheoryEngine {
    private final boolean log;
    private final Process process;
    private final BufferedReader in;
    private final PrintWriter out;

    private static final String initialPrompt = " Try";
    private static final String outFormat = "SetPrintFormattingStatus(\"*stdout*\", false);;";

    private static final Pattern generatorPattern = Pattern.compile("\\([()\\d,]+\\)"), cyclePattern = Pattern.compile("\\([\\d,]+\\)"), digitPattern = Pattern.compile("\\d+");

    public GAP(String location, boolean log) {
        if (!location.endsWith("/")) {
            location += '/';
        }
        location += "gap";
        this.log = log;

        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(location);
            process = processBuilder.start();
            in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            out = new PrintWriter(process.getOutputStream());

            // read initial input
            String line = in.readLine();
            while (!line.startsWith(initialPrompt)) {
                line = in.readLine();
            }
            // don't break GAP output into multiple lines
            out.println(outFormat);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public GAP() {
        this(System.getenv("GAP_HOME"), false);
    }

    private String read() {
        try {
            String line = in.readLine();
            while (line.startsWith("gap> ")) {
                line = line.substring(5);
            }
            // TODO: catch error
            return line;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void write(String s) {
        if (log) {
            System.out.println("GAP: " + s);
        }
        out.println(s);
        out.flush();
    }

    private boolean readBoolean() {
        return read().equals("true");
    }

    private static Domain parseDomain(Node node) {
        List<Tuple> a = new ArrayList<>();

        for (Node child : node) {
            List<Integer> tuple = new ArrayList<>();
            for (Node x : child) {
                tuple.add(x.getValue());
            }
            a.add(new Tuple(tuple));
        }

        return new ExplicitDomain(a);
    }

    @Override
    public long getOrder(Group g) {
        write("Order(" + g.toString() + ");");
        return Long.parseLong(read());
    }

    @Override
    public boolean isMember(Permutation p, Group g) {
        write(p.toString() + " in " + g.toString() + ';');
        return Boolean.parseBoolean(read());
    }

    @Override
    public boolean isTransitive(Group g, Domain d) {
        write("IsTransitive(" + g.toString() + ", " + d.toString() + ", OnSets);");
        return readBoolean();
    }

    @Override
    public List<Domain> getOrbits(Group g, Domain d) {
        write("OrbitsDomain(" + g.toString() + ", " + d.toString() + ", OnSets);");

        List<Domain> orbits = new ArrayList<>();
        Node tree = NestedParser.parse(read());
        for (Node child : tree) {
            orbits.add(parseDomain(child));
        }

        return orbits;
    }

    private Group getStabilizer(Group g, Domain d, String action) {
        write("Stabilizer(" + g.toString() + ", " + d.toString() + ", " + action + ");");

        List<Permutation> generators = new ArrayList<>();
        String s = read();
        Matcher generatorMatcher = generatorPattern.matcher(s);
        while (generatorMatcher.find()) {
            List<Cycle> cycles = new ArrayList<>();
            Matcher cycleMatcher = cyclePattern.matcher(generatorMatcher.group());
            while (cycleMatcher.find()) {
                List<Integer> cycle = new ArrayList<>();
                Matcher digitMatcher = digitPattern.matcher(cycleMatcher.group());
                while (digitMatcher.find()) {
                    cycle.add(Integer.parseInt(digitMatcher.group()));
                }
                cycles.add(new Cycle(cycle));
            }
            generators.add(new Permutation(cycles));
        }

        return new Group(generators);
    }

    @Override
    public Group getPointwiseStabilizer(Group g, Domain d) {
        return getStabilizer(g, d, "OnTuplesSets");
    }

    private Group getSetwiseStabilizer(Group g, Domain d) {
        return getStabilizer(g, d, "OnSetsSets");
    }

    @Override
    public Group getMinimalBlockSystemStabilizer(Group g, Domain d) {
        if (!isTransitive(g, d)) {
            throw new RuntimeException();
        }

        write("MaximalBlocks(" + g.toString() + ", " + d.toString() + ", OnSets);");

        Node tree = NestedParser.parse(read());
        for (Node child : tree) {
            g = getSetwiseStabilizer(g, parseDomain(child));
        }

        return g;
    }

    public void close() {
        try {
            in.close();
            out.close();
            process.destroy();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
