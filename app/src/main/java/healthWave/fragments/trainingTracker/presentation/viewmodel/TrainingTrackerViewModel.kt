package healthWave.fragments.trainingTracker.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthwave.R
import dagger.hilt.android.lifecycle.HiltViewModel
import healthWave.core.util.HelperFunctions.Companion.getCurrentDateAsString
import healthWave.core.util.UiEvent
import healthWave.core.util.UiText
import healthWave.data.local.database.entity.Exercise
import healthWave.fragments.trainingTracker.domain.useCase.TrainingTrackerUseCases
import healthWave.fragments.trainingTracker.presentation.event.TrainingTrackerEvent
import healthWave.fragments.trainingTracker.presentation.screen.TableCellDataItem
import healthWave.fragments.trainingTracker.presentation.state.ExerciseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TrainingTrackerViewModel @Inject constructor(
    private val trainingTrackerUseCases: TrainingTrackerUseCases
) : ViewModel() {

    private val _exerciseState = MutableStateFlow(ExerciseState())
    val exerciseState get() = _exerciseState.asStateFlow()

    var tableCellData: List<List<MutableState<TableCellDataItem>>>? = null

    private var getExercisesJob: Job? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getExercisesByDate(date = getCurrentDateAsString())
    }

    fun onEvent(event: TrainingTrackerEvent) {
        when (event) {
            is TrainingTrackerEvent.GetExerciseByDate -> getExercisesByDate(event.date)
            is TrainingTrackerEvent.UpdateCellData -> updateTableCellData(event.numberOfExercises)
            is TrainingTrackerEvent.InitializeTableCellData -> initializeTableCellData(
                event.numberOfExercises,
                event.defaultItem
            )

            is TrainingTrackerEvent.OnApplyClicked -> onApplyClicked(event.date)
            is TrainingTrackerEvent.OnCustomTableTextChanged -> onCustomTableTextChanged(
                event.cellState,
                event.newText,
                event.rowIndex,
                event.columnIndex
            )

            is TrainingTrackerEvent.OnCustomTableNextClicked -> onCustomTableNextClicked(
                event.rowIndex,
                event.columnIndex,
                event.rows,
                event.focusRequester,
                event.focusRequesters
            )

            is TrainingTrackerEvent.ClearTableCellData -> clearTableCellData()
        }
    }

    // Function to find the index of the next empty cell in the table
    private fun findNextEmptyCell(
        currentRowIndex: Int,
        currentColumnIndex: Int,
        rows: Int,
        tableCellData: List<List<MutableState<TableCellDataItem>>>
    ): Int {
        for (row in currentRowIndex until rows) {
            for (column in (if (row == currentRowIndex) currentColumnIndex + 1 else 0) until 4) {
                if (tableCellData[row][column].value.currentText.isBlank()) {
                    return row * 4 + column
                }
            }
        }
        return -1 // Return -1 if no empty cell is found
    }

    private fun onCustomTableNextClicked(
        rowIndex: Int,
        columnIndex: Int,
        rows: Int,
        focusRequester: FocusRequester,
        focusRequesters: List<FocusRequester>
    ) {
        val nextEmptyFocusIndex = findNextEmptyCell(
            rowIndex,
            columnIndex,
            rows,
            tableCellData!!
        )
        if (nextEmptyFocusIndex != -1) {
            focusRequesters[nextEmptyFocusIndex].requestFocus()
        } else {
            focusRequester.requestFocus()
        }
    }

    private fun addExercises(exercises: List<Exercise>) {
        exercises.forEach { exercise ->
            insertExercise(exercise)
        }
    }

    private inline fun <reified T> initializeRowsAndColumns(
        numberOfRows: Int,
        defaultItem: T
    ): List<List<MutableState<T>>> {
        return List(numberOfRows) {
            List(4) { mutableStateOf(defaultItem) }
        }
    }

    private fun initializeTableCellData(
        numberOfExercises: Int,
        defaultItem: TableCellDataItem
    ) {
        tableCellData = initializeRowsAndColumns(
            numberOfRows = numberOfExercises,
            defaultItem = defaultItem
        )
    }

    private fun onApplyClicked(
        date: String
    ) {
        viewModelScope.launch {

            if (tableCellData!!.all { row ->
                    row.all { cellState ->
                        cellState.value.currentText.isBlank()
                    }
                }) {
                deleteAllExercisesByDate(date = date)
                return@launch
            }

            //used to store the number of the exercise by their order in case if some are skipped from inserting
            var currentIndex = 0
            val exercisesToAdd = mutableListOf<Exercise>()
            val exercisesToUpdate = mutableListOf<Exercise>()
            //list of String because only the number of exercises will be sent on deletion, no need to keep whole Exercise class
            val exercisesToDelete = mutableListOf<String>()

            isLoading(flag = true)

            tableCellData!!.forEach { rowData ->
                val number = (currentIndex + 1).toString()
                val name = rowData[0].value.currentText
                val sets = rowData[1].value.currentText
                val reps = rowData[2].value.currentText
                val load = rowData[3].value.currentText

                if (rowData[0].value.currentText == rowData[0].value.previousText &&
                    rowData[1].value.currentText == rowData[1].value.previousText &&
                    rowData[2].value.currentText == rowData[2].value.previousText &&
                    rowData[3].value.currentText == rowData[3].value.previousText
                ) {
                    isLoading(flag = false)
                    currentIndex++
                    return@forEach
                    //If apply is clicked and the current data is same as previous data, then the next code doesn't have to be executed
                }

                // Check if any of the values in the row are not blank
                if (name.isNotBlank() || sets.isNotBlank() || reps.isNotBlank() || load.isNotBlank()) {
                    val existingExerciseIndex =
                        _exerciseState.value.exercises.indexOfFirst { it.number == number }

                    if (existingExerciseIndex != -1) {
                        // Exercise with the same number exists, update it
                        val existingExercise = _exerciseState.value.exercises[existingExerciseIndex]
                        val exerciseToUpdate = Exercise(
                            number = number,
                            name = name,
                            sets = sets,
                            reps = reps,
                            load = load,
                            date = date,
                            id = existingExercise.id //this is not optimal, but it's correct, since if the exercise already exists, then it is safe to get its id,
                            // what we're really interested in is if there are differences in the other properties
                        )

                        if (existingExercise != exerciseToUpdate) {

                            exercisesToUpdate.add(exerciseToUpdate) //Fill the list to update the database

                            //Update the values in that row to be up to date, so that the unsaved changes are now saved and ->
                            //->the yellow background will be shown for the newly added values
                            for (columnIndex in 0..3) {
                                rowData[columnIndex].value.hasUnsavedChanges = false
                                rowData[columnIndex].value.previousText =
                                    rowData[columnIndex].value.currentText
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
                } else {
                    if (rowData[0].value.previousText.isNotBlank() ||
                        rowData[1].value.previousText.isNotBlank() ||
                        rowData[2].value.previousText.isNotBlank() ||
                        rowData[3].value.previousText.isNotBlank()
                    ) {
                        exercisesToDelete.add(number)
                    }
                }
                currentIndex++
            }

            if (exercisesToAdd.isNotEmpty()) {
                withContext(Dispatchers.IO) {
                    addExercises(exercisesToAdd)
                }
            }
            if (exercisesToUpdate.isNotEmpty()) {
                withContext(Dispatchers.IO) {
                    updateExercises(exercisesToUpdate)
                }
            }
            if (exercisesToDelete.isNotEmpty()) {
                withContext(Dispatchers.IO) {
                    deleteExercises(exercisesToDelete, date)
                }
            }
            cleanUpTheChanges(date) // Execute cleanUpTheChanges after all jobs have completed
            isLoading(flag = false)
        }
    }

    private fun isLoading(flag: Boolean) {
        _exerciseState.value = exerciseState.value.copy(
            isLoading = flag
        )
    }

    private suspend fun deleteExercises(numbersOfExercises: List<String>, date: String) {
        val idsToBeDeleted = getExerciseIdsByNumberAndDate(
            numbersOfExercises = numbersOfExercises,
            date = date
        )
        trainingTrackerUseCases.deleteExercisesById(idsToBeDeleted, date)
    }

    private suspend fun cleanUpTheChanges(date: String) {
        val exercisesToBeOrdered = trainingTrackerUseCases
            .getExercisesByDate(date)
            .firstOrNull() ?: emptyList()
        val orderedList = orderTheExerciseNumbers(exercisesToBeOrdered)

        orderedList.forEach { exercise ->
            trainingTrackerUseCases.updateExerciseNumberByIdAndDate(
                exercise.number,
                exercise.id!!,
                exercise.date
            )
        }
        getExercisesByDate(date)
    }

    private fun orderTheExerciseNumbers(exercises: List<Exercise>): List<Exercise> {
        val sortedExercises = exercises.sortedBy { it.number.toInt() }

        return sortedExercises.mapIndexed { index, exercise ->
            exercise.copy(
                number = (index + 1).toString(),
                name = exercise.name,
                sets = exercise.sets,
                reps = exercise.reps,
                load = exercise.load,
                date = exercise.date,
                id = exercise.id
            )
        }
    }

    private suspend fun getExerciseIdsByNumberAndDate(
        numbersOfExercises: List<String>,
        date: String
    ): List<Int> = trainingTrackerUseCases.getExerciseIdsByNumberAndDate(numbersOfExercises, date)

    private fun getExercisesByDate(date: String) {
        getExercisesJob?.cancel()

        getExercisesJob = trainingTrackerUseCases.getExercisesByDate(date)
            .onEach { exercises ->
                _exerciseState.value = exerciseState.value.copy(
                    exercises = exercises
                )
            }.launchIn(viewModelScope)
    }

    private fun updateExercises(exercises: List<Exercise>) {
        exercises.forEach { exercise ->
            updateExerciseByNumberAndDate(
                exercise.name,
                exercise.sets,
                exercise.reps,
                exercise.load,
                exercise.number,
                exercise.date
            )
        }
    }

    private fun deleteAllExercisesByDate(date: String) {
        isLoading(flag = true)

        viewModelScope.launch {
            trainingTrackerUseCases.deleteAllExercisesByDate(date)

            _exerciseState.value = exerciseState.value.copy(
                exercises = emptyList(),
                isLoading = false
            )
            _uiEvent.send(
                UiEvent.ShowToast(
                    UiText.StringResource(resId = R.string.successfully_deleted)
                )
            )
        }
    }

    private fun insertExercise(exercise: Exercise) {
        viewModelScope.launch {
            trainingTrackerUseCases.addExercise(exercise)
        }
    }

    private fun updateTableCellData(numberOfExercises: Int) {
        tableCellData = updateRows(
            initializeRowsAndColumns(
                numberOfExercises,
                TableCellDataItem("", "")
            ),
            _exerciseState.value.exercises
        )
    }

    private fun clearTableCellData() {
        tableCellData!!.forEach { row ->
            row.forEach { cellState ->
                cellState.value = cellState.value.copy(
                    currentText = "",
                    previousText = "",
                    hasUnsavedChanges = false
                )
            }
        }
    }


    private fun onCustomTableTextChanged(
        cellState: MutableState<TableCellDataItem>,
        newText: String,
        rowIndex: Int,
        columnIndex: Int
    ) {
        val updatedCellContent = cellState.value.copy(
            currentText = newText,
            hasUnsavedChanges = newText != cellState.value.currentText &&
                    newText != cellState.value.previousText &&
                    newText.isNotBlank()

        )
        tableCellData!![rowIndex][columnIndex].value = updatedCellContent
    }

    private fun updateRows(
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

    private fun updateExerciseByNumberAndDate(
        name: String,
        sets: String,
        reps: String,
        load: String,
        number: String,
        date: String
    ) {
        viewModelScope.launch {
            trainingTrackerUseCases.updateExerciseByNumberAndDate(
                name,
                sets,
                reps,
                load,
                number,
                date
            )
        }
    }
}