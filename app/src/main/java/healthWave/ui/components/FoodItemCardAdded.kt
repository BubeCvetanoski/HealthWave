package healthWave.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthwave.R
import healthWave.fragments.calorieTracker.presentation.viewmodel.FoodNutrimentsInfoState
import healthWave.ui.theme.transparent_color

@Composable
fun FoodItemCardAdded(
    foodItemCardBackgroundColor: Color,
    foodNutrimentsInfoState: FoodNutrimentsInfoState,
    onDeleteFood: () -> Unit,
    onBack: () -> Unit
) {
    val food = foodNutrimentsInfoState.food
    val foodAmount = foodNutrimentsInfoState.amount

    var backgroundColor = foodItemCardBackgroundColor

    val tapHandler = {
        backgroundColor = transparent_color
    }

    BackHandler { onBack() }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .clip(RoundedCornerShape(5.dp))
            .background(backgroundColor)
            .padding(25.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { tapHandler() },
                    onLongPress = { tapHandler() },
                    onTap = {
                        tapHandler()
                        onBack()
                    }
                )
            },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = food.name,
                fontSize = 16.sp,
                maxLines = 2
            )
            Text(
                text = "Amount: $foodAmount grams",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(id = R.string.click_here_to_remove),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                modifier = Modifier.clickable { onDeleteFood() }
            )
        }
    }
}