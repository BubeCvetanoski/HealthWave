package healthWave.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthwave.R
import healthWave.ui.theme.HealthWaveColorScheme
import healthWave.ui.theme.black_color
import healthWave.ui.theme.gray_level_1

@Composable
fun EatingOccasionItemCard(
    icon: ImageVector,
    title: String,
    totalCalories: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(25.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = HealthWaveColorScheme.itemsColor
        )
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(30.dp)
                    .background(HealthWaveColorScheme.backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(id = R.string.eating_occasion_icon),
                    tint = black_color,
                    modifier = Modifier
                        .padding(5.dp)
                        .size(30.dp)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = if (title == stringResource(id = R.string.water))
                        "$totalCalories milliliters"
                    else
                        "$totalCalories calories",
                    fontSize = 12.sp
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(20.dp)
                    .background(HealthWaveColorScheme.itemsColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = stringResource(id = R.string.eating_occasion_forward_arrow),
                    tint = gray_level_1,
                    modifier = Modifier
                        .padding(5.dp)
                        .size(20.dp)
                )
            }
        }
    }
}