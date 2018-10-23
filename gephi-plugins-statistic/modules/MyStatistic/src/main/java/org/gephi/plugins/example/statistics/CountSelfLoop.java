package org.gephi.plugins.example.statistics;

import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphModel;
import org.gephi.statistics.spi.Statistics;

public class CountSelfLoop implements Statistics {

    //Result
    private int totalEdgeCount;
    private int selfLoopCount;

    @Override
    public void execute(GraphModel graphModel) {
        Graph graph = graphModel.getGraphVisible();
        selfLoopCount = 0;
        totalEdgeCount = graph.getEdgeCount();
        for (Edge e : graph.getEdges()) {
            if (e.isSelfLoop()) {
                selfLoopCount++;
            }
        }
    }

    @Override
    public String getReport() {
        String report = "<HTML> <BODY> <h1>Count Self-Loop Report </h1> "
                + "<hr>"
                + "<br> <h2> Results: </h2>"
                + "Total number of edges: " + totalEdgeCount
                + "<br />Number of self-loop: " + selfLoopCount
                + "<br />"
                + "</BODY></HTML>";
        return report;
    }

    public int getSelfLoopCount() {
        return selfLoopCount;
    }
}
