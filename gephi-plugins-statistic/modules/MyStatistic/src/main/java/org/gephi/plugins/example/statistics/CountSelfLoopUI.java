package org.gephi.plugins.example.statistics;

import javax.swing.JPanel;
import org.gephi.statistics.spi.Statistics;
import org.gephi.statistics.spi.StatisticsUI;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = StatisticsUI.class)
public class CountSelfLoopUI implements StatisticsUI {

    private CountSelfLoop statistic;
    private CountSelfLoopPanel panel;

    @Override
    public JPanel getSettingsPanel() {
        panel = new CountSelfLoopPanel();
        return panel;
    }

    @Override
    public void setup(Statistics stat) {
        this.statistic = (CountSelfLoop) stat;
    }

    @Override
    public void unsetup() {
        this.statistic = null;
    }

    @Override
    public Class<? extends Statistics> getStatisticsClass() {
        return CountSelfLoop.class;
    }

    @Override
    public String getValue() {
        if (statistic != null) {
            return "" + statistic.getSelfLoopCount();
        }
        return "";
    }

    @Override
    public String getDisplayName() {
        return "Graph Embedding";
    } //Nome nel menu a destra e dentro a settings

    @Override
    public String getCategory() {
        return StatisticsUI.CATEGORY_NETWORK_OVERVIEW;
    }

    @Override
    public int getPosition() {
        return 10000;
    }

    @Override
    public String getShortDescription() {
        return null;
    }
}
