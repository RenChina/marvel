package com.example.marvel.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage


@Composable
fun CardLayout(
    url: String,
    name: String,
    onClick: (String, String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(350.dp)
            .clickable { onClick(url, name) }
    ) {
        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(14.dp)),
            model = url,
            contentDescription = "Изображение персонажа $name",
        )
        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 16.dp),
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(4f, 4f),
                    blurRadius = 16f
                )
            )
        )
    }
}