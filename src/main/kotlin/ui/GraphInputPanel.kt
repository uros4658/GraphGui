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
    private val clearButton: JButton = JButton("Clear")
    private val graphParser: GraphParser = GraphParser()
    private val diagramPanel: DiagramPanel = DiagramPanel()
    val vertexListModel: VertexListModel = VertexListModel()
    val vertexList: JList<String> = JList(vertexListModel)
    var graph: Graph = Graph()

    init {
        layout = BorderLayout()
        val inputPanel = JPanel(BorderLayout())
        inputPanel.add(JScrollPane(textArea), BorderLayout.CENTER)
        val buttonPanel = JPanel()
        buttonPanel.add(parseButton)
        buttonPanel.add(addEdgesButton)
        buttonPanel.add(clearButton)
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

        clearButton.addActionListener(ActionListener {
            clearGraph()
        })
    }

    private fun getGraphInput(): String {
        return textArea.text
    }

    fun parseGraphInput(input: String) {
        println("Parsing graph input: $input")
        graph = Graph() // Reset the graph
        graph = graphParser.parse(input)
        println("Parsed graph: $graph")
        val layout = GraphLayout()
        layout.layoutGraph(graph)
        val enabledVertices = graph.getNodes().filter { vertexListModel.isEnabled(it) }
        vertexListModel.setVertices(enabledVertices)
        updateDiagram()
    }

    fun addEdgesToGraph(input: String) {
        println("Adding edges to graph: $input")
        val edges = graphParser.parseEdges(graph, input).toList()
        for (edge in edges) {
            val from = edge.first
            val to = edge.second
            if (!vertexListModel.isEnabled(from) || !vertexListModel.isEnabled(to)) {
                continue
            }
            if (!graph.containsNode(from)) {
                graph.addNode(from)
            }
            if (!graph.containsNode(to)) {
                graph.addNode(to)
            }
            graph.addEdge(from, to)
        }
        println("Updated graph: $graph")
        updateDiagram()
    }

    fun disableSelectedVertices() {
        val selectedVertices = vertexList.selectedValuesList
        for (vertex in selectedVertices) {
            vertexListModel.setEnabled(vertex, false)
        }
        updateDiagram()
    }

    private fun clearGraph() {
        graph = Graph() // Reset the graph
        vertexListModel.setVertices(emptyList())
        updateDiagram()
    }

    private fun updateDiagram() {
        diagramPanel.clear() // Clear the diagram panel
        val layout = GraphLayout()
        layout.layoutGraph(graph)
        diagramPanel.renderGraph(graph, layout, graph.getEnabledNodes().keys)
    }
}