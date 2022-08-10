package edu.kit.kastel.lissa.linking

import com.fasterxml.jackson.module.kotlin.readValue
import com.mxgraph.layout.mxOrganicLayout
import com.mxgraph.util.mxCellRenderer
import edu.kit.kastel.lissa.linking.graph.Graph
import edu.kit.kastel.lissa.linking.graphmatcher.convertSketchToGraph
import edu.kit.kastel.lissa.sketches.coco.UMLDataAccessor
import edu.kit.kastel.lissa.sketches.coco.domain.COCOData
import edu.kit.kastel.lissa.utils.createObjectMapper
import edu.kit.kastel.lissa.utils.runInCI
import org.jgrapht.ListenableGraph
import org.jgrapht.ext.JGraphXAdapter
import org.jgrapht.graph.DefaultDirectedGraph
import org.jgrapht.graph.DefaultListenableGraph
import org.junit.jupiter.api.Assumptions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.awt.Color
import java.awt.Desktop
import java.io.File
import javax.imageio.ImageIO

/**
 * Simply visualize a sketch into a graph.
 * Code initially adopted form https://www.baeldung.com/jgrapht
 */
class GraphVisualizeTest {

    private lateinit var data: UMLDataAccessor

    @BeforeEach
    fun simpleLoad() {
        val cocoFile = GraphVisualizeTest::class.java.getResourceAsStream("/sample/sample_coco_small.json")!!
        val multipleSketches: COCOData = createObjectMapper().readValue(cocoFile)
        data = UMLDataAccessor(multipleSketches.image(0))
    }

    @Test
    fun showGraph() {
        Assumptions.assumeFalse(runInCI())
        val sketch = data.sketch()
        val graph = convertSketchToGraph(sketch)
        val graphAdapter = JGraphXAdapter(graph2jGraphT(graph))
        val layout = mxOrganicLayout(graphAdapter)
        layout.execute(graphAdapter.defaultParent)

        val image = mxCellRenderer.createBufferedImage(graphAdapter, null, 2.0, Color.WHITE, true, null)
        val imgFile = File.createTempFile("graph", ".png")
        ImageIO.write(image, "PNG", imgFile)
        Desktop.getDesktop().open(imgFile)
    }

    private fun graph2jGraphT(graph: Graph): ListenableGraph<String, String> {
        val result: ListenableGraph<String, String> = DefaultListenableGraph(DefaultDirectedGraph(String::class.java))
        graph.nodes().forEach { result.addVertex(it.name()) }
        graph.edges().forEach { result.addEdge(it.first.name(), it.second.name(), it.third.name()) }
        return result
    }
}
