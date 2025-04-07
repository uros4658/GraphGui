package org.example.graph

class GraphParser {
    fun parse(input: String): Graph {
        val graph = Graph()
        val lines = input.lines()
        for (line in lines) {
            val parts = line.split("-")
            if (parts.size == 1) {
                graph.addNode(parts[0])
            } else if (parts.size == 2) {
                graph.addNode(parts[0])
                graph.addNode(parts[1])
                graph.addEdge(parts[0], parts[1])
            }
        }
        return graph
    }

    fun parseEdges(graph: Graph, input: String): List<Pair<String, String>> {
        val edges = mutableListOf<Pair<String, String>>()
        val lines = input.lines()
        for (line in lines) {
            val parts = line.split("-")
            if (parts.size == 2) {
                edges.add(Pair(parts[0], parts[1]))
                graph.addEdge(parts[0], parts[1])
            }
        }
        return edges
    }
}