package com.untenty.nauticalknots.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color(0xff000000), // основной фон
)

private val LightColorScheme = lightColorScheme(

//    primary = Color(0xFFE91E63), // цвет кнопок, курсора
//    secondary = Color(0xffc91814),
    tertiary = Color(0xFF9C27B0),
    background = Color(0xffffffff), // основной фон

//    onPrimary = Color(0xFFE91E63), // содержимое primary
//    primaryContainer = Color(0xffff9d30), // плавающая кнопка
//    onPrimaryContainer = Color(0xFF00BCD4), // содержимое primaryContainer
//    inversePrimary = Color(0xFF9C27B0),
//
//    onSecondary = Color(0xffc91814),
//    secondaryContainer = Color(0xffc91814),
//    onSecondaryContainer = Color(0xffc91814),
//
//    onTertiary = Color(0xffc91814),
//    tertiaryContainer = Color(0xffc91814),
//    onTertiaryContainer = Color(0xffc91814),
//
//    onBackground = Color(0xFFE91E63), // содержимое background
//    surface = Color(0xFF9C27B0), // фон панелей
//    onSurface = Color(0xFFE91E63), // содержимое surface
//    surfaceVariant = Color(0xFF9C27B0), // цвет поля ввода
//    onSurfaceVariant = Color(0xFFE91E63), // цвет иконки поля ввода или панели
//    surfaceTint = Color(0xFFE91E63),
//    inverseSurface = Color(0xffc91814),
//    inverseOnSurface = Color(0xffc91814),
//
//    outline = Color(0xffc91814),
//    outlineVariant = Color(0xFF5A5A5A), // линия разделения на панели
//    scrim = Color(0xFF5A5A5A), // маска затенения фоновой части
////////////////////////////
//
//    error = Color(0xffff9d30),
//    onError = Color(0xffff9d30),
//    errorContainer = Color(0xffff9d30),
//    onErrorContainer = Color(0xffff9d30),
//    surfaceBright = Color(0xffff9d30),
//    surfaceContainer = Color(0xffff9d30),
//    surfaceContainerHigh = Color(0xffff9d30),
//    surfaceContainerHighest = Color(0xffff9d30),
//    surfaceContainerLow = Color(0xffff9d30),
//    surfaceContainerLowest = Color(0xffff9d30),
//    surfaceDim = Color(0xffff9d30)
)

@Composable
fun NauticalknotsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}