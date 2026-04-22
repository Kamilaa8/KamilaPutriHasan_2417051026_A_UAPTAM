package com.example.utptam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.utptam.model.FotoSource
import com.example.utptam.model.foto
import com.example.utptam.ui.theme.UTPTAMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UTPTAMTheme {
                var selected by remember { mutableStateOf<foto?>(null) }

                if (selected == null) {
                    Dashboard(onKlikPlaylist = { selected = it })
                } else {
                    PlaylistDetailScreen(foto = selected!!, onBack = { selected = null })
                }
            }
        }
    }
}

@Composable
fun Dashboard(onKlikPlaylist: (foto) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Row {
                Text(text = "For You")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Focus")
            }
        }

        item {
            Text(text = "Recently played")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(FotoSource.dummyfoto) { foto ->
                    Box(modifier = Modifier.clickable { onKlikPlaylist(foto) }) {
                        FotoRowItem(foto)
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.purplecard),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().height(120.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Music made just for you",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "The more you enrich your profile, the closer the music recommendations will match your taste",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(vertical = 8.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Button(onClick = {}) {
                        Text("Set up your profile")
                    }
                }
            }
        }

        items(FotoSource.dummyfoto) { itemFoto ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onKlikPlaylist(itemFoto) },
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Card(modifier = Modifier.size(24.dp), shape = RoundedCornerShape(4.dp)) {
                        Image(
                            painter = painterResource(id = itemFoto.imageRes),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Playlists", fontWeight = FontWeight.Bold)
                }
                DetailScreen(foto = itemFoto)
            }
        }
    }
}

@Composable
fun DetailScreen(foto: foto) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = foto.imageRes),
                contentDescription = foto.artist,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = foto.judul,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Playlist by ${foto.artist}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun FotoRowItem(item: foto) {
    Card(modifier = Modifier.width(120.dp)) {
        Column {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(6.dp)) {
                Text(text = item.judul, maxLines = 1, fontWeight = FontWeight.Bold)
                Text(text = item.artist, maxLines = 1)
            }
        }
    }
}
@Composable
fun PlaylistDetailScreen(foto: foto, onBack: () -> Unit) {
    LazyColumn {
        item {
            Button(
                onClick = onBack,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Back")
            }
        }

        item {
            Image(
                painter = painterResource(id = foto.imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(250.dp),
                contentScale = ContentScale.Crop
            )
        }

        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = foto.judul, style = MaterialTheme.typography.headlineSmall)
                Text(text = foto.artist)
                Row(modifier = Modifier.padding(top = 8.dp)) {
                    Button(onClick = { }) { Text("Favorite") }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { }) { Text("Play") }
                }
            }
        }

        items(FotoSource.dummyfoto) { lagu ->
            Row(modifier = Modifier.padding(8.dp)) {
                Image(
                    painter = painterResource(id = lagu.imageRes),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Text(text = lagu.judul)
                    Text(text = lagu.artist, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    UTPTAMTheme {
        Dashboard(onKlikPlaylist = {})
    }
}