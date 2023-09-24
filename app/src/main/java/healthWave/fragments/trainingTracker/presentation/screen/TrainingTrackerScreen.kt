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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import healthWave.data.local.database.entity.Exercise
import healthWave.fragments.trainingTracker.presentation.viewmodel.ExerciseViewModel
import healthWave.launcher.presentation.viewmodel.UserViewModel
import healthWave.ui.components.CustomTable
import healthWave.ui.components.LargeDropdownSpinner
import healthWave.ui.theme.black_color
import healthWave.ui.theme.gray_level_1
import healthWave.ui.theme.transparent_color
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun TrainingTrackerScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    exerciseViewModel: ExerciseViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = exerciseViewModel.exerciseState.value
    val displayedExercises = remember { mutableStateListOf<Exercise>() }

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

    val firstLevelColor = remember { mutableStateOf(Color.Unspecified) }
    val baseColor = remember { mutableStateOf(Color.Unspecified) }
    val detailsColor = remember { mutableStateOf(Color.Unspecified) }
    val backgroundColor = remember { mutableStateOf(Color.Unspecified) }
    val itemsColor = remember { mutableStateOf(Color.Unspecified) }

    firstLevelColor.value = userViewModel.getHealthWaveFirstLevelColor()
    baseColor.value = userViewModel.getHealthWaveColors().first
    detailsColor.value = userViewModel.getHealthWaveColors().second
    backgroundColor.value = userViewModel.getCurrentApplicationThemeColors().first
    itemsColor.value = userViewModel.getCurrentApplicationThemeColors().second

    val spinnerColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = black_color,
        cursorColor = gray_level_1,
        containerColor = transparent_color,
        focusedBorderColor = detailsColor.value,
        unfocusedBorderColor = detailsColor.value,
        focusedLabelColor = detailsColor.value,
        unfocusedLabelColor = black_color
    )

    val onSpinnerItemSelected: (index: Int, item: String) -> Unit = { index, item ->
        selectedIndexExercises = index
        numberOfExercises = item.substringBefore(" Exercise").toInt()

        if (numberOfExercises < displayedExercises.size) {
            numberOfExercises = displayedExercises.size
            selectedIndexExercises = displayedExercises.size - 1
        }
        // Check if the number of exercises has changed -> update the table
        exerciseViewModel.tableCellData = updateTableCellData(
            initializeTableCellData(
                numberOfExercises,
                TableCellDataItem("", "")
            ),
            state.exercises
        )
    }

    val onDatePickerDateChanged: (newDate: LocalDate) -> Unit = { newDate ->
        pickedDate = newDate
        pickADateText = formattedDate

        exerciseViewModel.getExercisesByDate(formattedDate)
    }

    LaunchedEffect(key1 = state.exercises) {

        numberOfExercises = state.exercises.size
        selectedIndexExercises = numberOfExercises - 1

        exerciseViewModel.tableCellData = updateTableCellData(
            initializeTableCellData(
                numberOfExercises,
                TableCellDataItem("", "")
            ),
            state.exercises
        )

        displayedExercises.clear()
        for (i in 0 until numberOfExercises) {
            val name = exerciseViewModel.tableCellData!![i][0].value.currentText
            val sets = exerciseViewModel.tableCellData!![i][1].value.currentText
            val reps = exerciseViewModel.tableCellData!![i][2].value.currentText
            val load = exerciseViewModel.tableCellData!![i][3].value.currentText

            val exercise = Exercise(
                number = (i + 1).toString(),
                name = name,
                sets = sets,
                reps = reps,
                load = load,
                date = pickADateText
            )
            displayedExercises.add(exercise)
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (state.exercises.isNotEmpty()) {

            val firstExercise = state.exercises.first()
            pickADateText = firstExercise.date

            numberOfExercises = state.exercises.size
            if (selectedIndexExercises == -1) {
                // Only set selectedIndexExercises if it hasn't been set before
                selectedIndexExercises = numberOfExercises - 1
            }

            exerciseViewModel.tableCellData = updateTableCellData(
                initializeTableCellData(
                    numberOfExercises,
                    TableCellDataItem("", "")
                ),
                state.exercises
            )

            displayedExercises.clear()
            for (i in 0 until numberOfExercises) {
                val name = exerciseViewModel.tableCellData!![i][0].value.currentText
                val sets = exerciseViewModel.tableCellData!![i][1].value.currentText
                val reps = exerciseViewModel.tableCellData!![i][2].value.currentText
                val load = exerciseViewModel.tableCellData!![i][3].value.currentText

                val exercise = Exercise(
                    number = (i + 1).toString(),
                    name = name,
                    sets = sets,
                    reps = reps,
                    load = load,
                    date = pickADateText
                )
                displayedExercises.add(exercise)
            }

        } else {
            exerciseViewModel.tableCellData =
                initializeTableCellData(
                    numberOfExercises,
                    TableCellDataItem("", "")
                )
        }
    }

    BackHandler {}
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = backgroundColor.value
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
                modifier = Modifier.padding(20.dp)
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
                LargeDropdownSpinner(
                    label = stringResource(id = R.string.number_of_exercises),
                    colors = spinnerColors,
                    items = spinnerList,
                    selectedIndex = selectedIndexExercises,
                    onItemSelected = { index, item ->
                        onSpinnerItemSelected(index, item)
                    },
                    onValidate = {}
                )
                Spacer(modifier = Modifier.height(10.dp))

                if (numberOfExercises > 0) {
                    exerciseViewModel.tableCellData?.let { tableCellData ->
                        CustomTable(
                            headerColor = itemsColor.value,
                            date = formattedDate,
                            rows = numberOfExercises,
                            tableCellData = tableCellData,
                            displayedExercises = displayedExercises,
                            exerciseViewModel = exerciseViewModel
                        )
                    }
                }
            }
        }
    }
}

