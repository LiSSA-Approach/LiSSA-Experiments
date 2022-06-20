package edu.kit.kastel.lissa.swa.elementlabeler.services

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.readValue
import edu.kit.kastel.lissa.utils.createObjectMapper
import org.apache.hc.client5.http.classic.methods.HttpGet
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Common Programming Languages and Technologies Service.
 */
class CommonPLandTechnologies {
    companion object {
        const val URL_TO_WIKI_PL =
            "https://en.wikipedia.org/w/api.php?action=parse&format=json&page=List_of_programming_languages&prop=links"
        const val URL_TO_WIKI_WEB_FRAMEWORKS =
            "https://en.wikipedia.org/w/api.php?action=parse&format=json&page=Comparison_of_server-side_web_frameworks&prop=links"
        const val URL_TO_WIKI_JAVA_FRAMEWORKS =
            "https://en.wikipedia.org/w/api.php?action=parse&format=json&page=List_of_Java_frameworks&prop=links"
        val logger: Logger = LoggerFactory.getLogger(CommonPLandTechnologies::class.java)
    }

    private val oom = createObjectMapper()
    private lateinit var programmingLanguages: List<String>
    private lateinit var webFrameworks: List<String>
    private lateinit var javaFrameworks: List<String>

    fun programmingLanguages(): List<String> = programmingLanguages
    fun webFrameworks(): List<String> = webFrameworks
    fun javaFrameworks(): List<String> = javaFrameworks

    init {
        loadPL()
        loadWebFrameworks()
        loadJavaFrameworks()
    }

    private fun loadPL() {
        var programmingLanguages = loadTexts(URL_TO_WIKI_PL)
        programmingLanguages = programmingLanguages.map { lnk -> lnk.replace("(programming language)", "").trim() }
        programmingLanguages = programmingLanguages.filter { lnk -> lnk.matches(Regex("[A-Za-z0-9 _+.]+")) }
        this.programmingLanguages = programmingLanguages.toSortedSet().toList()
    }

    private fun loadWebFrameworks() {
        var webFrameworks = loadTexts(URL_TO_WIKI_WEB_FRAMEWORKS)
        webFrameworks = webFrameworks.map { lnk -> lnk.replace("(framework)", "").trim() }
        webFrameworks = webFrameworks.map { lnk -> lnk.replace("(toolkit)", "").trim() }
        webFrameworks = webFrameworks.map { lnk -> lnk.replace("(software)", "").trim() }
        webFrameworks = webFrameworks.filter { lnk -> lnk.matches(Regex("[A-Za-z0-9 _+.]+")) }
        this.webFrameworks = webFrameworks.toSortedSet().toList()
    }

    private fun loadJavaFrameworks() {
        var javaFrameworks = loadTexts(URL_TO_WIKI_JAVA_FRAMEWORKS)
        javaFrameworks = javaFrameworks.map { lnk -> lnk.replace("(framework)", "").trim() }
        javaFrameworks = javaFrameworks.map { lnk -> lnk.replace("(web framework)", "").trim() }
        javaFrameworks = javaFrameworks.map { lnk -> lnk.replace("(software)", "").trim() }
        javaFrameworks = javaFrameworks.filter { lnk -> lnk.matches(Regex("[A-Za-z0-9 _+.]+")) }
        this.javaFrameworks = javaFrameworks.toSortedSet().toList()
    }

    private fun loadTexts(url: String): List<String> {
        HttpClients.createDefault().use {
            val response = it.execute(HttpGet(url))
            if (response.code != 200) {
                logger.error("Request to Wiki failed with code ${response.code}")
                return emptyList()
            }

            var links: List<String> =
                oom.readValue<WikiParseResult>(response.entity.content).parse?.links?.map { lnk -> lnk.link }
                    ?: emptyList()
            logger.debug("Found ${links.size} possible results from $url")
            links = links.filter { lnk -> lnk.matches(Regex("[A-Za-z0-9 _+().]+")) }
            return links.toSortedSet().toList()
        }
    }

    data class WikiParseResult(
        @JsonProperty val parse: WikiParseResult? = null,
        val title: String? = null,
        val pageId: Int?,
        val links: List<WikiParseResultLink>? = null
    )

    data class WikiParseResultLink(
        @JsonProperty("*") val link: String
    )
}
