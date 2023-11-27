package healthWave.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import healthWave.fragments.programs.presentation.ProgramsListItem
import healthWave.ui.theme.HealthWaveColorScheme

@Composable
fun ProgramsListItemHeader(
    program: ProgramsListItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = HealthWaveColorScheme.itemsColor
        )
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            Text(
                text = program.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.TopStart)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${program.days} days split",
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
            )
        }
    }
}