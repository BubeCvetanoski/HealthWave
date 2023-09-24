package healthWave.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.healthwave.R
import healthWave.ui.theme.blue_color_level_6
import healthWave.ui.theme.pink_color_level_6

@Composable
fun Logo(
    spaceBetweenElements: Dp = 0.dp,
    modifier: Modifier,
    logoNameFontSize: TextUnit,
    sloganNameFontSize: TextUnit? = null,
    sloganColor: Color? = null
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.spacedBy(spaceBetweenElements),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.wrapContentSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Vector(
                vectorDrawable = R.drawable.logo,
                tint = pink_color_level_6,
                modifier = modifier
            )
        }
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = logoNameFontSize,
            textAlign = TextAlign.Center,
            color = blue_color_level_6,
            fontWeight = FontWeight.Bold
        )
        if (sloganNameFontSize != null && sloganColor != null) {
            Text(
                text = stringResource(id = R.string.app_slogan),
                fontSize = sloganNameFontSize,
                color = sloganColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Vector(
    vectorDrawable: Int,
    tint: Color,
    modifier: Modifier
) {
    Icon(
        painter = painterResource(
            id = vectorDrawable
        ),
        contentDescription = stringResource(id = R.string.logo_name),
        modifier = modifier,
        tint = tint
    )
}