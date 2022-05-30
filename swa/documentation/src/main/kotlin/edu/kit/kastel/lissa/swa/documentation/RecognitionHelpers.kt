package edu.kit.kastel.lissa.swa.documentation

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.io.InputStream
import java.io.OutputStream
import java.lang.Double.max
import java.lang.Double.min
import javax.imageio.ImageIO

private val colors = listOf(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE, Color.BLACK, Color.ORANGE)

fun visualize(imageStream: InputStream, recognitionResult: SketchRecognitionResult, destination: OutputStream) {
    val image = ImageIO.read(imageStream)
    val g2d: Graphics2D = image.createGraphics()
    g2d.stroke = BasicStroke(2F)

    val colorMap = mutableMapOf<String, Color>()
    var currentColor = 0
    for (box in recognitionResult.boxes) {
        if (!colorMap.containsKey(box.classification)) {
            colorMap[box.classification] = colors[currentColor]!!
            currentColor++
        }
        g2d.color = colorMap[box.classification]
        val coordinates = box.box
        g2d.drawRect(coordinates[0], coordinates[1], coordinates[2] - coordinates[0], coordinates[3] - coordinates[1])
    }
    g2d.dispose()
    ImageIO.write(image, "png", destination)
}

data class BoundingBox(val x1: Double, val y1: Double, val x2: Double, val y2: Double) {
    fun iou(bb: BoundingBox) = intersectionOverUnion(this, bb)
}

fun intersectionOverUnion(bb1: BoundingBox, bb2: BoundingBox): Double {
    val xLeft = max(bb1.x1, bb2.x1)
    val yTop = max(bb1.y1, bb2.y1)
    val xRight = min(bb1.x2, bb2.x2)
    val yBottom = min(bb1.y2, bb2.y2)

    if (xRight < xLeft || yBottom < yTop) return 0.0

    val intersectionArea = (xRight - xLeft) * (yBottom - yTop)

    val bb1Area = (bb1.x2 - bb1.x1) * (bb1.y2 - bb1.y1)
    val bb2Area = (bb2.x2 - bb2.x1) * (bb2.y2 - bb2.y1)

    return intersectionArea / (bb1Area + bb2Area - intersectionArea)
}

fun <E : Number> List<E>.bb(relative: Boolean = false): BoundingBox {
    if (this.size != 4)
        error("List has to contain 4 elements: x1,y1,x2,y2")
    if (relative)
        return BoundingBox(
            this[0].toDouble(),
            this[1].toDouble(),
            this[2].toDouble() - this[0].toDouble(),
            this[3].toDouble() - this[1].toDouble()
        )
    return BoundingBox(this[0].toDouble(), this[1].toDouble(), this[2].toDouble(), this[3].toDouble())
}
