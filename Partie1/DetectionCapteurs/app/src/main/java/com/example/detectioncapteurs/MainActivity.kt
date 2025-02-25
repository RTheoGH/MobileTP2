package com.example.detectioncapteurs

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.detectioncapteurs.ui.theme.DetectionCapteursTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DetectionCapteursTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text("Détections capteurs")
                            }
                        )
                    }
                ) { innerPadding ->
                    DetecCapteurs(innerPadding)
                }
            }
        }
    }
}

@Composable
fun DetecCapteurs(innerPadding: PaddingValues) {
    val ctx = LocalContext.current
    // On crée une variable capteurService qui sera notre capteur "général"
    val capteurService: SensorManager? = ctx.getSystemService(Context.SENSOR_SERVICE) as? SensorManager

    // On teste sur ces capteurs par exemple
    val capteurs = listOf(
        Pair(Sensor.TYPE_PRESSURE,"Pressure Sensor"),
        Pair(Sensor.TYPE_ACCELEROMETER,"Accelerometer"),
        Pair(Sensor.TYPE_GYROSCOPE,"Gyroscope"),
        Pair(Sensor.TYPE_MAGNETIC_FIELD,"Magnetic Field Sensor"),
        Pair(Sensor.TYPE_LIGHT,"Light Sensor")
    )

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        LazyColumn {
            items(capteurs) { (type,name) ->
                CapteurCard(capteurService,type,name)
            }
        }
    }
}

@Composable
fun CapteurCard(capteurService: SensorManager?, type: Int, name: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = if(capteurService?.getDefaultSensor(type) != null){
                "$name est disponible."
            } else {
                "$name n'est pas disponible."
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}