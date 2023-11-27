package healthWave.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import healthWave.fragments.programs.presentation.ProgramsListItem
import healthWave.ui.theme.HealthWaveColorScheme
import healthWave.ui.theme.transparent_color

@Composable
fun ProgramsListItemCard(
    program: ProgramsListItem,
    onClick: () -> Unit
) {
    val scrollState = rememberLazyListState()
    var backgroundColor = HealthWaveColorScheme.backgroundColor

    val tapHandler = {
        backgroundColor = transparent_color
    }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { tapHandler() },
                    onLongPress = { tapHandler() },
                    onTap = {
                        onClick()
                        tapHandler()
                    }
                )
            },
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = program.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.wrapContentWidth(align = Alignment.CenterHorizontally)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier.padding(10.dp),
            state = scrollState
        ) {
            item {
                Text(
                    text = program.text,
                    fontSize = 14.sp
                )
            }
        }
    }
}