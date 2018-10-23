package org.gephi.plugins.example.statistics;

import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphModel;
import org.gephi.statistics.spi.Statistics;

import java.io.IOException;

public class CountSelfLoop implements Statistics {

    //Result

    @Override
    public void execute(GraphModel graphModel) {
        Thread z = new Thread(){
            public void run(){
                ProcessBuilder pb = new ProcessBuilder("python", "prova.py");
                try {
                    Process p = pb.start();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        };
        z.start();


    }

    @Override
    public String getReport() {
        String report = "<HTML> <BODY> <h1>Embedding Report </h1> "
                + "<hr>"
                + "<br> <h2> Results: </h2>"
                + "Total number of edges: "
                + "<br />Number of self-loop: "
                + "<br />"
                + "</BODY></HTML>";
        return report;
    }

    public int getSelfLoopCount() {
        return 0;
    }
}
