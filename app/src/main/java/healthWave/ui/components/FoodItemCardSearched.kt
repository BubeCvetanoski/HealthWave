package healthWave.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.healthwave.R
import healthWave.core.util.HelperFunctions.Companion.initializeOutlinedTextFieldColors
import healthWave.fragments.calorieTracker.presentation.state.FoodNutrimentsInfoState
import healthWave.ui.theme.HealthWaveColorScheme
import healthWave.ui.theme.black_color
import healthWave.ui.theme.transparent_color

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FoodItemCardSearched(
    foodNutrimentsInfoState: FoodNutrimentsInfoState,
    onInsertFood: () -> Unit,
    onChangeFoodAmount: (String) -> Unit,
    onBack: () -> Unit
) {
    val food = foodNutrimentsInfoState.food

    val outlinedTextFieldColors = initializeOutlinedTextFieldColors(textColor = black_color)
    var backgroundColor = HealthWaveColorScheme.firstLevelColor

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
            Spacer(modifier = Modifier.height(5.dp))
            Image(
                painter = rememberImagePainter(
                    data = food.imageUrl,
                    builder = {
                        crossfade(true)
                        error(R.drawable.no_image_preview)
                        fallback(R.drawable.no_image_preview)
                    }
                ),
                contentDescription = food.name,
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
            Text(
                text = food.calories100g.toString() + " " + stringResource(id = R.string.calories_per_100_grams),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(modifier = Modifier.padding(5.dp)) {
                NutrimentInfo(
                    name = stringResource(id = R.string.carbs),
                    amount = food.carbs100g,
                    nameFontSize = 14.sp,
                    amountFontSize = 12.sp
                )
                Spacer(modifier = Modifier.width(10.dp))
                NutrimentInfo(
                    name = stringResource(id = R.string.proteins),
                    amount = food.protein100g,
                    nameFontSize = 14.sp,
                    amountFontSize = 12.sp
                )
                Spacer(modifier = Modifier.width(10.dp))
                NutrimentInfo(
                    name = stringResource(id = R.string.fats),
                    amount = food.fat100g,
                    nameFontSize = 14.sp,
                    amountFontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(modifier = Modifier.padding(5.dp)) {
                Text(
                    text = stringResource(id = R.string.amount),
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                OutlinedTextField(
                    value = foodNutrimentsInfoState.amount,
                    onValueChange = { newAmount ->
                        onChangeFoodAmount(newAmount)
                    },
                    singleLine = true,
                    maxLines = 1,
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .wrapContentHeight(Alignment.CenterVertically)
                        .width(90.dp)
                        .padding(5.dp),
                    colors = outlinedTextFieldColors,
                    shape = RoundedCornerShape(5.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = if (foodNutrimentsInfoState.amount.isNotBlank()) {
                            ImeAction.Done
                        } else ImeAction.Default,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onInsertFood()
                            defaultKeyboardAction(ImeAction.Done)
                        }
                    )
                )
                Text(
                    text = "grams",
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}