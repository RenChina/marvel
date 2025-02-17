package com.example.marvel.ui.screens


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.marvel.MarvelApp
import com.example.marvel.data.AppUrls
import com.example.marvel.data.HeroData
import com.example.marvel.ui.components.CardLayout
import com.example.marvel.ui.theme.Gray1
import com.example.marvel.ui.theme.Red1
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MainScreen(navController: NavHostController) {
    var selectedHero by remember { mutableStateOf<HeroData?>(null) }

    if (selectedHero != null) {
        CardInfo(
            url = selectedHero!!.imageUrl,
            nameResId = selectedHero!!.nameResId,
            descriptionResId = selectedHero!!.descriptionResId,
            onClose = { selectedHero = null }
        )
    } else {
        Box {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Gray1)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { clip = false }
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    withTransform({
                        rotate(
                            degrees = 45f,
                            pivot = Offset(0f, size.height)
                        )
                    }) {
                        drawRect(
                            color = Red1,
                            size = Size(3000f, 4000f),
                            topLeft = Offset(0f, size.height - 3000f)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .padding(top = 30.dp, bottom = 50.dp),
                    model = AppUrls.MARVEL_LOGO_URL,
                    contentDescription = null,
                )
                Text(
                    text = "Choose your hero",
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,
                    color = Color.White
                )
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.95f)
                    .padding(top = 50.dp)
                ) {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 30.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        itemsIndexed(AppUrls.heroes) { _, hero ->
                            CardLayout (
                                url = hero.imageUrl,
                                nameResId = hero.nameResId,
                                onClick = {
                                    val encodedUrl = URLEncoder.encode(hero.imageUrl, StandardCharsets.UTF_8.toString())
                                    navController.navigate("heroDetail/$encodedUrl")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MarvelApp()
}
