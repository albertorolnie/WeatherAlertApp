// BluetoothSender.java
package com.example.weatheralertapp

import android.Manifest
import android.bluetooth.BluetoothAdapter
import androidx.annotation.RequiresPermission
import java.util.UUID

class BluetoothSender {
    private var bluetoothAdapter: BluetoothAdapter? = null

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun sendData(data: String) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter != null && bluetoothAdapter!!.isEnabled) {
            val pairedDevices = bluetoothAdapter!!.bondedDevices
            for (device in pairedDevices) {
                try {
                    val socket =
                        device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
                    socket.connect()
                    val outputStream = socket.outputStream
                    outputStream.write(data.toByteArray())
                    outputStream.close()
                    socket.close()
                    break
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}