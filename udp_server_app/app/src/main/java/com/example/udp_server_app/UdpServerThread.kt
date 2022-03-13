package com.example.udp_server_app

import java.io.IOException
import java.net.*
import java.util.*

class UdpServerThread(private val serverPort: Int) : Thread() {
    var socket: DatagramSocket? = null
    var running: Boolean = false


    override fun run() {
        running = true

        try {
            MainActivity.instance.updateState("Starting UDP Server")
            socket = DatagramSocket(serverPort)

            MainActivity.instance.updateState("UDP Server now running")

            while (running) {
                var buffer = byteArrayOf()

                // receive request
                socket?.let { socket ->
                    var packet = DatagramPacket(buffer, buffer.size)
                    socket.receive(packet) // block the program flow

                    // send the response to the client at "address" and "port"
                    val address: InetAddress = packet.address
                    val port = packet.port

                    MainActivity.instance.updatePrompt("Request from $address:$port\n")

                    val date = Date().toString()
                    buffer = date.toByteArray()
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
                it.close()
                Logger("Socket closed")
            }
        }
    }
}