package com.example.udp_server_app.ui.client

import com.example.udp_server_app.Logger
import java.io.IOException
import java.net.*

class UdpClientThread(
    private val dstAddress: String,
    private val dstPort: Int,
) : Thread() {

    var running: Boolean = false
    var socket: DatagramSocket? = null
    lateinit var address: InetAddress

    private fun sendState(msg: String) {

    }

    override fun run() {
        sendState("connecting")

        running = true

        try {
            address = InetAddress.getByName(dstAddress)
            socket = DatagramSocket()

            socket?.let { socket ->
                // send request
                var buffer = ByteArray(256)
                Logger("$buffer")
                var packet = DatagramPacket(buffer, buffer.size, address, dstPort)
                socket.send(packet)

                sendState("connected")

                // get response
                packet = DatagramPacket(buffer, buffer.size)
                socket.receive(packet)

                val msg = String(packet.data)

            }

        } catch (e: SocketException) {
            Logger("SocketException ${e.stackTrace}, ${e.message}")
        } catch (e: UnknownHostException) {
            Logger("UnknownHostException ${e.stackTrace}, ${e.message}")
        } catch (e: IOException) {
            Logger("IOException ${e.stackTrace}, ${e.message}")
        } finally {
            socket?.close()

        }
    }
}