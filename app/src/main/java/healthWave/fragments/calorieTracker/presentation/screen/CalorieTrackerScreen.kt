package healthWave.fragments.calorieTracker.presentation.screen

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.EggAlt
import androidx.compose.material.icons.twotone.Icecream
import androidx.compose.material.icons.twotone.LocalDrink
import androidx.compose.material.icons.twotone.LunchDining
import androidx.compose.material.icons.twotone.RamenDining
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthwave.R
import com.ramcosta.composedestinations.annotation.Destination
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import healthWave.fragments.calorieTracker.presentation.viewmodel.FoodViewModel
import healthWave.fragments.calorieTracker.presentation.viewmodel.OverviewState
import healthWave.launcher.presentation.viewmodel.UserViewModel
import healthWave.ui.components.DailyCalorieCountCard
import healthWave.ui.components.EatingOccasionItemCard
import healthWave.ui.components.EatingOccasionSearchScreenCard
import healthWave.ui.components.NutrimentInfo
import healthWave.ui.theme.black_color
import healthWave.ui.theme.gray_level_1
import healthWave.ui.theme.transparent_color
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun CalorieTrackerScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    foodViewModel: FoodViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }

    val dateDialogState = rememberMaterialDialogState()
    var pickADateText by remember {
        mutableStateOf(
            context
                .getString(R.string.pick_a_date)
        )
    }
    var pickedDate by remember { mutableStateOf(LocalDate.now()) }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("dd MMM yyyy").format(pickedDate)
        }
    }
    val userGoalCalories = remember { mutableStateOf("") }
    val firstLevelColor = remember { mutableStateOf(Color.Unspecified) }
    val baseColor = remember { mutableStateOf(Color.Unspecified) }
    val detailsColor = remember { mutableStateOf(Color.Unspecified) }
    val backgroundColor = remember { mutableStateOf(Color.Unspecified) }
    val itemsColor = remember { mutableStateOf(Color.Unspecified) }

    userGoalCalories.value = userViewModel.userState.value.calories
    firstLevelColor.value = userViewModel.getHealthWaveFirstLevelColor()
    baseColor.value = userViewModel.getHealthWaveColors().first
    detailsColor.value = userViewModel.getHealthWaveColors().second
    backgroundColor.value = userViewModel.getCurrentApplicationThemeColors().first
    itemsColor.value = userViewModel.getCurrentApplicationThemeColors().second

    val outlinedTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = black_color,
        cursorColor = gray_level_1,
        containerColor = transparent_color,
        focusedBorderColor = detailsColor.value,
        unfocusedBorderColor = detailsColor.value,
        focusedLabelColor = detailsColor.value,
        unfocusedLabelColor = black_color
    )

    val onDatePickerDateChanged: (newDate: LocalDate) -> Unit = { newDate ->
        pickedDate = newDate
        pickADateText = formattedDate

        foodViewModel.getFoodOverviewByDate(formattedDate)
    }
    val foodState = foodViewModel.foodState
    val overviewState = foodViewModel.overviewState

    val eatingOccasionItems = initializeEatingOccasionItems(
        overviewState = overviewState,
        context = context
    )

    LaunchedEffect(Unit) {
        foodViewModel.setEatingOccasionItemCardExpanded(false)
        foodViewModel.resetRememberedState(true)
    }

    BackHandler {}
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = backgroundColor.value
    ) {
        if (overviewState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Center)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = detailsColor.value,
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.wait_while_the_screen_is_being_initialized),
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Button(
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = detailsColor.value,
                        contentColor = black_color
                    ),
                    onClick = { dateDialogState.show() },
                ) {
                    Text(
                        text = pickADateText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
                MaterialDialog(
                    dialogState = dateDialogState,
                    properties = DialogProperties(
                        dismissOnBackPress = false,
                        dismissOnClickOutside = false
                    ),
                    buttons = {
                        positiveButton(
                            text = stringResource(id = R.string.apply),
                            textStyle = TextStyle(color = black_color, fontSize = 14.sp)
                        )
                        negativeButton(
                            text = stringResource(id = R.string.cancel),
                            textStyle = TextStyle(color = black_color, fontSize = 14.sp)
                        )
                    }
                ) {
                    datepicker(
                        initialDate = pickedDate,
                        yearRange = 1954..2024,
                        colors = DatePickerDefaults.colors(
                            headerBackgroundColor = detailsColor.value,
                            dateActiveBackgroundColor = detailsColor.value
                        ),
                        onDateChange = { newDate ->
                            onDatePickerDateChanged(newDate)
                        }
                    )
                }
                DailyCalorieCountCard(
                    containerColor = firstLevelColor.value,
                    backgroundColor = backgroundColor.value,
                    userGoalCalories = userGoalCalories.value,
                    userDailyCalories = overviewState.overallCalories.toString()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row {
                    NutrimentInfo(
                        name = stringResource(id = R.string.total_carbs),
                        amount = overviewState.overallCarbs,
                        nameFontSize = 14.sp,
                        amountFontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    NutrimentInfo(
                        name = stringResource(id = R.string.total_proteins),
                        amount = overviewState.overallProteins,
                        nameFontSize = 14.sp,
                        amountFontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    NutrimentInfo(
                        name = stringResource(id = R.string.total_fats),
                        amount = overviewState.overallFats,
                        nameFontSize = 14.sp,
                        amountFontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = stringResource(id = R.string.meals))
                    Text(
                        text = stringResource(id = R.string.see_all),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable(
                            onClick = {
                                title = context.getString(R.string.all_meals)
                                foodViewModel.onEatingOccasionItemExpanded()
                            }
                        )
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))

                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    eatingOccasionItems.forEach { item ->
                        EatingOccasionItemCard(
                            containerColor = itemsColor.value,
                            icon = item.icon,
                            iconBackgroundColor = backgroundColor.value,
                            title = item.title,
                            totalCalories = item.calories.toString(),
                            onClick = {
                                title = item.title
                                foodViewModel.onEatingOccasionItemExpanded()
                            }
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
            if (foodState.isEatingOccasionItemCardExpanded) {
                EatingOccasionSearchScreenCard(
                    title = title,
                    themeColor = backgroundColor.value,
                    detailsColor = detailsColor.value,
                    firstLevelColor = firstLevelColor.value,
                    outlinedTextFieldColors = outlinedTextFieldColors,
                    foodItemBorderColor = itemsColor.value,
                    date = formattedDate,
                    foodViewModel = foodViewModel,
                    foodState = foodState,
                    onBackHandlerClick = {
                        foodViewModel.onEatingOccasionItemExpanded()
                        foodViewModel.resetRememberedState(true)
                    }
                )
            }
        }
    }
}

data class EatingOccasionItem(
    val icon: ImageVector,
    val title: String,
    val calories: Int = 0
)

fun initializeEatingOccasionItems(
    overviewState: OverviewState,
    context: Context
): List<EatingOccasionItem> {
    return listOf(
        EatingOccasionItem(
            icon = Icons.TwoTone.EggAlt,
            title = context.getString(R.string.breakfast),
            calories = overviewState.breakfastCalories
        ),
        EatingOccasionItem(
            icon = Icons.TwoTone.LunchDining,
            title = context.getString(R.string.lunch),
            calories = overviewState.lunchCalories
        ),
        EatingOccasionItem(
            icon = Icons.TwoTone.RamenDining,
            title = context.getString(R.string.dinner),
            calories = overviewState.dinnerCalories
        ),
        EatingOccasionItem(
            icon = Icons.TwoTone.Icecream,
            title = context.getString(R.string.snack),
            calories = overviewState.snackCalories
        ),
        EatingOccasionItem(
            icon = Icons.TwoTone.LocalDrink,
            title = context.getString(R.string.water),
            calories = overviewState.overallWaterIntake
        )
    )
}