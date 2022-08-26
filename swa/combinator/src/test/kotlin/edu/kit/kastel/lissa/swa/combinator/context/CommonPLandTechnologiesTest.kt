package edu.kit.kastel.lissa.swa.combinator.context

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CommonPLandTechnologiesTest {
    private lateinit var commonPLandTechnologies: CommonPLandTechnologies

    @BeforeAll
    fun init() {
        commonPLandTechnologies = CommonPLandTechnologies()
    }

    @Test
    fun testPLs() {
        val languages = commonPLandTechnologies.programmingLanguages()
        Assertions.assertThat(languages).containsAll(listOf("Java", "Kotlin", "C++"))
    }

    @Test
    fun testFrameworks() {
        val webFramework = commonPLandTechnologies.webFrameworks()
        Assertions.assertThat(webFramework).containsAll(listOf("Spring Framework"))
        val javaFramework = commonPLandTechnologies.javaFrameworks()
        Assertions.assertThat(javaFramework).containsAll(listOf("Thymeleaf"))
    }
}
