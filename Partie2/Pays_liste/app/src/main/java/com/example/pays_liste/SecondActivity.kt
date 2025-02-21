package com.example.pays_liste

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pays_liste.ui.theme.Pays_listeTheme

class SecondActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pays = intent.getSerializableExtra("EXTRA_PAYS") as? Pays ?: Pays(0, "Inconnu","Inconnu", 0.0, 0)

        setContent {
            Pays_listeTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = { Text(pays.nom) }
                        )
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding).padding(16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = pays.imageRes),
                            contentDescription = null,
                            modifier = Modifier.height(200.dp).size(500.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Capitale: ${pays.capitale}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Superficie: ${pays.superficie} km²")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Population: ${pays.population}")
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = { finish() }) {
                            Text("Retour")
                        }
                    }
                }
            }
        }
    }
}