package com.example.listecapteurs

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.listecapteurs.ui.theme.ListeCapteursTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListeCapteursTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text("Liste de capteurs")
                            }
                        )
                    }

                ) { innerPadding ->
                    ListeCapteur(innerPadding)
                }
            }
        }
    }
}

@Composable
fun ListeCapteur(innerPadding:PaddingValues){
    val ctx = LocalContext.current
    val capteurService: SensorManager = ctx.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val liste_capteurs: List<Sensor> = capteurService.getSensorList(Sensor.TYPE_ALL) // On recupere la liste de tous les capteurs

    Column(
        modifier = Modifier.padding(innerPadding).padding(16.dp)
    ) {
        // On les affiche tous dans une liste d'objets
        LazyColumn {
            items(liste_capteurs){ capteur ->
                AfficheCapteur(capteur)
            }
        }
    }
}

@Composable
fun AfficheCapteur(capteur: Sensor){
    Card(
        modifier = Modifier.padding(8.dp).fillMaxSize()
    ){
        Text(
            text = "Nom : ${capteur.name}\nType : ${capteur.stringType}",
            modifier = Modifier.padding(16.dp)
        )
    }
}