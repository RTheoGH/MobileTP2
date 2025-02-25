package com.example.proximity

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.proximity.ui.theme.ProximityTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProximityTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text("Rapproche ton téléphone")
                            }
                        )
                    }
                ) { innerPadding ->
                    Prox(innerPadding)
                }
            }
        }
    }
}

@Composable
fun Prox(innerPadding: PaddingValues) {
    val ctx = LocalContext.current
    val sensorManager = ctx.getSystemService(SENSOR_SERVICE) as SensorManager
    val proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) // On recupere le capteur de proximité

    var isFar by remember { mutableStateOf(true) }

    val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                val distance = it.values[0]
                isFar = distance >= 5.0
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    LaunchedEffect(Unit) {
        sensorManager.registerListener(
            sensorEventListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            sensorManager.unregisterListener(sensorEventListener)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        if (isFar) {
            Image(
                painter = painterResource(id = R.drawable.loin),
                contentDescription = "Objet loin",
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(.9f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Aucun objet ou trop loin."
            )
        }else{
            Image(
                painter = painterResource(id = R.drawable.proche),
                contentDescription = "Objet proche",

                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(.9f)
            )
            Spacer(modifier = Modifier.height(8.dp)
            )
            Text(text = "Objet proche.")
        }
    }
}

