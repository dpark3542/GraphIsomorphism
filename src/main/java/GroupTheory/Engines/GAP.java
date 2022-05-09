package GroupTheory.Engines;

import GroupTheory.Structs.*;
import Parser.NestedParser;
import Parser.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GAP implements GroupTheoryEngine {
    private final boolean log;
    private final Process process;
    private final BufferedReader in;
    private final PrintWriter out;

    private static final String initialPrompt = " Try";
    private static final String outFormat = "SetPrintFormattingStatus(\"*stdout*\", false);;";

    public GAP(String location, boolean log) {
        try {
            this.log = log;
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

    public GAP(String location) {
        this(location, false);
    }

    private String read() {
        try {
            String line = in.readLine();
            while (line.startsWith("gap> ")) {
                line = line.substring(5);
            }
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
    public List<Domain> getOrbits(Group g, Domain d) {
        write("OrbitsDomain(" + g.toString() + ", " + d.toString() + ", OnSets);");

        List<Domain> orbits = new ArrayList<>();
        Node tree = NestedParser.parse(read());
        for (Node domain : tree) {
            orbits.add(NestedParser.parseDomain(domain));
        }

        return orbits;
    }

    @Override
    public Group getPointwiseStabilizer(Group g, Domain d) {
        return null;
    }

    @Override
    public Domain getMinimalBlockSystem(Group g, Domain d) {
        return null;
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
