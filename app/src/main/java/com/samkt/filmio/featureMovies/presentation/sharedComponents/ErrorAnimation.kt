package com.samkt.filmio.featureMovies.presentation.sharedComponents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.samkt.filmio.R

@Composable
fun ErrorAnimation(
    modifier: Modifier = Modifier
) {
    val lottieComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            R.raw.loading_error
        )
    )
    val loaderProgress by animateLottieCompositionAsState(
        lottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    LottieAnimation(
        composition = lottieComposition,
        progress = loaderProgress,
        modifier = modifier
    )
}
