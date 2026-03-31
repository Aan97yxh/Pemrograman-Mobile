package com.contoh.kelompok6

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DeviceInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_device_info)
        val tvDeviceInfo = findViewById<TextView>(R.id.tvDeviceInfo)
        val btnBack = findViewById<Button>(R.id.btnBack)

        val androidVersion = Build.VERSION.RELEASE
        val deviceModel = Build.MODEL
        val manufacturer = Build.MANUFACTURER
        val kernelVersion = System.getProperty("os.version") ?: "Unknown"
        val batteryLevel = getBatteryLevel()
        val networkStatus = getNetworkStatus()

        val infoText = """
            Android Versi: $androidVersion
            Model: $deviceModel
            Produsen: $manufacturer
            Kernel: $kernelVersion
            Baterai: $batteryLevel
            Jaringan: $networkStatus
        """.trimIndent()

        tvDeviceInfo.text = infoText
        btnBack.setOnClickListener {
            finish()
        }
    }
    private fun getBatteryLevel(): String {
        val batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        val batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        return "$batteryLevel%"
    }

    private fun getNetworkStatus(): String {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return "Offline"
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return "Offline"

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "Wi-Fi Terhubung"
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Seluler Terhubung"
            else -> "Offline"
        }
    }
}