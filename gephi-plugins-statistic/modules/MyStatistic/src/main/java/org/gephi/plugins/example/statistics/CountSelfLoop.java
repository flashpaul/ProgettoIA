package org.gephi.plugins.example.statistics;

import org.gephi.graph.api.GraphModel;
import org.gephi.statistics.spi.Statistics;

import java.io.IOException;
public class CountSelfLoop implements Statistics {

    //Result

    @Override
    public void execute(GraphModel graphModel) {

        try {
            Process p = Runtime.getRuntime().exec("py ..\\..\\modules\\MyStatistic\\src\\main\\java\\org\\gephi\\plugins\\example\\statistics\\prova.py");



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getReport() {
        return "<HTML> <BODY> <h1>Embedding Report </h1> "
                + "<hr>"
                + "<br> <h2> Results: </h2>"
                + "Total number of edges: "
                + "<br />Number of self-loop: "
                + "<br />"
                + "</BODY></HTML>";
    }

    int getSelfLoopCount() {
        return 0;
    }
}
