package com.example.udp_client_app

import android.os.Message
import java.io.IOException
import java.net.*

class UdpClientThread(
    private val dstAddress: String,
    private val dstPort: Int,
    private val handler: UdpClientHandler
) : Thread() {

    var running: Boolean = false
    var socket: DatagramSocket? = null
    lateinit var address: InetAddress

    private fun sendState(msg: String) {
        handler.sendMessage(Message.obtain(handler, UdpClientHandler.UPDATE_STATE, msg))
    }

    override fun run() {
        sendState("connecting")

        running = true

        try {
            address = InetAddress.getByName(dstAddress)
            socket = DatagramSocket()

            socket?.let { socket ->
                // send request
                var buffer = byteArrayOf()
                var packet = DatagramPacket(buffer, buffer.size, address, dstPort)
                socket.send(packet)

                sendState("connected")

                // get response
                packet = DatagramPacket(buffer, buffer.size)
                socket.receive(packet)
                val msg = "${packet.data}"

                handler.sendMessage(Message.obtain(handler, UdpClientHandler.UPDATE_MSG, msg))
            }

        } catch (e: SocketException) {
            Logger("SocketException ${e.stackTrace}, ${e.message}")
        } catch (e: UnknownHostException) {
            Logger("UnknownHostException ${e.stackTrace}, ${e.message}")
        } catch (e: IOException) {
            Logger("IOException ${e.stackTrace}, ${e.message}")
        } finally {
            socket?.close()
            handler.sendEmptyMessage(UdpClientHandler.UPDATE_END)
        }
    }
}