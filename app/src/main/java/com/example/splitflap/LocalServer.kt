package com.example.splitflap

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.net.Inet4Address
import java.net.NetworkInterface

class LocalServer(private val onMessageReceived: (String) -> Unit) {

    private var server: NettyApplicationEngine? = null

    fun start() {
        server = embeddedServer(Netty, port = 8080) {
            install(ContentNegotiation) {
                json()
            }
            routing {
                post("/update-message") {
                    try {
                        val request = call.receive<MessageRequest>()
                        // Invoke callback. Note: This runs on a background thread.
                        onMessageReceived(request.text)
                        call.respond(mapOf("status" to "ok"))
                    } catch (e: Exception) {
                        call.respond(io.ktor.http.HttpStatusCode.BadRequest, mapOf("error" to e.localizedMessage))
                    }
                }
            }
        }.start(wait = false)
    }

    fun stop() {
        server?.stop(1000, 2000)
    }

    fun getIpAddress(): String {
         return try {
             NetworkInterface.getNetworkInterfaces().toList()
                 .flatMap { it.inetAddresses.toList() }
                 .filter { !it.isLoopbackAddress && it is Inet4Address }
                 .map { it.hostAddress }
                 .firstOrNull() ?: "Unavailable"
         } catch (e: Exception) {
             "Error"
         }
    }
}

@Serializable
data class MessageRequest(val text: String)
