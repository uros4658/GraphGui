package org.example.ui

import javax.swing.AbstractListModel

class VertexListModel : AbstractListModel<String>() {
    private val vertices = mutableListOf<String>()
    private val enabledVertices = mutableSetOf<String>()

    override fun getSize(): Int = vertices.size

    override fun getElementAt(index: Int): String = vertices[index]

    fun setVertices(newVertices: List<String>) {
        vertices.clear()
        vertices.addAll(newVertices)
        enabledVertices.clear()
        enabledVertices.addAll(newVertices)
        fireContentsChanged(this, 0, vertices.size - 1)
    }

    fun isEnabled(vertex: String): Boolean = enabledVertices.contains(vertex)

    fun setEnabled(vertex: String, enabled: Boolean) {
        if (enabled) {
            enabledVertices.add(vertex)
        } else {
            enabledVertices.remove(vertex)
        }
        fireContentsChanged(this, 0, vertices.size - 1)
    }

    fun getEnabledVertices(): Set<String> = enabledVertices

    fun getDisabledVertices(): Set<String> {
        return vertices.filterNot { enabledVertices.contains(it) }.toSet()
    }
}