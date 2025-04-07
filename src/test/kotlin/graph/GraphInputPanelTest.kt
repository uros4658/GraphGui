import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.example.graph.Graph
import org.example.graph.GraphParser
import org.example.ui.GraphInputPanel

class GraphInputPanelTest {
    private lateinit var graphInputPanel: GraphInputPanel
    private lateinit var graphParser: GraphParser

    @BeforeEach
    fun setUp() {
        graphInputPanel = GraphInputPanel()
        graphParser = GraphParser()
    }

    @Test
    fun testParseGraphInput() {
        val input = "A - B\nB - C"
        graphInputPanel.parseGraphInput(input)
        val graph = graphInputPanel.graph
        assertTrue(graph.containsNode("A"))
        assertTrue(graph.containsNode("B"))
        assertTrue(graph.containsNode("C"))
    }

    @Test
    fun testAddEdgesToGraph() {
        val input = "A - B\nB - C"
        graphInputPanel.parseGraphInput(input)
        val graph = graphInputPanel.graph
        assertEquals(5, graph.getNodes().size)
        assertTrue(graph.containsNode("A"))
        assertTrue(graph.containsNode("C"))
    }

    @Test
    fun testDisableSelectedVertices() {
        val input = "A - B\nB - C"
        graphInputPanel.parseGraphInput(input)
        graphInputPanel.vertexList.setSelectedValue("B", true)
        graphInputPanel.disableSelectedVertices()
        assertFalse(graphInputPanel.vertexListModel.isEnabled("B"))
    }

    @Test
    fun testDisabledVerticesNotParsed() {
        val input = "A - B\nB - C"
        graphInputPanel.parseGraphInput(input)
        graphInputPanel.vertexList.setSelectedValue("B", true)
        graphInputPanel.disableSelectedVertices()
        val graph = graphInputPanel.graph
        assertFalse(graph.containsNode("D"))
        assertFalse(graph.containsNode("E"))
    }
}