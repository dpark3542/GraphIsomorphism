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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GAP implements GroupTheoryEngine {
    private final boolean log;
    private final Process process;
    private final BufferedReader in;
    private final PrintWriter out;

    private static final String initialPrompt = " Try";
    private static final String outFormat = "SetPrintFormattingStatus(\"*stdout*\", false);;";

    private static final Pattern permutationPattern = Pattern.compile("\\([()\\d,]+\\)"), cyclePattern = Pattern.compile("\\([\\d,]+\\)"), digitPattern = Pattern.compile("\\d+");

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

    private void write(String s) {
        if (log) {
            System.out.println("GAP: " + s);
        }
        out.println(s);
        out.flush();
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

    private boolean readBoolean() {
        return Boolean.parseBoolean(read());
    }

    private int readInt() {
        return Integer.parseInt(read());
    }

    private Permutation parsePermutation(String t) {
        if (t.equals("()")) {
            return new Permutation();
        }
        else {
            List<Cycle> cycles = new ArrayList<>();
            Matcher cycleMatcher = cyclePattern.matcher(t);
            while (cycleMatcher.find()) {
                List<Integer> cycle = new ArrayList<>();
                Matcher digitMatcher = digitPattern.matcher(cycleMatcher.group());
                while (digitMatcher.find()) {
                    cycle.add(Integer.parseInt(digitMatcher.group()));
                }
                cycles.add(new Cycle(cycle));
            }
            return new Permutation(cycles);
        }
    }

    private List<Permutation> parsePermutations(String s) {
        List<Permutation> permutations = new ArrayList<>();
        Matcher permutationMatcher = permutationPattern.matcher(s);
        while (permutationMatcher.find()) {
            permutations.add(parsePermutation(permutationMatcher.group()));
        }

        return permutations;
    }

    private Group readGroup() {
        String s = read();
        if (s.equals("Group(())")) {
            return new Group();
        }
        // Group([ perm, ..., perm ])
        else {
            return new Group(parsePermutations(s));
        }
    }

    @Override
    public Permutation multiply(Permutation p, Permutation q) {
        write(p.toString() + '*' + q.toString());
        return parsePermutation(read());
    }

    @Override
    public Permutation invert(Permutation p) {
        write(p.toString() + "^-1");
        return parsePermutation(read());
    }

    @Override
    public int getOrder(Group g) {
        write("Order(" + g.toString() + ");");
        return readInt();
    }

    @Override
    public boolean isMember(Permutation p, Group g) {
        write(p.toString() + " in " + g.toString() + ';');
        return readBoolean();
    }

    @Override
    public boolean isTransitive(Group g, Domain d) {
        write("IsTransitive(" + g.toString() + ", " + d.toString() + ", OnSets);");
        return readBoolean();
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

        return readGroup();
    }

    @Override
    public Group getPointwiseStabilizer(Group g, Domain d) {
        return getStabilizer(g, d, "OnTuplesSets");
    }

    public Group getSetwiseStabilizer(Group g, Domain d) {
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

    public boolean isSubgroup(Group g, Group h) {
        write("IsSubgroup(" + g.toString() + ", " + h.toString() + ");");
        return readBoolean();
    }

    @Override
    public List<Permutation> getTransversal(Group g, Group h) {
        if (!isSubgroup(g, h)) {
            throw new RuntimeException();
        }

        write("Elements(RightTransversal(" + g.toString() + ", " + h.toString() + "));");

        return parsePermutations(read());
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
