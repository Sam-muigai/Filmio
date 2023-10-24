package com.samkt.filmio.presentation.sharedComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
internal fun MovieCard(
    modifier: Modifier = Modifier,
    imageUrl: String = "",
    clickable:Boolean = true,
    cornerSize: Dp = 0.dp,
    onMovieClicked:()->Unit = {}
) {
    val context = LocalContext.current

    val cardImageModifier = if (clickable){
        Modifier
            .clickable { onMovieClicked.invoke()  }
            .fillMaxSize()
            .clip(RoundedCornerShape(cornerSize))
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
    } else{
        Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(cornerSize))
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
    }
    Box(
        modifier = modifier,
    ) {
        Column(
            modifier = cardImageModifier
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(context).data(imageUrl).crossfade(500).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
    }
}




