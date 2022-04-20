package GraphTheory.Engines;

import GraphTheory.Structs.Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Nauty {
    private String location;

    public Nauty(String location) {
        if (location.endsWith("/")) {
            this.location = location.substring(0, location.length() - 1);
        }
        else {
            this.location = location;
        }
    }

    private String amtogGraph(Graph g) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(location + "/amtog");
        Process process = processBuilder.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        PrintWriter out = new PrintWriter(process.getOutputStream());

        int n = g.getNumVertices();
        out.println("n=" + n);
        out.flush();
        out.println('m');
        out.flush();
        for (int i = 0; i < n; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < n; j++) {
                if (g.isAdjacent(i, j)) {
                    sb.append('1');
                }
                else {
                    sb.append('0');
                }
            }
            out.println(sb.toString());
            out.flush();
        }
        out.println('q');
        out.flush();

        String output = in.readLine();

        in.close();
        out.close();
        process.destroy();

        return output;
    }

    public boolean isIsomorphic(Graph g, Graph h) throws IOException {
        int n = g.getNumVertices();
        if (h.getNumVertices() != n) {
            return false;
        }

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(location + "/shortg");
        Process process = processBuilder.start();
        BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        PrintWriter out = new PrintWriter(process.getOutputStream());

        out.println(amtogGraph(g));
        out.flush();
        out.println(amtogGraph(h));
        out.flush();
        out.close();

        String line = err.readLine();
        while (line.startsWith(">")) {
            if (line.startsWith(">Z") && line.endsWith("stdout")) {
                int output = Integer.parseInt(line.split("\\s+")[1]);
                return output == 1;
            }
            line = err.readLine();
        }

        throw new RuntimeException();
    }
}
