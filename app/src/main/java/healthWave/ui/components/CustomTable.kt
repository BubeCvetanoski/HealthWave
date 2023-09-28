package healthWave.ui.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.healthwave.R
import healthWave.core.util.HelperFunctions.Companion.clearTableCellData
import healthWave.core.util.HelperFunctions.Companion.findNextEmptyCell
import healthWave.core.util.HelperFunctions.Companion.showToast
import healthWave.core.util.UiEvent
import healthWave.data.local.database.entity.Exercise
import healthWave.fragments.trainingTracker.presentation.screen.TableCellDataItem
import healthWave.fragments.trainingTracker.presentation.viewmodel.ExerciseState
import healthWave.fragments.trainingTracker.presentation.viewmodel.ExerciseViewModel
import healthWave.ui.theme.black_color
import healthWave.ui.theme.transparent_color
import healthWave.ui.theme.white_color

@Composable
fun CustomTable(
    headerColor: Color,
    date: String,
    rows: Int,
    tableCellData: List<List<MutableState<TableCellDataItem>>>,
    exerciseState: ExerciseState,
    exerciseViewModel: ExerciseViewModel
) {
    val context = LocalContext.current
    val tableCellHeaderItems = initializeTableCellHeaderItems(context = context)
    val focusRequesters = List(rows * 4) { remember { FocusRequester() } }

    val nameColumnWeight = .55f
    val restColumnWeight = .15f

    LaunchedEffect(exerciseViewModel) {
        exerciseViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowToast -> {
                    showToast(
                        context = context,
                        message = event.message.asString(context),
                        duration = 10
                    )
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            Modifier.fillMaxSize()
        ) {
            // Here is the header of the table
            item {
                Row(Modifier.background(headerColor)) {
                    tableCellHeaderItems.forEach { headerItem ->
                        TableCell(
                            text = headerItem.text,
                            weight = headerItem.weight,
                            enabled = headerItem.enabled
                        )
                    }
                }
            }
            // Here are all the rows of the table.
            itemsIndexed(tableCellData) { rowIndex, rowData ->
                Row(Modifier.fillMaxWidth()) {
                    rowData.forEachIndexed { columnIndex, cellState ->

                        val focusRequester = focusRequesters[rowIndex * 4 + columnIndex]
                        TableCell(
                            text = cellState.value.currentText,
                            weight = if (columnIndex == 0) nameColumnWeight else restColumnWeight,
                            enabled = true,
                            modifier = Modifier
                                .background(
                                    if (cellState.value.hasUnsavedChanges)
                                        headerColor
                                    else transparent_color
                                ),
                            focusRequester = focusRequester,
                            keyboardType = if (columnIndex == 0) KeyboardType.Text else KeyboardType.Number,
                            onNext = {
                                val nextEmptyFocusIndex = findNextEmptyCell(
                                    rowIndex,
                                    columnIndex,
                                    rows,
                                    tableCellData
                                )
                                if (nextEmptyFocusIndex != -1) {
                                    focusRequesters[nextEmptyFocusIndex].requestFocus()
                                } else {
                                    focusRequester.requestFocus()
                                }
                            },
                            onTextChanged = { newText ->
                                val updatedCellContent = cellState.value.copy(
                                    currentText = newText,
                                    hasUnsavedChanges =
                                    newText != cellState.value.currentText &&
                                            newText != cellState.value.previousText &&
                                            newText.isNotBlank()

                                )
                                tableCellData[rowIndex][columnIndex].value = updatedCellContent
                            }
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            FloatingActionButton(
                containerColor = black_color,
                elevation = FloatingActionButtonDefaults.elevation(4.dp),
                onClick = {
                    clearTableCellData(tableCellData)
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = stringResource(id = R.string.clear),
                    tint = white_color
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            FloatingActionButton(
                containerColor = white_color,
                elevation = FloatingActionButtonDefaults.elevation(4.dp),
                onClick = {
                    //used to store the number of the exercise by their order in case if some are skipped from inserting
                    var currentIndex = 0
                    val exercisesToAdd = mutableListOf<Exercise>()
                    val exercisesToUpdate = mutableListOf<Exercise>()

                    if (tableCellData.all { row ->
                            row.all { cellState ->
                                cellState.value.currentText.isBlank()
                            }
                        }) {
                        exerciseViewModel.deleteAllExercisesByDate(date)
                        return@FloatingActionButton
                    }

                    tableCellData.forEach { rowData ->
                        val number = (currentIndex + 1).toString()
                        val name = rowData[0].value.currentText
                        val sets = rowData[1].value.currentText
                        val reps = rowData[2].value.currentText
                        val load = rowData[3].value.currentText

                        // Check if any of the values in the row are not blank
                        if (name.isNotBlank() || sets.isNotBlank() || reps.isNotBlank() || load.isNotBlank()) {
                            val existingExerciseIndex = exerciseState.exercises.indexOfFirst { it.number == number }

                            if (existingExerciseIndex != -1) {
                                // Exercise with the same number exists, update it
                                val existingExercise = exerciseState.exercises[existingExerciseIndex]
                                val exerciseToUpdate = Exercise(
                                    number = number,
                                    name = name,
                                    sets = sets,
                                    reps = reps,
                                    load = load,
                                    date = date
                                )

                                if (existingExercise != exerciseToUpdate) {

                                    exercisesToUpdate.add(exerciseToUpdate) //Fill the list to update the database

                                    //Update the values in that row to be up to date, so that the unsaved changes are now saved and ->
                                    //->the yellow background will be shown for the newly added values
                                    for (columnIndex in 0..3) {
                                        rowData[columnIndex].value.hasUnsavedChanges = false
                                        rowData[columnIndex].value.previousText = rowData[columnIndex].value.currentText
                                    }
                                }
                            } else {
                                // Exercise with the same number doesn't exist, create a new one
                                val newExercise = Exercise(
                                    number = number,
                                    name = name,
                                    sets = sets,
                                    reps = reps,
                                    load = load,
                                    date = date
                                )
                                exercisesToAdd.add(newExercise)
                                for (columnIndex in 0..3) {
                                    rowData[columnIndex].value.hasUnsavedChanges = false
                                    rowData[columnIndex].value.previousText =
                                        rowData[columnIndex].value.currentText
                                }
                            }
                            currentIndex++
                        }
                    }
                    if (exercisesToAdd.isNotEmpty()) {
                        exerciseViewModel.addExercises(exercisesToAdd)
                    }
                    if (exercisesToUpdate.isNotEmpty()) {
                        exerciseViewModel.updateExercises(exercisesToUpdate)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = stringResource(id = R.string.clear),
                    tint = black_color
                )
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = FocusRequester.Cancel,
    keyboardType: KeyboardType = KeyboardType.Text,
    onNext: (() -> Unit)? = null,
    onTextChanged: (String) -> Unit = { }
) {
    BasicTextField(
        modifier = modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
            .focusRequester(focusRequester),
        value = text,
        maxLines = 3,
        minLines = 3,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                if (onNext != null) {
                    onNext()
                }
            }
        ),
        onValueChange = onTextChanged
    )
}

data class TableCellHeaderItem(
    val text: String,
    val weight: Float,
    val enabled: Boolean,
    val columnId: Int,
    val onTextChanged: (String) -> Unit = { }
)



private fun initializeTableCellHeaderItems(
    context: Context
): List<TableCellHeaderItem> {
    val nameColumnWeight = .55f
    val restColumnWeight = .15f

    return listOf(
        TableCellHeaderItem(
            text = context.getString(R.string.exercise_name),
            weight = nameColumnWeight,
            enabled = false,
            columnId = 0
        ),
        TableCellHeaderItem(
            text = context.getString(R.string.sets),
            weight = restColumnWeight,
            enabled = false,
            columnId = 1
        ),
        TableCellHeaderItem(
            text = context.getString(R.string.reps),
            weight = restColumnWeight,
            enabled = false,
            columnId = 2
        ),
        TableCellHeaderItem(
            text = context.getString(R.string.load),
            weight = restColumnWeight,
            enabled = false,
            columnId = 3
        )
    )
}