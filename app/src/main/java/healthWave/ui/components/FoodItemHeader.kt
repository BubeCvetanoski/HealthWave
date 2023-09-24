package healthWave.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.healthwave.R
import healthWave.fragments.calorieTracker.presentation.viewmodel.FoodNutrimentsInfoState

@ExperimentalCoilApi
@Composable
fun FoodItemHeader(
    modifier: Modifier = Modifier,
    expandedFromWhichState: String,
    foodItemBorderColor: Color,
    outlinedTextFieldColors: TextFieldColors,
    firstLevelColor: Color,
    foodNutrimentsInfoState: FoodNutrimentsInfoState,
    onChangeFoodAmount: (String) -> Unit = {},
    onInsertFood: () -> Unit = {},
    onDeleteFood: () -> Unit = {},
    onFoodItemHeaderExpanded: () -> Unit
) {
    val food = foodNutrimentsInfoState.food
    val isFoodItemHeaderExpanded = foodNutrimentsInfoState.isFoodItemHeaderExpanded

    Box {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(5.dp))
                .padding(5.dp)
                .clickable { onFoodItemHeaderExpanded() }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = foodItemBorderColor
                        ),
                        shape = RoundedCornerShape(5.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f)
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = food.imageUrl,
                            builder = {
                                error(R.drawable.no_image_preview)
                                fallback(R.drawable.no_image_preview)
                            }
                        ),
                        contentDescription = food.name,
                        contentScale = ContentScale.FillBounds,
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(5.dp))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = food.name,
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = food.calories100g.toString() + " calories",
                            fontSize = 10.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                Row(modifier = Modifier.padding(5.dp)) {
                    NutrimentInfo(
                        name = stringResource(id = R.string.carbs),
                        amount = food.carbs100g
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    NutrimentInfo(
                        name = stringResource(id = R.string.proteins),
                        amount = food.protein100g
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    NutrimentInfo(
                        name = stringResource(id = R.string.fats),
                        amount = food.fat100g
                    )
                }
            }
        }
    }
    if (isFoodItemHeaderExpanded && expandedFromWhichState == stringResource(id = R.string.searched)) {
        FoodItemCardSearched(
            outlinedTextFieldColors = outlinedTextFieldColors,
            foodItemCardBackgroundColor = firstLevelColor,
            foodNutrimentsInfoState = foodNutrimentsInfoState,
            onChangeFoodAmount = onChangeFoodAmount,
            onInsertFood = onInsertFood,
            onBack = onFoodItemHeaderExpanded
        )
    } else if (isFoodItemHeaderExpanded && expandedFromWhichState == stringResource(id = R.string.added)) {
        FoodItemCardAdded(
            foodItemCardBackgroundColor = firstLevelColor,
            foodNutrimentsInfoState = foodNutrimentsInfoState,
            onDeleteFood = onDeleteFood,
            onBack = onFoodItemHeaderExpanded
        )
    }
}