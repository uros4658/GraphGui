package org.example.ui

import javax.swing.AbstractListModel

class VertexListModel : AbstractListModel<String>() {
    private val vertices = mutableListOf<String>()
    private val enabledVertices = mutableMapOf<String, Boolean>()

    override fun getSize(): Int {
        return vertices.size
    }

    override fun getElementAt(index: Int): String {
        return vertices[index]
    }

    fun setVertices(newVertices: List<String>) {
        vertices.clear()
        vertices.addAll(newVertices)
        enabledVertices.clear()
        for (vertex in newVertices) {
            enabledVertices[vertex] = true
        }
        fireContentsChanged(this, 0, vertices.size - 1)
    }

    fun isEnabled(vertex: String): Boolean {
        return enabledVertices[vertex] ?: false
    }

    fun setEnabled(vertex: String, enabled: Boolean) {
        enabledVertices[vertex] = enabled
        fireContentsChanged(this, 0, vertices.size - 1)
    }
}