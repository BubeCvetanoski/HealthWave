package healthWave.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun NutrimentInfo(
    name: String,
    amount: Int,
    nameFontSize: TextUnit = 12.sp,
    amountFontSize: TextUnit = 10.sp,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = name,
            fontSize = nameFontSize,
            fontWeight = fontWeight
        )
        Text(
            text = "$amount grams",
            fontSize = amountFontSize
        )
    }
}