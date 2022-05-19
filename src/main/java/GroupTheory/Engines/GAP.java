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
    private final boolean log, performance;
    private final Process process;
    private final BufferedReader in;
    private final PrintWriter out;

    private static final String outFormat = "SetPrintFormattingStatus(\"*stdout*\", false);;";

    private static final Pattern permutationPattern = Pattern.compile("(\\([()\\d,]+\\)|\\(\\))"), cyclePattern = Pattern.compile("\\([\\d,]+\\)"), digitPattern = Pattern.compile("\\d+");

    public GAP(String location, boolean log, boolean performance) {
        if (location == null) {
            throw new IllegalArgumentException();
        }
        if (!location.endsWith("/")) {
            location += '/';
        }
        location += "gap";
        this.log = log;

        String[] args = {location, "-q"};

        this.performance = performance;

        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(args);
            process = processBuilder.start();
            in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            out = new PrintWriter(process.getOutputStream());

            // don't break GAP output into multiple lines
            out.println(outFormat);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public GAP(boolean log) {
        this(System.getenv("GAP_HOME"), log, false);
    }

    public GAP() {
        this(System.getenv("GAP_HOME"), false, true);
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
            // TODO: catch error
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean readBoolean() {
        String s = read().trim();
        if ("true".equals(s)) {
            return true;
        }
        else if ("false".equals(s)) {
            return false;
        }
        else {
            throw new RuntimeException();
        }
    }

    private int readInt() {
        return Integer.parseInt(read());
    }

    private List<Integer> parseDigits(String u) {
        List<Integer> a = new ArrayList<>();
        Matcher digitMatcher = digitPattern.matcher(u);
        while (digitMatcher.find()) {
            a.add(Integer.parseInt(digitMatcher.group()));
        }
        return a;
    }

    private FormalString readFormalString() {
        return new FormalString(parseDigits(read()));
    }

    private Permutation parsePermutation(String t) {
        if (t.equals("()")) {
            return new Permutation();
        }
        else {
            List<Cycle> cycles = new ArrayList<>();
            Matcher cycleMatcher = cyclePattern.matcher(t);
            while (cycleMatcher.find()) {
                cycles.add(new Cycle(parseDigits(cycleMatcher.group())));
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
    public Permutation listToPermutation(List<Integer> list) {
        if (list.isEmpty()) {
            throw new RuntimeException();
        }
        int n = list.size();
        boolean[] mkd = new boolean[n];
        for (int x : list) {
            if (mkd[x - 1]) {
                throw new RuntimeException();
            }
            mkd[x - 1] = true;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("SortingPerm([");
        for (int i = 0; i < n - 1; i++) {
            sb.append(list.get(i));
            sb.append(',');
        }
        sb.append(list.get(n - 1));
        sb.append("])^-1;");

        write(sb.toString());

        return parsePermutation(read());
    }

    @Override
    public Permutation multiply(Permutation p, Permutation q) {
        if (performance && p.isIdentity()) {
            return q;
        }
        else if (performance && q.isIdentity()) {
            return p;
        }
        else {
            write(p.toString() + '*' + q.toString() + ';');
            return parsePermutation(read());
        }
    }

    @Override
    public Permutation invert(Permutation p) {
        if (performance && p.isIdentity()) {
            return p;
        }
        else {
            write(p.toString() + "^-1" + ';');
            return parsePermutation(read());
        }
    }

    @Override
    public FormalString permute(FormalString s, Permutation p) {
        write("Permuted(" + s.toString() + ", " + p.toString() + ");");
        return readFormalString();
    }

    @Override
    public List<Integer> act(List<Integer> t, Permutation p) {
        if (performance && t.isEmpty()) {
            return t;
        }
        else {
            StringBuilder sb = new StringBuilder();
            sb.append("OnTuples([");
            int n = t.size();
            for (int i = 0; i < n - 1; i++) {
                sb.append(t.get(i));
                sb.append(',');
            }
            sb.append(t.get(n - 1));
            sb.append("], ");
            sb.append(p.toString());
            sb.append(");");
            write(sb.toString());
            return parseDigits(read());
        }
    }

    @Override
    public List<Tuple> act(ImplicitDomain domain, Permutation p) {
        write("OnTuplesTuples(" + domain.toString() + ", " + p.toString() + ");");
        List<Integer> in = parseDigits(read());
        List<Tuple> ans = new ArrayList<>();
        int i = 0;
        while (i < in.size()) {
            List<Integer> tuple = new ArrayList<>(domain.k());
            for (int j = 0; j < domain.k(); j++, i++) {
                tuple.add(in.get(i));
            }
            ans.add(new Tuple(tuple));
        }
        return ans;
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
        if (performance && g.isTrivial()) {
            return d.size() == 1;
        }
        else {
            write("IsTransitive(" + g.toString() + ", " + d.toString() + ", OnSets);");
            return readBoolean();
        }
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
        List<Domain> orbits = new ArrayList<>();
        if (performance && g.isTrivial()) {
            for (Tuple tuple : d) {
                orbits.add(new ExplicitDomain(tuple));
            }
        }
        else {
            write("OrbitsDomain(" + g.toString() + ", " + d.toString() + ", OnSets);");

            Node tree = NestedParser.parse(read());
            for (Node child : tree) {
                orbits.add(parseDomain(child));
            }
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
        if (!performance && !isTransitive(g, d)) {
            throw new RuntimeException();
        }

        write("Blocks(" + g.toString() + ", " + d.toString() + ", OnSets);");

        Node tree = NestedParser.parse(read());
        if (tree.numChildren() == 1) {
            return new Group();
        }
        else {
            for (Node child : tree) {
                g = getSetwiseStabilizer(g, parseDomain(child));
            }
            return g;
        }
    }

    public boolean isSubgroup(Group g, Group h) {
        write("IsSubgroup(" + g.toString() + ", " + h.toString() + ");");
        return readBoolean();
    }

    @Override
    public List<Permutation> getTransversal(Group g, Group h) {
        if (!performance && !isSubgroup(g, h)) {
            throw new RuntimeException();
        }

        write("Elements(RightTransversal(" + g.toString() + ", " + h.toString() + "));");

        return parsePermutations(read());
    }

    public String identifyGroup(Group g) {
        write("StructureDescription(" + g.toString() + ");");
        String s = read();
        return s.substring(1, s.length() - 1);
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
