package com.iptvplayer.streaming.service

import com.iptvplayer.streaming.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern

class M3UParser {
    
    companion object {
        private const val EXTM3U_HEADER = "#EXTM3U"
        private const val EXTINF_TAG = "#EXTINF:"
        private const val GROUP_TITLE_PATTERN = "group-title=\"([^\"]*)\""
        private const val TVG_LOGO_PATTERN = "tvg-logo=\"([^\"]*)\""
        private const val TVG_ID_PATTERN = "tvg-id=\"([^\"]*)\""
        private const val TVG_NAME_PATTERN = "tvg-name=\"([^\"]*)\""
        private const val RADIO_PATTERN = "radio=\"([^\"]*)\""
        private const val USER_AGENT_PATTERN = "user-agent=\"([^\"]*)\""
        private const val HTTP_REFERRER_PATTERN = "http-referrer=\"([^\"]*)\""
    }
    
    suspend fun parseM3U(url: String, userAgent: String? = null): M3UPlaylist = withContext(Dispatchers.IO) {
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            userAgent?.let { connection.setRequestProperty("User-Agent", it) }
            connection.connectTimeout = 10000
            connection.readTimeout = 30000
            
            val inputStream = connection.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            
            val content = reader.readText()
            reader.close()
            
            parseM3UContent(content, url)
        } catch (e: Exception) {
            e.printStackTrace()
            M3UPlaylist(emptyList(), sourceUrl = url)
        }
    }
    
    suspend fun parseM3UContent(content: String, sourceUrl: String? = null): M3UPlaylist = withContext(Dispatchers.Default) {
        val lines = content.lines()
        val entries = mutableListOf<M3UEntry>()
        
        if (lines.isEmpty() || !lines[0].startsWith(EXTM3U_HEADER)) {
            return@withContext M3UPlaylist(emptyList(), sourceUrl = sourceUrl)
        }
        
        var currentEntry: M3UEntry? = null
        var i = 1
        
        while (i < lines.size) {
            val line = lines[i].trim()
            
            when {
                line.startsWith(EXTINF_TAG) -> {
                    currentEntry = parseExtinfLine(line)
                }
                line.isNotEmpty() && !line.startsWith("#") && currentEntry != null -> {
                    val finalEntry = currentEntry.copy(url = line.trim())
                    entries.add(finalEntry)
                    currentEntry = null
                }
            }
            i++
        }
        
        M3UPlaylist(entries, sourceUrl = sourceUrl)
    }
    
    private fun parseExtinfLine(line: String): M3UEntry {
        val commaIndex = line.indexOf(',')
        val title = if (commaIndex != -1) line.substring(commaIndex + 1).trim() else ""
        
        val group = extractPattern(line, GROUP_TITLE_PATTERN)
        val logo = extractPattern(line, TVG_LOGO_PATTERN)
        val epgId = extractPattern(line, TVG_ID_PATTERN)
        val userAgent = extractPattern(line, USER_AGENT_PATTERN)
        val httpReferrer = extractPattern(line, HTTP_REFERRER_PATTERN)
        val isRadio = extractPattern(line, RADIO_PATTERN) == "true"
        
        // Detectar conteúdo adulto por palavras-chave
        val isAdult = detectAdultContent(title, group)
        
        // Extrair categorias
        val categories = mutableListOf<String>()
        group?.let { categories.add(it) }
        if (isRadio) categories.add("Radio")
        if (isAdult) categories.add("Adult")
        
        return M3UEntry(
            url = "",
            title = title,
            group = group,
            logo = logo,
            epgId = epgId,
            isRadio = isRadio,
            isAdult = isAdult,
            userAgent = userAgent,
            httpReferrer = httpReferrer,
            categories = categories
        )
    }
    
    private fun extractPattern(text: String, pattern: String): String? {
        val matcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(text)
        return if (matcher.find()) matcher.group(1) else null
    }
    
    private fun detectAdultContent(title: String, group: String?): Boolean {
        val adultKeywords = listOf("xxx", "adult", "18+", "+18", "porn", "erotic", "sexy", "hot")
        val content = "$title ${group ?: ""}".lowercase()
        return adultKeywords.any { content.contains(it) }
    }
}
