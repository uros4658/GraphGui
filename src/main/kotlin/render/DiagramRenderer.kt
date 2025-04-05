package org.example.render

import org.example.graph.Graph
import org.example.graph.GraphLayout
import java.io.File

class DiagramRenderer {
    fun render(graph: Graph, layout: GraphLayout): String {
        val builder = StringBuilder()
        val nodePositions = layout.getAllNodePositions()

        builder.append("Graph Layout:\n")
        for ((node, position) in nodePositions) {
            builder.append("Node $node at position (${position.first}, ${position.second})\n")
        }

        builder.append("\nEdges:\n")
        for ((from, toSet) in graph.getEdges()) {
            for (to in toSet) {
                builder.append("$from -> $to\n")
            }
        }

        return builder.toString()
    }

    fun saveToFile(filePath: String, content: String) {
        File(filePath).writeText(content)
        println("Saving diagram to $filePath")
    }
}