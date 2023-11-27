package healthWave.fragments.trainingTracker.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import healthWave.core.util.HelperFunctions
import healthWave.core.util.HelperFunctions.Companion.initializeOutlinedTextFieldColors
import healthWave.fragments.trainingTracker.presentation.event.TrainingTrackerEvent
import healthWave.fragments.trainingTracker.presentation.viewmodel.ExerciseViewModel
import healthWave.ui.components.CustomTable
import healthWave.ui.components.LargeDropdownSpinner
import healthWave.ui.theme.HealthWaveColorScheme
import healthWave.ui.theme.black_color
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Destination
@Composable
fun TrainingTrackerScreen(
    exerciseViewModel: ExerciseViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = exerciseViewModel.exerciseState.value
    val stateSize = state.exercises.size

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
    var selectedIndexExercises by remember { mutableStateOf(-1) }
    var numberOfExercises by remember { mutableStateOf(0) }
    val spinnerList = HelperFunctions.createListForSpinner(1..20) { number ->
        when (number) {
            1 -> "$number Exercise"
            else -> "$number Exercises"
        }
    }

    val spinnerColors = initializeOutlinedTextFieldColors(textColor = black_color)

    val onSpinnerItemSelected: (index: Int, item: String) -> Unit = { index, item ->
        selectedIndexExercises = index
        numberOfExercises = item.substringBefore(" Exercise").toInt()

        if (numberOfExercises != exerciseViewModel.tableCellData!!.size) {

            if (numberOfExercises < stateSize) {

                numberOfExercises = stateSize
                selectedIndexExercises = stateSize - 1
            }

            // Check if the number of exercises has changed -> update the table
            exerciseViewModel.onEvent(
                TrainingTrackerEvent.UpdateCellData(numberOfExercises)
            )
        }
    }

    val onDatePickerDateChanged: (newDate: LocalDate) -> Unit = { newDate ->
        pickedDate = newDate
        pickADateText = formattedDate

        exerciseViewModel.onEvent(
            TrainingTrackerEvent.GetExerciseByDate(formattedDate)
        )
    }

    LaunchedEffect(key1 = state.exercises) {
        if (numberOfExercises != stateSize) {

            numberOfExercises = stateSize
            selectedIndexExercises = stateSize - 1

            // Check if the number of exercises has changed -> update the table
            exerciseViewModel.onEvent(
                TrainingTrackerEvent.UpdateCellData(numberOfExercises)
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (state.exercises.isNotEmpty()) {

            val firstExercise = state.exercises.first()
            pickADateText = firstExercise.date

            numberOfExercises = stateSize
            if (selectedIndexExercises == -1) {
                // Only set selectedIndexExercises if it hasn't been set before
                selectedIndexExercises = numberOfExercises - 1
            }

            exerciseViewModel.onEvent(
                TrainingTrackerEvent.UpdateCellData(numberOfExercises)
            )

        } else {
            exerciseViewModel.onEvent(
                TrainingTrackerEvent.InitializeTableCellData(
                    numberOfExercises,
                    TableCellDataItem("", "")
                )
            )
        }
    }

    BackHandler {}
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = HealthWaveColorScheme.backgroundColor
    ) {
        if (state.isLoading) {
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
        } else {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(20.dp)
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
                LargeDropdownSpinner(
                    label = stringResource(id = R.string.number_of_exercises),
                    colors = spinnerColors,
                    items = spinnerList,
                    selectedIndex = selectedIndexExercises,
                    onItemSelected = { index, item ->
                        onSpinnerItemSelected(index, item)
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))

                if (numberOfExercises > 0) {
                    exerciseViewModel.tableCellData?.let { tableCellData ->
                        CustomTable(
                            date = formattedDate,
                            rows = numberOfExercises,
                            tableCellData = tableCellData,
                            exerciseViewModel = exerciseViewModel
                        )
                    }
                }
            }
        }
    }
}

data class TableCellDataItem(
    val currentText: String,
    var previousText: String,
    var hasUnsavedChanges: Boolean = false
)