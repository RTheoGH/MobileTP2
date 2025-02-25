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
import kotlin.math.abs

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
                                Text("Direction du Téléphone")
                            }
                        )
                    }
                ) { innerPadding ->
                    Direction(innerPadding)
                }
            }
        }
    }
}

@Composable
fun Direction(innerPadding: PaddingValues) {
    val ctx = LocalContext.current
    val sensorManager = ctx.getSystemService(SENSOR_SERVICE) as SensorManager
    val directionSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) // On utilise LINEAR pour avoir des mouvements linéaires

    var direction by remember { mutableStateOf("Stable") }

    val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                val x = it.values[0]
                val y = it.values[1]

                direction = when {
                    abs(x) > abs(y) && x < -2 -> "Droite"
                    abs(x) > abs(y) && x > 2 -> "Gauche"
                    abs(y) > abs(x) && y > 2 -> "Haut"
                    abs(y) > abs(x) && y < -2 -> "Bas"
                    else -> "Stable"
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    LaunchedEffect(Unit) {
        sensorManager.registerListener(
            sensorEventListener, directionSensor, SensorManager.SENSOR_DELAY_UI
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
