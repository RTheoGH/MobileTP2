package com.example.accelerometre

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.accelerometre.ui.theme.AccelerometreTheme
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AccelerometreTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text("Accélérometre")
                            }
                        )
                    }
                ) { innerPadding ->
                    Accelerometer(innerPadding)
                }
            }
        }
    }
}

@Composable
fun Accelerometer(innerPadding:PaddingValues){

    val ctx = LocalContext.current
    val sensorManager = ctx.getSystemService(SENSOR_SERVICE) as SensorManager
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    var backgroundColor by remember { mutableStateOf(Color.Yellow) }
    var cpt by remember { mutableFloatStateOf(0f) }

    val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                val x = it.values[0]
                val y = it.values[1]
                val z = it.values[2]

                val force = sqrt((x*x+y*y+z*z).toDouble()).toFloat()
                cpt = force

                backgroundColor = when {
                    force < 15 -> Color.Green
                    force in 15.0..30.0 -> Color.Yellow
                    else -> Color.Red
                }
            }
        }

        override fun onAccuracyChanged(s: Sensor?, p1: Int) {}
    }

    LaunchedEffect(Unit) {
        sensorManager.registerListener(
            sensorEventListener,accelerometer,SensorManager.SENSOR_DELAY_GAME
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            sensorManager.unregisterListener(sensorEventListener)
        }
    }

    Column(
        modifier = Modifier.padding(innerPadding).fillMaxSize().background(backgroundColor)
    ) {
        Text(text = cpt.toString())
    }
}
