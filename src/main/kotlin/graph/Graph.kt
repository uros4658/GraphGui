package org.example.graph

class Graph {
    private val nodes = mutableSetOf<String>()
    private val edges = mutableMapOf<String, MutableSet<String>>()
    private val enabledNodes = mutableMapOf<String, Boolean>()

    fun addNode(node: String, enabled: Boolean = true) {
        nodes.add(node)
        enabledNodes[node] = enabled
    }

    fun addEdge(from: String, to: String) {
        if (!nodes.contains(from) || !nodes.contains(to)) {
            throw IllegalArgumentException("Both nodes must be in the graph")
        }
        edges.computeIfAbsent(from) { mutableSetOf() }.add(to)
    }

    fun containsNode(node: String): Boolean {
        return nodes.contains(node)
    }

    fun isEnabled(node: String): Boolean {
        return enabledNodes[node] ?: false
    }

    fun setEnabled(node: String, enabled: Boolean) {
        if (nodes.contains(node)) {
            enabledNodes[node] = enabled
        }
    }

    fun getNodes(): Set<String> = nodes

    fun getEdges(): Map<String, Set<String>> = edges

    fun getEnabledNodes(): Map<String, Boolean> = enabledNodes
}