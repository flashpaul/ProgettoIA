/*
Copyright 2008-2011 Gephi
Authors : Mathieu Bastian <mathieu.bastian@gephi.org>
Website : http://www.gephi.org

This file is part of Gephi.

DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

Copyright 2011 Gephi Consortium. All rights reserved.

The contents of this file are subject to the terms of either the GNU
General Public License Version 3 only ("GPL") or the Common
Development and Distribution License("CDDL") (collectively, the
"License"). You may not use this file except in compliance with the
License. You can obtain a copy of the License at
http://gephi.org/about/legal/license-notice/
or /cddl-1.0.txt and /gpl-3.0.txt. See the License for the
specific language governing permissions and limitations under the
License.  When distributing the software, include this License Header
Notice in each file and include the License files at
/cddl-1.0.txt and /gpl-3.0.txt. If applicable, add the following below the
License Header, with the fields enclosed by brackets [] replaced by
your own identifying information:
"Portions Copyrighted [year] [name of copyright owner]"

If you wish your version of this file to be governed by only the CDDL
or only the GPL Version 3, indicate your decision by adding
"[Contributor] elects to include this software in this distribution
under the [CDDL or GPL Version 3] license." If you do not indicate a
single choice of license, a recipient has the option to distribute
your version of this file under either the CDDL, the GPL Version 3 or
to extend the choice of license to its licensees as provided above.
However, if you add GPL Version 3 code and therefore, elected the GPL
Version 3 license, then the option applies only if the new code is
made subject to such option by the copyright holder.

Contributor(s):

Portions Copyrighted 2011 Gephi Consortium.
 */
package org.gephi.plugins.example.layout;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import org.gephi.graph.api.*;
import org.gephi.layout.spi.Layout;
import org.gephi.layout.spi.LayoutBuilder;
import org.gephi.layout.spi.LayoutProperty;
import org.gephi.ui.propertyeditor.NodeColumnAllNumbersEditor;
import org.gephi.ui.propertyeditor.NodeColumnNumbersEditor;
import org.openide.util.Lookup;

/**
 * Example of a layout algorithm which places all nodes in a grid.
 * <p>
 * The algorithm calculates nodes' position but moved them slowly at each iteration
 * so we can see transitions.
 * <p>
 * This class also defines the properties the user can manipulate: area size and speed.
 * It shows how to create {@link LayoutProperty} objects. Make sure you also set
 * the getter and setter for each property.
 * 
 * @author Mathieu Bastian
 */
public class TryLayout implements Layout {

    //Architecture
    private final LayoutBuilder builder;
    private GraphModel graphModel;
    //Flags
    private boolean executing = false;
    private boolean first = true;
    //Properties
    private String xAttribute, yAttribute;
    public static String[] attributes;



    private boolean gridded;

    public TryLayout(TryLayoutBuilder builder) {
        this.builder = builder;
        resetPropertiesValues();
    }

    @Override
    public void resetPropertiesValues() {
        gridded = false;
        if (graphModel != null) {
            attributes = new String[graphModel.getNodeTable().countColumns()];
            for (int i = 0; i < graphModel.getNodeTable().countColumns(); i++) {
                attributes[i] = graphModel.getNodeTable().getColumn(i).getId();
            }
        }
    }

    @Override
    public void initAlgo() {
        executing = true;
    }

