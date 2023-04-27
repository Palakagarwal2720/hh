package com.example.userapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import com.example.userapp.util.Constants.WIDTH_FOUR_EIGHTY
import com.example.userapp.util.Constants.WIDTH_ONE_SIXTY
import com.example.userapp.util.Constants.WIDTH_ONE_SIXTY_ONE
import com.example.userapp.util.Constants.WIDTH_THREE_TWENTY
import com.example.userapp.util.Constants.WIDTH_THREE_TWENTY_ONE
import com.example.userapp.util.Constants.WIDTH_TWO_FORTY
import com.example.userapp.util.Constants.WIDTH_TWO_FORTY_ONE

@Composable
fun ProvideDimens(
    dimensions: Dimensions,
    fontSizes: FontSize,
    content: @Composable () -> Unit
) {
    val dimensionSet = remember { dimensions }
    val fontSizeSet = remember { fontSizes }
    CompositionLocalProvider(LocalAppDimens provides dimensionSet, LocalAppFontSizes provides fontSizeSet, content = content)
}

private val LocalAppDimens = staticCompositionLocalOf {
    dimen_mdpi
}

private val LocalAppFontSizes = staticCompositionLocalOf {
    font_size_mdpi
}


@Composable
fun UserTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }


    val configuration = LocalConfiguration.current
    var dimensions = dimen_mdpi
    var fontSize = font_size_mdpi

    if (configuration.screenWidthDp <= WIDTH_ONE_SIXTY) {
        dimensions = dimen_mdpi
        fontSize = font_size_mdpi
    } else if (configuration.screenWidthDp in (WIDTH_ONE_SIXTY_ONE..WIDTH_TWO_FORTY)) {
        dimensions = dimen_hdpi
        fontSize = font_size_hdpi
    } else if (configuration.screenWidthDp in (WIDTH_TWO_FORTY_ONE..WIDTH_THREE_TWENTY)) {
        dimensions = dimen_xhdpi
        fontSize = font_size_xhdpi
    } else if (configuration.screenWidthDp in (WIDTH_THREE_TWENTY_ONE..WIDTH_FOUR_EIGHTY)) {
        dimensions = dimen_xxhdpi
        fontSize = font_size_xxhdpi
    }

    ProvideDimens(
        dimensions = dimensions,
        fontSizes = fontSize
    ) {
        MaterialTheme(
            colors = colors,
            shapes = Shapes,
            content = content
        )
    }
}

object UserTheme {
    var dimensions = dimen_mdpi
    var fontSize = font_size_mdpi
    val dimens: Dimensions
        @Composable
        get(){
            val configuration = LocalConfiguration.current
            if (configuration.screenWidthDp <= WIDTH_ONE_SIXTY) {
                dimensions = dimen_mdpi
                fontSize = font_size_mdpi
            } else if (configuration.screenWidthDp in (WIDTH_ONE_SIXTY_ONE..WIDTH_TWO_FORTY)) {
                dimensions = dimen_hdpi
                fontSize = font_size_hdpi
            } else if (configuration.screenWidthDp in (WIDTH_TWO_FORTY_ONE..WIDTH_THREE_TWENTY)) {
                dimensions = dimen_xhdpi
                fontSize = font_size_xhdpi
            } else if (configuration.screenWidthDp in (WIDTH_THREE_TWENTY_ONE..WIDTH_FOUR_EIGHTY)) {
                dimensions = dimen_xxhdpi
                fontSize = font_size_xxhdpi
            }
            return dimensions
        }

    val colors: Colors
        @Composable
        get() = MaterialTheme.colors

    val fontSizes: FontSize
        @Composable
        get() = fontSize
}

val Dimens: Dimensions
    @Composable
    get() = UserTheme.dimens

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

