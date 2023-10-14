package com.samkt.filmio.presentation.homeScreen.components.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.TabPosition
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.pagerAnimation(
    pagerState: PagerState,
    thisPageIndex: Int,
) = then(
    graphicsLayer {
        val pageOffset = (
                (pagerState.currentPage - thisPageIndex) + pagerState
                    .currentPageOffsetFraction
                )

        cameraDistance = 4 * density
        rotationY = lerp(
            start = 0f,
            stop = 0f,
            fraction = pageOffset.coerceIn(-1f, 1f),
        )

        lerp(
            start = 0.7f,
            stop = 1f,
            fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f),
        ).also { scale ->
            scaleX = scale
            scaleY = scale
        }
    },
)

