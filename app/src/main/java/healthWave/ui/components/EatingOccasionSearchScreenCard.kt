package healthWave.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.example.healthwave.R
import healthWave.core.util.HelperFunctions.Companion.CollectUiEvents
import healthWave.fragments.calorieTracker.domain.model.MealType
import healthWave.fragments.calorieTracker.presentation.event.CalorieTrackerEvent
import healthWave.fragments.calorieTracker.presentation.state.FoodState
import healthWave.fragments.calorieTracker.presentation.viewmodel.FoodViewModel
import healthWave.ui.theme.HealthWaveColorScheme
import healthWave.ui.theme.transparent_color

@OptIn(ExperimentalComposeUiApi::class, ExperimentalCoilApi::class)
@Composable
fun EatingOccasionSearchScreenCard(
    title: String,
    outlinedTextFieldColors: TextFieldColors,
    date: String,
    foodViewModel: FoodViewModel,
    foodState: FoodState,
    onBackHandlerClick: () -> Unit
) {
    val context = LocalContext.current
    val isWaterState =
        title == stringResource(id = R.string.water) //If it is clicked from Water Item Card, then this composable will have different look

    val keyboardController = LocalSoftwareKeyboardController.current
    var backgroundColor = HealthWaveColorScheme.backgroundColor

    val tapHandler = {
        backgroundColor = transparent_color
    }

    CollectUiEvents(
        uiEvent = foodViewModel.uiEvent,
        viewModel = foodViewModel,
        context = context
    )

    if (!isWaterState) {
        LaunchedEffect(Unit) {
            if (title == context.getString(R.string.all_meals)) {
                foodViewModel.onEvent(
                    CalorieTrackerEvent.ViewMyMeals(
                        date,
                        MealType.fromString(name = title)
                    )
                )
            }
        }
    }

    if (isWaterState) {
        LaunchedEffect(Unit) {
            foodViewModel.onEvent(CalorieTrackerEvent.GetWaterByDate(date))
        }
    }

    BackHandler { onBackHandlerClick() }
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { tapHandler() },
                    onLongPress = { tapHandler() },
                    onTap = { tapHandler() }
                )
            },
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        if (!isWaterState) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.wrapContentWidth(align = Alignment.CenterHorizontally)
                    )
                    Text(
                        text = stringResource(id = R.string.open_my_meals),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .wrapContentWidth(align = Alignment.CenterHorizontally)
                            .clickable {
                                foodViewModel.onEvent(
                                    CalorieTrackerEvent.ViewMyMeals(
                                        date,
                                        MealType.fromString(name = title)
                                    )
                                )
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            SearchTextField(
                colors = outlinedTextFieldColors,
                text = foodState.query,
                onValueChange = { newQuery ->
                    foodViewModel.onEvent(CalorieTrackerEvent.OnQueryChange(newQuery))
                },
                onSearch = {
                    keyboardController?.hide()
                    foodViewModel.onEvent(CalorieTrackerEvent.OnSearch)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    if (foodState.viewMyMeals) {
                        items(foodState.foodNutrimentsInfo) {
                            FoodItemHeader(
                                modifier = Modifier.fillMaxWidth(),
                                expandedFromWhichState = stringResource(id = R.string.added),
                                foodNutrimentsInfoState = it,
                                onDeleteFood = {
                                    foodViewModel.onEvent(
                                        CalorieTrackerEvent.DeleteFood(
                                            date,
                                            it.food
                                        )
                                    )
                                },
                                onFoodItemHeaderExpanded = {
                                    foodViewModel.onEvent(
                                        CalorieTrackerEvent.FoodItemHeaderExpanded(it.food)
                                    )
                                }
                            )
                        }
                    } else {
                        items(foodState.foodNutrimentsInfo) {
                            FoodItemHeader(
                                foodNutrimentsInfoState = it,
                                onChangeFoodAmount = { newAmount ->
                                    if (newAmount.length <= 5) {
                                        foodViewModel.onEvent(
                                            CalorieTrackerEvent.ChangeFoodAmount(
                                                it.food,
                                                newAmount
                                            )
                                        )
                                    }
                                },
                                onInsertFood = {
                                    keyboardController?.hide()
                                    foodViewModel.onEvent(
                                        CalorieTrackerEvent.InsertFood(
                                            date,
                                            it.food,
                                            MealType.fromString(title)
                                        )
                                    )
                                },
                                onFoodItemHeaderExpanded = {
                                    foodViewModel.onEvent(
                                        CalorieTrackerEvent.FoodItemHeaderExpanded(it.food)
                                    )
                                },
                                expandedFromWhichState = stringResource(id = R.string.searched),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.wrapContentWidth(align = Alignment.CenterHorizontally)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            WaterItemHeaderAdd(
                waterItemBorderColor = HealthWaveColorScheme.itemsColor,
                outlinedTextFieldColors = outlinedTextFieldColors,
                backgroundColor = backgroundColor,
                takeWaterIntake = foodState.takeWaterIntake,
                onChangeWaterMilliliters = { newMilliliters ->
                    if (newMilliliters.length <= 5) {
                        foodViewModel.onEvent(
                            CalorieTrackerEvent.ChangeWaterMilliliters(newMilliliters)
                        )
                    }
                },
                onInsertWater = {
                    keyboardController?.hide()
                    foodViewModel.onEvent(
                        CalorieTrackerEvent.InsertWater(
                            date,
                            foodState.takeWaterIntake
                        )
                    )
                }
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(foodState.waterIntakeInfo) {
                    WaterHeaderItemAdded(
                        waterIntakeInfoState = it,
                        onDeleteWater = {
                            foodViewModel.onEvent(
                                CalorieTrackerEvent.DeleteWater(date, it)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
    if (foodState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = HealthWaveColorScheme.detailsElementsColor,
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    text = stringResource(id = R.string.wait_while_the_screen_is_being_initialized),
                    fontSize = 14.sp
                )
            }
        }
    }
}