package com.example.marvel.ui.screens

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.marvel.api.CharacterViewModel
import com.example.marvel.data.AppUrls
import com.example.marvel.ui.components.CardLayout
import com.example.marvel.ui.theme.Gray1
import com.example.marvel.ui.theme.Red1
import com.example.marvel.utils.isNetworkAvailable

@Composable
fun MainScreen(navController: NavHostController, characterViewModel: CharacterViewModel, context: Context) {
    val characters = characterViewModel.characters.collectAsState().value
    val errorMessage = characterViewModel.errorMessage.collectAsState().value
    val isLoading = characters.isEmpty() && errorMessage == null
    val hasError = errorMessage != null

    val isNetworkAvailable = isNetworkAvailable(context)

    LaunchedEffect(isNetworkAvailable) {
        characterViewModel.getCharacters(isNetworkAvailable)
    }

    Box {
        Box(modifier = Modifier.fillMaxSize().background(Gray1))
        Box(modifier = Modifier.fillMaxSize().graphicsLayer { clip = false }) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                withTransform({
                    rotate(degrees = 45f, pivot = Offset(0f, size.height))
                }) {
                    drawRect(color = Red1, size = Size(3000f, 4000f), topLeft = Offset(0f, size.height - 3000f))
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(0.4f).fillMaxHeight(0.15f).padding(top = 30.dp, bottom = 50.dp),
                model = AppUrls.MARVEL_LOGO_URL,
                contentDescription = null,
                placeholder = ColorPainter(Color.Gray)
            )

            Text(
                text = "Choose your hero",
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                color = Color.White,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(4f, 4f),
                        blurRadius = 16f
                    )
                )
            )

            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.95f).padding(top = 50.dp)) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 30.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    when {
                        isLoading -> {
                            items(2) { PlaceholderCard() }
                        }
                        hasError -> {
                            items(2) { PlaceholderCard(showError = true, errorMessage = errorMessage) }
                        }
                        else -> {
                            items(characters) { character ->
                                val imageUrl = "${character.thumbnail.path}.${character.thumbnail.extension}".replace("http://", "https://")

                                CardLayout(
                                    url = imageUrl,
                                    name = character.name,
                                    onClick = { url, name ->
                                        val encodedUrl = Uri.encode(url)
                                        val encodedName = Uri.encode(name)
                                        val encodedDescription = Uri.encode(character.description)

                                        navController.navigate("heroDetail/$encodedUrl/$encodedName/$encodedDescription")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
