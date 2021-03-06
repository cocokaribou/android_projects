package com.example.udp_server_app.ui.server

import com.example.udp_server_app.Logger
import java.io.IOException
import java.net.*
import java.text.SimpleDateFormat
import java.util.*

class UdpServerThread(private val serverPort: Int) : Thread() {
    var socket: DatagramSocket? = null
    var running: Boolean = false


    override fun run() {
        running = true

        try {
            ServerActivity.instance.updateState("Starting UDP Server")
            socket = DatagramSocket(serverPort)

            ServerActivity.instance.updateState("UDP Server now running")

            while (running) {
                var buffer = ByteArray(256)

                // receive request
                socket?.let { socket ->
                    var packet = DatagramPacket(buffer, buffer.size)
                    socket.receive(packet) // block the program flow

                    // send the response to the client at "address" and "port"
                    val address = packet.address
                    val port = packet.port

                    ServerActivity.instance.updatePrompt("Request from $address:$port\n")

                    val date = "${SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date())}"
                    buffer = date.toByteArray(Charsets.UTF_8) // java String.getBytes()
                    packet = DatagramPacket(buffer, buffer.size, address, port)
                    socket.send(packet)
                }

            }
            Logger("UDP Server ended")
        } catch (e: SocketException) {
            Logger("SocketException ${e.stackTrace}")
        } catch (e: IOException) {
            Logger("IOException ${e.stackTrace}")
        } finally {
            socket?.let {
//                GlobalScope.launch(Dispatchers.IO) {
//                    coroutineScope {
//                        delay(4000L)
//                        it.close()
//                        Logger("socket closed after 4 sec")
//                    }
//                }
                it.close()
            }
        }
    }
}