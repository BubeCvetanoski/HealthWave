package healthWave.fragments.calorieTracker.presentation.screen

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
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
import healthWave.core.util.HelperFunctions.Companion.initializeEatingOccasionItems
import healthWave.core.util.HelperFunctions.Companion.initializeOutlinedTextFieldColors
import healthWave.fragments.calorieTracker.presentation.event.CalorieTrackerEvent
import healthWave.fragments.calorieTracker.presentation.viewmodel.CalorieTrackerViewModel
import healthWave.launcher.presentation.event.SharedSignUpEvent
import healthWave.launcher.presentation.viewmodel.SharedUserViewModel
import healthWave.ui.components.DailyCalorieCountCard
import healthWave.ui.components.EatingOccasionItemCard
import healthWave.ui.components.EatingOccasionSearchScreenCard
import healthWave.ui.components.NutrimentInfo
import healthWave.ui.theme.HealthWaveColorScheme
import healthWave.ui.theme.black_color
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Destination
@Composable
fun CalorieTrackerScreen(
    sharedUserViewModel: SharedUserViewModel = hiltViewModel(),
    calorieTrackerViewModel: CalorieTrackerViewModel = hiltViewModel()
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

    val outlinedTextFieldColors = initializeOutlinedTextFieldColors(textColor = black_color)

    val onDatePickerDateChanged: (newDate: LocalDate) -> Unit = { newDate ->
        pickedDate = newDate
        pickADateText = formattedDate

        calorieTrackerViewModel.onEvent(
            CalorieTrackerEvent.GetFoodOverviewByDate(formattedDate)
        )
    }
    val foodState = calorieTrackerViewModel.foodState.collectAsState().value
    val overviewState = calorieTrackerViewModel.overviewState.collectAsState().value

    val eatingOccasionItems = context.initializeEatingOccasionItems(
        overviewState = overviewState
    )

    LaunchedEffect(Unit) {
        calorieTrackerViewModel.onEvent(
            CalorieTrackerEvent.SetEatingOccasionItemCardExpanded(false)
        )
        calorieTrackerViewModel.onEvent(
            CalorieTrackerEvent.ResetRememberedState(true)
        )
    }

    BackHandler {}
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = HealthWaveColorScheme.backgroundColor
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
                        color = HealthWaveColorScheme.detailsElementsColor,
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
                        containerColor = HealthWaveColorScheme.detailsElementsColor,
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
                            headerBackgroundColor = HealthWaveColorScheme.detailsElementsColor,
                            dateActiveBackgroundColor = HealthWaveColorScheme.detailsElementsColor
                        ),
                        onDateChange = { newDate ->
                            onDatePickerDateChanged(newDate)
                        }
                    )
                }
                DailyCalorieCountCard(
                    containerColor = HealthWaveColorScheme.itemsColor,
                    backgroundColor = HealthWaveColorScheme.backgroundColor,
                    userGoalCalories = sharedUserViewModel.onEvent(SharedSignUpEvent.GetGoalCalories) as String,
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
                                calorieTrackerViewModel.onEvent(CalorieTrackerEvent.ExpandEatingOccasionItem)
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
                            icon = item.icon,
                            title = item.title,
                            totalCalories = item.calories.toString(),
                            onClick = {
                                title = item.title
                                calorieTrackerViewModel.onEvent(CalorieTrackerEvent.ExpandEatingOccasionItem)
                            }
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
            if (foodState.isEatingOccasionItemCardExpanded) {
                EatingOccasionSearchScreenCard(
                    title = title,
                    outlinedTextFieldColors = outlinedTextFieldColors,
                    date = formattedDate,
                    calorieTrackerViewModel = calorieTrackerViewModel,
                    foodState = foodState,
                    onBackHandlerClick = {
                        calorieTrackerViewModel.onEvent(CalorieTrackerEvent.ExpandEatingOccasionItem)
                        calorieTrackerViewModel.onEvent(CalorieTrackerEvent.ResetRememberedState(true))
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