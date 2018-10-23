package org.gephi.plugins.example.statistics;

import org.gephi.statistics.spi.Statistics;
import org.gephi.statistics.spi.StatisticsBuilder;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = StatisticsBuilder.class)
public class CountSelfLoopBuilder implements StatisticsBuilder {

    @Override
    public String getName() {
        return "Graph Embedding";
    }

    @Override
    public Statistics getStatistics() {
        return new CountSelfLoop();
    }

    @Override
    public Class<? extends Statistics> getStatisticsClass() {
        return CountSelfLoop.class;
    }
}
