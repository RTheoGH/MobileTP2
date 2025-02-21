package com.example.pays_liste

import android.content.Intent
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pays_liste.ui.theme.Pays_listeTheme
import java.io.Serializable
import kotlin.math.exp

data class Pays(
    val imageRes: Int,
    val nom: String,
    val capitale: String,
    val superficie: Double,
    val population: Long
) : Serializable

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pays_listeTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = { Text("Sélecteur de Pays") }
                        )
                    }
                ) { innerPadding ->
                    Pays(innerPadding) { pays ->
                        val intent = Intent(this, SecondActivity::class.java).apply {
                            putExtra("EXTRA_PAYS", pays)
                        }
                        startActivity(intent)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pays(innerPadding : PaddingValues,onPaysClick: (Pays) -> Unit) {
    val pays = listOf(
        Pays(R.drawable.france,"France", "Paris", 643801.0, 67372000),
        Pays(R.drawable.espagne,"Espagne", "Madrid", 505992.0, 46719142),
        Pays(R.drawable.allemagne,"Allemagne", "Berlin", 357022.0, 83166711),
        Pays(R.drawable.italie,"Italie", "Rome", 301340.0, 60262770),
        Pays(R.drawable.belgique,"Belgique", "Bruxelles", 30528.0, 11589623)
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedPays by remember { mutableStateOf<Pays?>(null) }

    Column(modifier = Modifier.padding(innerPadding).padding(16.dp))
    {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = selectedPays?.nom ?: "",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                label = { Text("Sélectionnez un pays") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                pays.forEach { pays ->
                    DropdownMenuItem(
                        text = { Text(pays.nom) },
                        onClick = {
                            selectedPays = pays
                            expanded = false
                            onPaysClick(pays)
                        }
                    )
                }
            }
        }
    }
}
