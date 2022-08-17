package edu.kit.kastel.lissa.linking.graphmatcher

import com.fasterxml.jackson.module.kotlin.readValue
import edu.kit.kastel.lissa.linking.GraphVisualizeTest
import edu.kit.kastel.lissa.linking.graph.Graph
import edu.kit.kastel.lissa.sketches.coco.UMLDataAccessor
import edu.kit.kastel.lissa.sketches.coco.domain.COCOData
import edu.kit.kastel.lissa.utils.createObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GraphMatcherTest {
    @Test
    fun simpleTest() {
        val graphA = Graph()
        val a = graphA.addVertex("A")
        val b = graphA.addVertex("B")
        val c = graphA.addVertex("C")

        val e1 = graphA.addEdge("1", "assoc", a, b)
        val e2 = graphA.addEdge("2", "assoc", b, c)
        val e3 = graphA.addEdge("3", "assoc", a, c)

        val graphB = Graph()
        val d = graphB.addVertex("D")
        val e = graphB.addVertex("E")
        val f = graphB.addVertex("F")

        val e4 = graphB.addEdge("4", "assoc", d, e)
        val e5 = graphB.addEdge("5", "assoc", e, f)
        val e6 = graphB.addEdge("6", "inherit", d, f)

        val matcher = GraphMatcher(graphA, graphB)

        val matchings = matcher.match(a, d)
        assertEquals(3, matchings.size)
    }

    @Test
    fun complexTest() {
        val cocoFile = GraphVisualizeTest::class.java.getResourceAsStream("/sample/sample_coco_small.json")!!
        val multipleSketches: COCOData = createObjectMapper().readValue(cocoFile)
        val data = UMLDataAccessor(multipleSketches.image(0))
        val graphA = convertSketchToGraph(data.sketch())
        val graphB = convertSketchToGraph(data.sketch())

        val matcher = GraphMatcher(graphA, graphB)

        for (i in 0 until graphA.nodes().size) {
            val matchings = matcher.match(graphA.nodes()[i], graphB.nodes()[i])
            matchings.forEach { println(it) }
        }
    }

    @Test
    fun multiSeed() {
        val graphA = Graph()
        val a = graphA.addVertex("Important Node")
        val b = graphA.addVertex("Not a Node")
        val c = graphA.addVertex("Very very very important node")
        val x = graphA.addVertex("not important node")

        val e1 = graphA.addEdge("1", "assoc", a, b)
        val e2 = graphA.addEdge("2", "assoc", b, a)
        val e3 = graphA.addEdge("3", "assoc", x, c)

        val graphB = Graph()
        val d = graphB.addVertex("ImportantNode")
        val e = graphB.addVertex("E")
        val f = graphB.addVertex("F")
        val y = graphB.addVertex("NotImportantNode")

        val e4 = graphB.addEdge("4", "assoc", d, e)
        val e5 = graphB.addEdge("5", "assoc", e, d)
        val e6 = graphB.addEdge("6", "assoc", f, y)

        val matcher = GraphMatcher(graphA, graphB)

        val matchings = matcher.matchUntilNoSeeds(SeedGenerator().generateSeeds(graphA, graphB))
        assertEquals(4, matchings.size)
    }
}
