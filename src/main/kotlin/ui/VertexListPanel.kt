package org.example.ui

import javax.swing.JPanel
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.DefaultListModel
import java.awt.BorderLayout

class VertexListPanel : JPanel() {
    private val vertexListModel = DefaultListModel<String>()
    private val vertexList = JList(vertexListModel)

    init {
        layout = BorderLayout()
        add(JScrollPane(vertexList), BorderLayout.CENTER)
    }

    fun addVertex(vertex: String) {
        vertexListModel.addElement(vertex)
    }

    fun removeVertex(vertex: String) {
        vertexListModel.removeElement(vertex)
    }

    fun getVertices(): List<String> {
        return (0 until vertexListModel.size()).map { vertexListModel.getElementAt(it) }
    }
}