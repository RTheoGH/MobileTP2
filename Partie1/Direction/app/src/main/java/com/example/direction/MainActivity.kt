package com.example.direction

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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
import com.example.direction.ui.theme.DirectionTheme
import android.hardware.Sensor.TYPE_ROTATION_VECTOR
import android.hardware.SensorManager.getRotationMatrixFromVector
import android.hardware.SensorManager.getOrientation
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DirectionTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text("Rotation du Téléphone")
                            }
                        )
                    }
                ) { innerPadding ->
                    RotationSensor(innerPadding)
                }
            }
        }
    }
}

@Composable
fun RotationSensor(innerPadding: PaddingValues) {
    val ctx = LocalContext.current
    val sensorManager = ctx.getSystemService(SENSOR_SERVICE) as SensorManager
    val rotationSensor = sensorManager.getDefaultSensor(TYPE_ROTATION_VECTOR)

    var direction by remember { mutableStateOf("Stable") }

    val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                val rotationMatrix = FloatArray(9)
                getRotationMatrixFromVector(rotationMatrix, it.values)
                val orientationValues = FloatArray(3)
                getOrientation(rotationMatrix, orientationValues)
                val pos = Math.toDegrees(orientationValues[0].toDouble()).roundToInt()

                direction = when {
                    pos in 35..135 -> "haut"
                    pos in 115..255 -> "droite"
                    pos in 255..345 -> "bas"
                    else -> "gauche"
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    LaunchedEffect(Unit) {
        sensorManager.registerListener(
            sensorEventListener, rotationSensor, SensorManager.SENSOR_DELAY_UI
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            sensorManager.unregisterListener(sensorEventListener)
        }
    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(text = "Direction: $direction", modifier = Modifier.padding(16.dp))
    }
}
