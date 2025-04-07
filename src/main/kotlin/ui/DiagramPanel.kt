package org.example.ui

import com.mxgraph.layout.mxCircleLayout
import com.mxgraph.swing.mxGraphComponent
import com.mxgraph.view.mxGraph
import org.example.graph.Graph
import org.example.graph.GraphLayout
import javax.swing.JPanel
import java.awt.BorderLayout
import java.awt.Graphics

class DiagramPanel : JPanel() {
    private val graphComponent: mxGraphComponent
    private val jGraph: mxGraph

    init {
        layout = BorderLayout()
        jGraph = mxGraph()
        graphComponent = mxGraphComponent(jGraph)
        add(graphComponent, BorderLayout.CENTER)
    }

    fun clear() {
        jGraph.model.beginUpdate()
        try {
            jGraph.removeCells(jGraph.getChildCells(jGraph.defaultParent, true, true))
        } finally {
            jGraph.model.endUpdate()
        }
        repaint()
    }


    fun renderGraph(graph: Graph, layout: GraphLayout, enabledVertices: Set<String>) {
        val parent = jGraph.defaultParent
        jGraph.model.beginUpdate()
        try {
            val nodeMap = mutableMapOf<String, Any>()
            for (node in graph.getNodes()) {
                if (enabledVertices.contains(node)) {
                    val position = layout.getNodePosition(node)
                    if (position != null) {
                        val vertex = jGraph.insertVertex(parent, null, node, position.first.toDouble(), position.second.toDouble(), 80.0, 80.0, "shape=ellipse")
                        nodeMap[node] = vertex
                    }
                }
            }
            for ((from, toSet) in graph.getEdges()) {
                if (enabledVertices.contains(from)) {
                    for (to in toSet) {
                        if (enabledVertices.contains(to)) {
                            val fromVertex = nodeMap[from]
                            val toVertex = nodeMap[to]
                            if (fromVertex != null && toVertex != null) {
                                jGraph.insertEdge(parent, null, "", fromVertex, toVertex)
                            }
                        }
                    }
                }
            }
        } finally {
            jGraph.model.endUpdate()
        }

        // Apply circular layout
        val layout = mxCircleLayout(jGraph)
        layout.execute(jGraph.defaultParent)
    }
    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        // Custom painting code
    }

}