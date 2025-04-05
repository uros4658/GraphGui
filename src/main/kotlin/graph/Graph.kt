package org.example.graph

class Graph {
    private val nodes = mutableSetOf<String>()
    private val edges = mutableMapOf<String, MutableSet<String>>()

    fun addNode(node: String) {
        nodes.add(node)
    }

    fun addEdge(from: String, to: String) {
        if (!nodes.contains(from) || !nodes.contains(to)) {
            throw IllegalArgumentException("Both nodes must be in the graph")
        }
        edges.computeIfAbsent(from) { mutableSetOf() }.add(to)
    }

    fun getNodes(): Set<String> = nodes

    fun getEdges(): Map<String, Set<String>> = edges

}