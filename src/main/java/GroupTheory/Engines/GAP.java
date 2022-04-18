package GroupTheory.Engines;

import GroupTheory.Structs.Group;
import GroupTheory.Structs.Permutation;

import java.io.*;

public class GAP implements GroupTheoryEngine {
    private final Process process;
    private final BufferedReader in, err;
    private final PrintWriter out;

    private final String initialPrompt = " Try";
    private final String outFormat = "SetPrintFormattingStatus(\"*stdout*\", false);;";


    public GAP(String location) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(location);
        process = processBuilder.start();
        in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        out = new PrintWriter(process.getOutputStream());
        err = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        // don't break GAP output into multiple lines
        String line = in.readLine();
        while (!line.startsWith(initialPrompt)) {
            line = in.readLine();
        }
        out.println(outFormat);
        out.flush();
    }

    private String read() {
        try {
            String line = in.readLine();
            while (line.startsWith("gap> ")) {
                line = line.substring(5);
            }
            return line;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void write(String s) {
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
        return false;
    }

    public void close() throws IOException {
        in.close();
        out.close();
        process.destroy();
    }
}
