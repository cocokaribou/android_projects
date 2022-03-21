package com.example.udp_server_app.ui.client

import com.example.udp_server_app.Logger
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException

class TcpClientHandler(
    private val dataInputStream: DataInputStream,
    private val dataOutputStream: DataOutputStream

): Thread() {

    override fun run() {
        while(true) {
            try {
                if(dataInputStream.available() > 0) {
                    Logger("Received: "+dataInputStream.readUTF())
                    dataOutputStream.writeUTF("Hello Client")
                    sleep(2000L)
                }
            } catch (e: IOException) {
                Logger("${e.message}, ${e.stackTrace}")
                try {
                    dataInputStream.close()
                    dataOutputStream.close()
                } catch (e: IOException) {
                    Logger("${e.message}, ${e.stackTrace}")
                }
            } catch (e: InterruptedException) {
                Logger("${e.message}, ${e.stackTrace}")
                try {
                    dataInputStream.close()
                    dataOutputStream.close()
                } catch (e: IOException) {
                    Logger("${e.message}, ${e.stackTrace}")
                }
            }
        }
    }
}