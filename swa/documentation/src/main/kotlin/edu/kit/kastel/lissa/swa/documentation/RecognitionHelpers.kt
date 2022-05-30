package edu.kit.kastel.lissa.swa.documentation

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.io.InputStream
import java.io.OutputStream
import javax.imageio.ImageIO

private val colors = listOf(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE, Color.BLACK, Color.ORANGE)

fun visualize(imageStream: InputStream, recognitionResult: RecognitionResult, destination: OutputStream) {
    val image = ImageIO.read(imageStream)
    val g2d: Graphics2D = image.createGraphics()
    g2d.stroke = BasicStroke(2F)

    val colorMap = mutableMapOf<String, Color>()
    var currentColor = 0;
    for (box in recognitionResult.boxes) {
        if (!colorMap.containsKey(box.classification)) {
            colorMap[box.classification] = colors[currentColor]!!
            currentColor++;
        }
        g2d.color = colorMap[box.classification]
        val coordinates = box.box
        g2d.drawRect(coordinates[0], coordinates[1], coordinates[2] - coordinates[0], coordinates[3] - coordinates[1])
    }
    g2d.dispose()
    ImageIO.write(image, "png", destination)
}
