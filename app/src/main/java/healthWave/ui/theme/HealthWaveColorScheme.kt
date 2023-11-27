package healthWave.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import healthWave.core.util.Constants.DARK_MODE

object HealthWaveColorScheme {
    private lateinit var _instance: HealthWaveColors
    val instance: HealthWaveColors
        get() = _instance

    val LocalAppTheme = staticCompositionLocalOf<HealthWaveColors> {
        error("No HealthWaveColorScheme provided")
    }
    fun initialize(
        gender: String,
        applicationTheme: String
    ) {
        when (applicationTheme) {
            DARK_MODE -> _instance = if (gender == "Female") HealthWaveColors(
                backgroundColor = dark_mode_background_color,
                itemsColor = dark_mode_items_color,
                baseElementsColor = pink_color_level_2,
                detailsElementsColor = pink_color_level_6,
                firstLevelColor = pink_color_level_1
            ) else HealthWaveColors(
                backgroundColor = dark_mode_background_color,
                itemsColor = dark_mode_items_color,
                baseElementsColor = blue_color_level_2,
                detailsElementsColor = blue_color_level_6,
                firstLevelColor = blue_color_level_1
            )

            else -> {
                _instance = if (gender == "Female") HealthWaveColors(
                    backgroundColor = light_mode_background_color,
                    itemsColor = light_mode_items_color,
                    baseElementsColor = pink_color_level_2,
                    detailsElementsColor = pink_color_level_6,
                    firstLevelColor = pink_color_level_1
                ) else HealthWaveColors(
                    backgroundColor = light_mode_background_color,
                    itemsColor = light_mode_items_color,
                    baseElementsColor = blue_color_level_2,
                    detailsElementsColor = blue_color_level_6,
                    firstLevelColor = blue_color_level_1
                )
            }
        }
    }
    val backgroundColor: Color
        get() = _instance.backgroundColor
    val itemsColor: Color
        get() = _instance.itemsColor
    val baseElementsColor: Color
        get() = _instance.baseElementsColor
    val detailsElementsColor: Color
        get() = _instance.detailsElementsColor
    val firstLevelColor: Color
        get() = _instance.firstLevelColor

    fun setBackgroundColor(applicationTheme: String) {
        _instance = if (applicationTheme == DARK_MODE) {
            _instance.copy(backgroundColor = dark_mode_background_color)
        } else {
            _instance.copy(backgroundColor = light_mode_background_color)
        }
    }

    fun setItemsColor(applicationTheme: String) {
        _instance = if (applicationTheme == DARK_MODE) {
            _instance.copy(backgroundColor = dark_mode_items_color)
        } else {
            _instance.copy(backgroundColor = light_mode_items_color)
        }
    }

    fun setDetailsElementsColor(color: Color) {
        _instance = _instance.copy(detailsElementsColor = color)
    }
}

data class HealthWaveColors(
    val backgroundColor: Color,
    val itemsColor: Color,
    val baseElementsColor: Color,
    val detailsElementsColor: Color,
    val firstLevelColor: Color
)