package edu.kit.kastel.lissa.swa.combinator

import edu.kit.kastel.informalin.framework.models.java.JavaProject
import edu.kit.kastel.informalin.framework.models.java_x_pcm.JavaPCMConnectorImpl
import edu.kit.kastel.lissa.swa.pcm.PalladioComponentModel
import edu.kit.kastel.lissa.utils.createObjectMapper
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.charset.StandardCharsets

@Disabled("Not a test!")
class JavaPCMGeneratorTest {
    @Test
    fun generateCombinationTeammates() {
        val pcmModel = PalladioComponentModel("../benchmark/teammates/pcm/teammates.repository")
        val javaModel =
            createObjectMapper().readValue(File("../benchmark/teammates/code-java.json"), JavaProject::class.java)

        val map = JavaPCMConnectorImpl(javaModel, pcmModel.model())

        javaModel.classes.forEach {
            when (it.fullyQualifiedName.split(".")[1]) {
                "client" -> map.addClassToComponent(it, pcmModel.components().find { c -> c.entityName == "Client" })
                "ui" -> map.addClassToComponent(it, pcmModel.components().find { c -> c.entityName == "UI" })
                "e2e" -> map.addClassToComponent(it, pcmModel.components().find { c -> c.entityName == "E2E" })
                "common" -> map.addClassToComponent(it, pcmModel.components().find { c -> c.entityName == "Common" })
                "logic" -> map.addClassToComponent(it, pcmModel.components().find { c -> c.entityName == "Logic" })
                "storage" -> map.addClassToComponent(it, pcmModel.components().find { c -> c.entityName == "Storage" })
                "test" -> map.addClassToComponent(it, pcmModel.components().find { c -> c.entityName == "Test Driver" })
                "lnp" -> map.addClassToComponent(it, pcmModel.components().find { c -> c.entityName == "Test Driver" })
                "architecture" -> map.addClassToComponent(
                    it, // teammates.architecture.ArchitectureTest
                    pcmModel.components().find { c -> c.entityName == "Test Driver" }
                )
                "main" -> {}
                else -> error("No mapping for class ${it.fullyQualifiedName}")
            }
        }

        File("src/test/resources/goldstandards/teammates-java2pcm.json").writeText(
            map.saveToString(),
            charset = StandardCharsets.UTF_8
        )
    }
}
