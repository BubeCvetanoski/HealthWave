package healthWave.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.healthwave.R
import healthWave.ui.theme.blue_color_level_6
import healthWave.ui.theme.pink_color_level_4
import kotlinx.coroutines.delay

@Composable
fun AnimatedLogo(
    modifier: Modifier,
    logoNameFontSize: TextUnit,
    sloganNameFontSize: TextUnit,
    initialSloganColor: Color,
    finalSloganColor: Color
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.wrapContentSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Vector(
                vectorDrawable = R.drawable.logo,
                tint = pink_color_level_4,
                modifier = modifier
            )
        }
        AnimateLogoText(
            stringResource(id = R.string.app_name),
            logoNameFontSize
        )
        AnimateSloganText(
            stringResource(id = R.string.app_slogan),
            sloganNameFontSize,
            initialSloganColor,
            finalSloganColor,
        )
    }
}

@Composable
fun AnimateLogoText(text: String, logoNameFontSize: TextUnit) {
    val textVisibility = remember { mutableStateListOf<Boolean>() }

    LaunchedEffect(Unit) {
        textVisibility.addAll(List(text.length) { false })
        val visibilityList = textVisibility.toList()

        visibilityList.forEachIndexed { index, _ ->
            delay(index * 15L) // Letter by letter appearance speed.
            textVisibility[index] = true
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        text.forEachIndexed { index, char ->
            Text(
                text = char.toString(),
                modifier = Modifier.alpha(if (textVisibility.getOrNull(index) == true) 1f else 0f),
                fontSize = logoNameFontSize,
                color = blue_color_level_6,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AnimateSloganText(
    text: String,
    sloganNameFontSize: TextUnit,
    initialSloganColor: Color,
    finalSloganColor: Color
) {
    val textVisibility = remember { mutableStateListOf<Boolean>() }
    var shouldChangeColor by remember { mutableStateOf(false) }
    val currentColor = if (shouldChangeColor) finalSloganColor else initialSloganColor

    LaunchedEffect(Unit) {
        textVisibility.addAll(List(text.length) { false })
        val visibilityList = textVisibility.toList()

        visibilityList.forEachIndexed { index, _ ->
            delay(index * 1L) // Letter by letter appearance speed.
            textVisibility[index] = true
        }
        // Show the text with the new color by setting the visibility list to all true
        textVisibility.replaceAll { true }
    }
    // This LaunchedEffect will update the color when the whole text is shown
    LaunchedEffect(textVisibility.joinToString(",")) {
        if (textVisibility.all { it }) {
            delay(400L)
            shouldChangeColor = true
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        text.forEachIndexed { index, char ->
            Text(
                text = char.toString(),
                modifier = Modifier
                    .alpha(
                        if (textVisibility.getOrNull(index) == true) 1f
                        else 0f
                    ),
                fontSize = sloganNameFontSize,
                color = currentColor,
                textAlign = TextAlign.Center
            )
        }
    }
}