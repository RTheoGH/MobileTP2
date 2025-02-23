package com.example.pays_fragment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.pays_fragment.ui.theme.Pays_fragmentTheme

data class Pays(
    val imageRes: Int,
    val nom: String,
    val capitale: String,
    val superficie: Double,
    val population: Long
)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pays_fragmentTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = { Text("Informations pays") }
                        )
                    }
                ) { innerPadding ->
                    Pays(innerPadding)
                }
            }
        }
    }
}

@Composable
fun Pays(innerPadding : PaddingValues) {
    val pays = listOf(
        Pays(R.drawable.france,"France", "Paris", 643801.0, 67372000),
        Pays(R.drawable.espagne,"Espagne", "Madrid", 505992.0, 46719142),
        Pays(R.drawable.allemagne,"Allemagne", "Berlin", 357022.0, 83166711),
        Pays(R.drawable.italie,"Italie", "Rome", 301340.0, 60262770),
        Pays(R.drawable.belgique,"Belgique", "Bruxelles", 30528.0, 11589623)
    )
    var selectedPays by remember { mutableStateOf<Pays?>(null) }

    Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(216, 224, 253, 255))
                .padding(8.dp)
        ){
            pays.forEach{p ->
                TextButton(
                    onClick = {
                        selectedPays = p
                    }
                ) {
                    Image(
                        painter = painterResource(id = p.imageRes),
                        contentDescription = null,
                        modifier = Modifier.height(30.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        PaysDesc(selectedPays)
    }
}

@Composable
fun PaysDesc(pays: Pays?){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if(pays != null){
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 8.em,
                fontWeight = FontWeight.ExtraBold,
                text = pays.nom
            )
            Spacer(modifier = Modifier.height(16.dp))
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
        }else{
            Text("choisie un pays")
        }
    }
}