    @Override
    public void goAlgo() {
        Graph graph = graphModel.getGraphVisible();
        int nodeCount = graph.getNodeCount();
        Node[] nodes = graph.getNodes().toArray();

        if (!graphModel.getNodeTable().hasColumn("grid")) {
            graphModel.getNodeTable().addColumn("grid", String.class);
        }
        else{
            for (Node i : nodes){
                if (i.getAttribute("grid").equals("grid")) nodeCount--;
            }
        }
        graph.readLock();

        for (int i = 0; i < nodeCount; i++) {
            Node node = nodes[i];

            int x = Integer.parseInt(node.getAttribute(xAttribute).toString());
            int y = Integer.parseInt(node.getAttribute(yAttribute).toString());
            node.setX(x);
            node.setY(y);
            //node.setSize((areaSize / cols) / 10);
            if (first) {
                node.setAttribute("grid", "no");
            }

        }
        graph.readUnlock();


        int rows = (int) Math.round(Math.sqrt(nodeCount)) + 1;
        int cols = (int) Math.round(Math.sqrt(nodeCount)) + 1;

//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols && (i * rows + j) < nodes.length; j++) {
//                Node node = nodes[i * rows + j];
//                if (!first) {
//                    if (node.getAttribute("grid").toString().equals("")) {
//                        float x = (j * (areaSize / cols)) + (areaSize / cols) / 2;
//                        float y = (i * (areaSize / rows)) + (areaSize / rows) / 2;
//                        node.setX(x);
//                        node.setY(y);
//                        node.setSize((areaSize / cols) / 10);
//                    }
//                }
//                else{
//                    float x = (j * (areaSize / cols)) + (areaSize / cols) / 2;
//                    float y = (i * (areaSize / rows)) + (areaSize / rows) / 2;
//                    node.setX(x);
//                    node.setY(y);
//                    node.setSize((areaSize / cols) / 10);
//                    node.setAttribute("grid", "");
//                }
//            }
//        }

//        graph.readUnlock();
        graph.writeLock();

        if (gridded && first) {
            Node[][] grid = new Node[rows + 1][cols + 1];

            GraphFactory factory = graphModel.factory();
            for (int i = 0; i <= rows; i++) {
                for (int j = 0; j <= cols; j++) {
                    Node tmp = factory.newNode();
                    float x = j;
                    float y = i;
                    tmp.setX(x);
                    tmp.setY(y);
                    tmp.setSize(1);
                    tmp.setColor(Color.BLACK);
                    tmp.setAttribute("grid", "grid");
                    grid[i][j] = tmp;
                    graph.addNode(tmp);
                    if (i > 0) if (grid[i - 1][j] != null) {
                        graph.addEdge(factory.newEdge(tmp, grid[i - 1][j], 10, false));
                    }
                    if (i < rows) if (grid[i + 1][j] != null) {
                        graph.addEdge(factory.newEdge(tmp, grid[i + 1][j], 10, false));
                    }
                    if (j > 0) if (grid[i][j - 1] != null) {
                        graph.addEdge(factory.newEdge(tmp, grid[i][j - 1], 10, false));
                    }
                    if (j < cols) if (grid[i][j + 1] != null) {
                        graph.addEdge(factory.newEdge(tmp, grid[i][j + 1], 10, false));
                    }
                }
            }
            first = false;
        }
        if (!gridded){
            for (Node i: nodes){
                if (i.getAttribute("grid").equals("grid")){
                    for (Edge j : graph.getEdges().toArray()){
                        if (j.getSource() == i ) graph.removeEdge(j);
                        else if (j.getTarget() == i ) graph.removeEdge(j);
                    }
                    graph.removeNode(i);
                }
            }
            first = true;
        }
        graph.writeUnlock();

        endAlgo();
    }

    @Override
    public void endAlgo() {
        executing = false;
    }

    @Override
    public boolean canAlgo() {
        return executing;
    }

    @Override
    public LayoutProperty[] getProperties() {
        List<LayoutProperty> properties = new ArrayList<LayoutProperty>();
        final String TRYLAYOUT = "Try Layout"; //titolo del layout una volta selezionato

        try {
            properties.add(LayoutProperty.createProperty(
                    this, Boolean.class,
                    "Grid",
                    TRYLAYOUT,
                    "View the grid",
                    "isGridded", "setGridded"));
            properties.add(LayoutProperty.createProperty(
                    this, String.class,
                    "X attribute",
                    TRYLAYOUT,
                    "The attribute to be used as horizontal coordinate",
                    "getxAttribute", "setxAttribute", CustomComboBoxEditor.class));
            properties.add(LayoutProperty.createProperty(
                    this, String.class,
                    "Y attribute",
                    TRYLAYOUT,
                    "The attribute to be used as vertical coordinate",
                    "getyAttribute", "setyAttribute", CustomComboBoxEditor.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return properties.toArray(new LayoutProperty[0]);
    }

    @Override
    public LayoutBuilder getBuilder() {
        return builder;
    }

    @Override
    public void setGraphModel(GraphModel gm) {
        this.graphModel = gm;
        resetPropertiesValues();
    }

    public Boolean isGridded() {
        return gridded;
    }

    public void setGridded(Boolean gridded) {
        this.gridded = gridded;
    }

    public String getxAttribute() {
        return xAttribute;
    }

    public void setxAttribute(String xAttribute) {
        this.xAttribute = xAttribute;
    }

    public String getyAttribute() {
        return yAttribute;
    }

    public void setyAttribute(String yAttribute) {
        this.yAttribute = yAttribute;
    }
}
