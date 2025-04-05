package org.example.render

import org.example.graph.Graph
import org.example.graph.GraphLayout
import java.io.File

class PlantUmlService {
    fun render(graph: Graph, layout: GraphLayout): String {
        val builder = StringBuilder()
        builder.append("@startuml\n")

        val nodePositions = layout.getAllNodePositions()
        for ((node, position) in nodePositions) {
            builder.append("rectangle $node {\n")
            builder.append("  position (${position.first}, ${position.second})\n")
            builder.append("}\n")
        }

        for ((from, toSet) in graph.getEdges()) {
            for (to in toSet) {
                builder.append("$from --> $to\n")
            }
        }

        builder.append("@enduml\n")
        return builder.toString()
    }

    fun saveToFile(filePath: String, content: String) {
        File(filePath).writeText(content)
        println("Saving diagram to $filePath")
    }
}