package com.samkt.filmio.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.samkt.filmio.R

val anago = FontFamily(
    Font(R.font.anago_thin, FontWeight.W100),
    Font(R.font.anago_book, FontWeight.W400),
    Font(R.font.anago_medium, FontWeight.W500),
    Font(R.font.anago_bold, FontWeight.W700),
    Font(R.font.anago_black, FontWeight.W800)
)

val urbanist = FontFamily(
    Font(R.font.urbanist_light, FontWeight.W100),
    Font(R.font.urbanist_semi_bold, FontWeight.W400),
    Font(R.font.urbanist_medium, FontWeight.W500),
    Font(R.font.urbanist_bold, FontWeight.W700),
    Font(R.font.urbanist_black, FontWeight.W800)
)

val ruberoid = FontFamily(
    Font(R.font.ruberoid_bold, FontWeight.Bold)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.W400,
        fontSize = 50.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.W400,
        fontSize = 40.sp,
        lineHeight = 52.sp
    ),
    displaySmall = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.W400,
        fontSize = 30.sp,
        lineHeight = 44.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.W400,
        fontSize = 28.sp,
        lineHeight = 40.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.W400,
        fontSize = 24.sp,
        lineHeight = 36.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.W400,
        fontSize = 20.sp,
        lineHeight = 32.sp
    ),
    titleLarge = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.W700,
        fontSize = 18.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.W700,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp
    ),
    titleSmall = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.W400,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.W400,
        fontSize = 13.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.W400,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = urbanist,
        fontWeight = FontWeight.W500,
        fontSize = 9.sp,
        lineHeight = 16.sp
    )
)
