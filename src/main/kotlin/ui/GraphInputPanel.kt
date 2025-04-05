package org.example.ui

import org.example.graph.GraphParser
import org.example.graph.Graph
import org.example.graph.GraphLayout
import javax.swing.JPanel
import javax.swing.JTextArea
import javax.swing.JButton
import javax.swing.JScrollPane
import javax.swing.JList
import javax.swing.ListSelectionModel
import javax.swing.event.ListSelectionListener
import java.awt.BorderLayout
import java.awt.event.ActionListener

class GraphInputPanel : JPanel() {
    private val textArea: JTextArea = JTextArea(5, 30)
    private val parseButton: JButton = JButton("Parse Graph")
    private val addEdgesButton: JButton = JButton("Add Edges")
    private val disableButton: JButton = JButton("Disable")
    private val graphParser: GraphParser = GraphParser()
    private val diagramPanel: DiagramPanel = DiagramPanel()
    private val vertexListModel: VertexListModel = VertexListModel()
    private val vertexList: JList<String> = JList(vertexListModel)
    private var graph: Graph = Graph()

    init {
        layout = BorderLayout()
        val inputPanel = JPanel(BorderLayout())
        inputPanel.add(JScrollPane(textArea), BorderLayout.CENTER)
        val buttonPanel = JPanel()
        buttonPanel.add(parseButton)
        buttonPanel.add(addEdgesButton)
        inputPanel.add(buttonPanel, BorderLayout.SOUTH)
        add(inputPanel, BorderLayout.NORTH)
        add(diagramPanel, BorderLayout.CENTER)
        val listPanel = JPanel(BorderLayout())
        listPanel.add(JScrollPane(vertexList), BorderLayout.CENTER)
        listPanel.add(disableButton, BorderLayout.SOUTH)
        add(listPanel, BorderLayout.EAST)

        vertexList.selectionMode = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        vertexList.addListSelectionListener(ListSelectionListener {
            updateDiagram()
        })

        parseButton.addActionListener(ActionListener {
            val input = getGraphInput()
            parseGraphInput(input)
        })

        addEdgesButton.addActionListener(ActionListener {
            val input = getGraphInput()
            addEdgesToGraph(input)
        })

        disableButton.addActionListener(ActionListener {
            disableSelectedVertices()
        })
    }

    private fun getGraphInput(): String {
        return textArea.text
    }

    private fun parseGraphInput(input: String) {
        println("Parsing graph input: $input")
        graph = Graph() // Reset the graph
        graph = graphParser.parse(input)
        println("Parsed graph: $graph")
        val layout = GraphLayout()
        layout.layoutGraph(graph)
        vertexListModel.setVertices(graph.getNodes().toList())
        updateDiagram()
    }

    private fun addEdgesToGraph(input: String) {
        println("Adding edges to graph: $input")
        graphParser.parseEdges(graph, input)
        println("Updated graph: $graph")
        updateDiagram()
    }

    private fun disableSelectedVertices() {
        val selectedVertices = vertexList.selectedValuesList
        for (vertex in selectedVertices) {
            vertexListModel.setEnabled(vertex, false)
        }
        updateDiagram()
    }

    private fun updateDiagram() {
        val layout = GraphLayout()
        layout.layoutGraph(graph)
        diagramPanel.renderGraph(graph, layout, vertexListModel.getEnabledVertices())
    }
}