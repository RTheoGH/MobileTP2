package com.example.pays_fragment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
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
    val population: Long,
    val langue: String,
    val monnaie: String,
    val pib: String
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
        Pays(R.drawable.france, "France", "Paris", 643801.0, 67372000, "Français", "Euro (€)", "2,78 trillions USD"),
        Pays(R.drawable.espagne, "Espagne", "Madrid", 505992.0, 46719142, "Espagnol", "Euro (€)", "1,44 trillions USD"),
        Pays(R.drawable.allemagne, "Allemagne", "Berlin", 357022.0, 83166711, "Allemand", "Euro (€)", "4,22 trillions USD"),
        Pays(R.drawable.italie, "Italie", "Rome", 301340.0, 60262770, "Italien", "Euro (€)", "2,01 trillions USD"),
        Pays(R.drawable.belgique, "Belgique", "Bruxelles", 30528.0, 11589623, "Français, Néerlandais, Allemand", "Euro (€)", "594 milliards USD"),
        Pays(R.drawable.portugal, "Portugal", "Lisbonne", 92212.0, 10295909, "Portugais", "Euro (€)", "255 milliards USD"),
        Pays(R.drawable.suisse, "Suisse", "Berne", 41285.0, 8734867, "Français, Allemand, Italien, Romanche", "Franc Suisse (CHF)", "807 milliards USD"),
        Pays(R.drawable.states, "États-Unis", "Washington D.C.", 9833517.0, 331002651, "Anglais", "Dollar ($)", "26,9 trillions USD"),
        Pays(R.drawable.mexique, "Mexique", "Mexico", 1964375.0, 128932753, "Espagnol", "Peso mexicain (MXN)", "1,42 trillions USD"),
        Pays(R.drawable.russie, "Russie", "Moscou", 17098242.0, 144104080, "Russe", "Rouble (RUB)", "1,78 trillions USD"),
        Pays(R.drawable.chine, "Chine", "Pékin", 9596961.0, 1411778724, "Mandarin", "Yuan (CNY)", "17,7 trillions USD"),
        Pays(R.drawable.japon, "Japon", "Tokyo", 377975.0, 125800000, "Japonais", "Yen (¥)", "4,9 trillions USD"),
        Pays(R.drawable.afrique_du_sud, "Afrique du Sud", "Pretoria", 1221037.0, 60041996, "Anglais, Afrikaans, Zoulou + 8 autres", "Rand (ZAR)", "399 milliards USD")
    )

    var selectedPays by remember { mutableStateOf<Pays?>(null) }
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(216, 224, 253, 255))
                .padding(8.dp)
                .horizontalScroll(scrollState)
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
        PaysDesc(selectedPays) // On appelle PaysDesc qui va afficher le pays choisi
    }
}

@Composable
fun PaysDesc(pays: Pays?){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if(pays != null){ // Si on a sélectionné un pays
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
                modifier = Modifier.height(150.dp).size(500.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(216, 224, 253, 255))
                    .padding(16.dp)
            ) {
                Text("Capitale : ${pays.capitale}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Superficie : ${pays.superficie} km²")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Population : ${pays.population} habitants")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Langue(s) : ${pays.langue}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Monnaie : ${pays.monnaie}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("PIB : ${pays.pib}")
            }
        }else{
            Text("Choisir un pays.")
        }
    }
}