private inline fun <reified T> initializeTableCellData(
    numberOfRows: Int,
    defaultItem: T
): List<List<MutableState<T>>> {
    return List(numberOfRows) {
        List(4) { mutableStateOf(defaultItem) }
    }
}

private fun updateTableCellData(
    tableCellData: List<List<MutableState<TableCellDataItem>>>,
    exercises: List<Exercise>
): List<List<MutableState<TableCellDataItem>>> {
    val updatedData = tableCellData.toMutableList()

    for (i in exercises.indices) {
        val exercise = exercises[i]

        if (i < updatedData.size) {
            val row = updatedData[i]
            row[0].value = TableCellDataItem(
                currentText = exercise.name,
                previousText = exercise.name
            )
            row[1].value = TableCellDataItem(
                currentText = exercise.sets,
                previousText = exercise.sets
            )
            row[2].value = TableCellDataItem(
                currentText = exercise.reps,
                previousText = exercise.reps
            )
            row[3].value = TableCellDataItem(
                currentText = exercise.load,
                previousText = exercise.load
            )
        } else {
            // If there are more exercises than rows, add new rows
            val newRow = listOf(
                mutableStateOf(
                    TableCellDataItem(
                        currentText = exercise.name,
                        previousText = exercise.name
                    )
                ),
                mutableStateOf(
                    TableCellDataItem(
                        currentText = exercise.sets,
                        previousText = exercise.sets
                    )
                ),
                mutableStateOf(
                    TableCellDataItem(
                        currentText = exercise.reps,
                        previousText = exercise.reps
                    )
                ),
                mutableStateOf(
                    TableCellDataItem(
                        currentText = exercise.load,
                        previousText = exercise.load
                    )
                )
            )
            updatedData.add(newRow)
        }
    }
    return updatedData
}

data class TableCellDataItem(
    val currentText: String,
    var previousText: String,
    var hasUnsavedChanges: Boolean = false
)