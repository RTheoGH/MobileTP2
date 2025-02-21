package com.example.secouer

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.secouer.ui.theme.SecouerTheme
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SecouerTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text("Secoue ton téléphone !!")
                            }
                        )
                    }
                ) { innerPadding ->
                    ShakeToFlash(innerPadding)
                }
            }
        }
    }
}

@Composable
fun ShakeToFlash(innerPadding: PaddingValues) {
    val ctx = LocalContext.current
    val sensorManager = ctx.getSystemService(SENSOR_SERVICE) as SensorManager
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    val cameraManager = ctx.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    val cameraId = cameraManager.cameraIdList.firstOrNull()

    var flash by remember { mutableStateOf(false) }
    var Shake by remember { mutableStateOf(0L) }

    val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                val x = it.values[0]
                val y = it.values[1]
                val z = it.values[2]

                val acceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
                val currentTime = System.currentTimeMillis()

                if (acceleration > 15 && currentTime - Shake > 1000) {
                    Shake = currentTime
                    flash = !flash
                    toggleFlash(flash, cameraManager, cameraId)
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    LaunchedEffect(Unit) {
        sensorManager.registerListener(
            sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_GAME
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            sensorManager.unregisterListener(sensorEventListener)
            toggleFlash(false, cameraManager, cameraId)
        }
    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(text = "Flash: ${if (flash) "Allumé" else "Éteint"}", modifier = Modifier.padding(16.dp))
    }
}

fun toggleFlash(turnOn: Boolean, cameraManager: CameraManager, cameraId: String?) {
    cameraId?.let {
        try {
            cameraManager.setTorchMode(it, turnOn)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
