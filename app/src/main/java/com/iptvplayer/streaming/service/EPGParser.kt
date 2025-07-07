package com.iptvplayer.streaming.service

import com.iptvplayer.streaming.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class EPGParser {
    
    companion object {
        private val dateFormat = SimpleDateFormat("yyyyMMddHHmmss Z", Locale.US)
    }
    
    suspend fun parseEPG(url: String): EPGData = withContext(Dispatchers.IO) {
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.connectTimeout = 15000
            connection.readTimeout = 60000
            
            val inputStream = connection.inputStream
            val content = inputStream.bufferedReader().readText()
            inputStream.close()
            
            parseEPGContent(content)
        } catch (e: Exception) {
            e.printStackTrace()
            EPGData(emptyMap())
        }
    }
    
    suspend fun parseEPGContent(xmlContent: String): EPGData = withContext(Dispatchers.Default) {
        try {
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(StringReader(xmlContent))
            
            val channels = mutableMapOf<String, MutableList<EPGProgram>>()
            var eventType = parser.eventType
            
            var currentChannelId = ""
            var currentTitle = ""
            var currentDesc = ""
            var currentStart = 0L
            var currentStop = 0L
            var currentCategory = ""
            var currentIcon = ""
            
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        when (parser.name) {
                            "programme" -> {
                                currentChannelId = parser.getAttributeValue(null, "channel") ?: ""
                                currentStart = parseDate(parser.getAttributeValue(null, "start"))
                                currentStop = parseDate(parser.getAttributeValue(null, "stop"))
                                currentTitle = ""
                                currentDesc = ""
                                currentCategory = ""
                                currentIcon = ""
                            }
                            "title" -> {
                                parser.next()
                                if (parser.eventType == XmlPullParser.TEXT) {
                                    currentTitle = parser.text ?: ""
                                }
                            }
                            "desc" -> {
                                parser.next()
                                if (parser.eventType == XmlPullParser.TEXT) {
                                    currentDesc = parser.text ?: ""
                                }
                            }
                            "category" -> {
                                parser.next()
                                if (parser.eventType == XmlPullParser.TEXT) {
                                    currentCategory = parser.text ?: ""
                                }
                            }
                            "icon" -> {
                                currentIcon = parser.getAttributeValue(null, "src") ?: ""
                            }
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if (parser.name == "programme" && currentChannelId.isNotEmpty()) {
                            val program = EPGProgram(
                                channelId = currentChannelId,
                                title = currentTitle,
                                description = currentDesc.takeIf { it.isNotEmpty() },
                                startTime = currentStart,
                                endTime = currentStop,
                                category = currentCategory.takeIf { it.isNotEmpty() },
                                icon = currentIcon.takeIf { it.isNotEmpty() },
                                isLive = isCurrentlyLive(currentStart, currentStop)
                            )
                            
                            channels.getOrPut(currentChannelId) { mutableListOf() }.add(program)
                        }
                    }
                }
                eventType = parser.next()
            }
            
            EPGData(channels)
        } catch (e: Exception) {
            e.printStackTrace()
            EPGData(emptyMap())
        }
    }
    
    private fun parseDate(dateStr: String?): Long {
        return try {
            dateStr?.let { dateFormat.parse(it)?.time } ?: 0L
        } catch (e: Exception) {
            0L
        }
    }
    
    private fun isCurrentlyLive(startTime: Long, endTime: Long): Boolean {
        val now = System.currentTimeMillis()
        return now in startTime..endTime
    }
    
    fun getCurrentProgram(channelId: String, epgData: EPGData): EPGProgram? {
        val now = System.currentTimeMillis()
        return epgData.channels[channelId]?.find { program ->
            now in program.startTime..program.endTime
        }
    }
    
    fun getNextPrograms(channelId: String, epgData: EPGData, count: Int = 5): List<EPGProgram> {
        val now = System.currentTimeMillis()
        return epgData.channels[channelId]
            ?.filter { it.startTime > now }
            ?.sortedBy { it.startTime }
            ?.take(count) ?: emptyList()
    }
}
