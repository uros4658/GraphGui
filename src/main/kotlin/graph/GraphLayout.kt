package org.example.graph

class GraphLayout {
    private val nodePositions = mutableMapOf<String, Pair<Int, Int>>()

    fun setNodePosition(node: String, x: Int, y: Int) {
        nodePositions[node] = Pair(x, y)
    }

    fun getNodePosition(node: String): Pair<Int, Int>? {
        return nodePositions[node]
    }

    fun getAllNodePositions(): Map<String, Pair<Int, Int>> {
        return nodePositions
    }

    fun layoutGraph(graph: Graph) {
        var x = 50
        var y = 50
        for (node in graph.getNodes()) {
            setNodePosition(node, x, y)
            x += 100
            if (x > 500) {
                x = 50
                y += 100
            }
        }
    }
}