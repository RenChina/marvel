package com.example.marvel.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PlaceholderCard(
    showError: Boolean = false,
    errorMessage: String? = null
) {
    Column(
        modifier = Modifier
            .width(350.dp)
            .fillMaxHeight()
            .background(Color.LightGray, shape = RoundedCornerShape(14.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom
    ) {
        if (showError && errorMessage != null) {
            Text(
                text = "Ошибка: ${errorMessage}",
                color = Color.Red,
                fontSize = 30.sp,
                textAlign = TextAlign.Center
            )
        } else {
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth(0.7f)
                    .background(Color.Gray, shape = RoundedCornerShape(8.dp))
            )
        }
    }
}
