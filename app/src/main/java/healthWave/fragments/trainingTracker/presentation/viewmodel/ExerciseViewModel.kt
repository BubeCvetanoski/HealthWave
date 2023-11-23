package healthWave.fragments.trainingTracker.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
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
import healthWave.fragments.trainingTracker.domain.useCase.ExerciseUseCases
import healthWave.fragments.trainingTracker.event.TrainingTrackerEvent
import healthWave.fragments.trainingTracker.presentation.screen.TableCellDataItem
import healthWave.fragments.trainingTracker.state.ExerciseState
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val exerciseUseCases: ExerciseUseCases
) : ViewModel() {

    private val _exerciseState = mutableStateOf(ExerciseState())
    val exerciseState: State<ExerciseState> = _exerciseState

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
            is TrainingTrackerEvent.UpdateExercises -> updateExercises(event.exercises)
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
        _exerciseState.value = exerciseState.value.copy(isLoading = true)

        viewModelScope.launch {
            exercises.forEach { exercise ->
                insertExercise(exercise)
            }

            _exerciseState.value = exerciseState.value.copy(
                exercises = exercises,
                isLoading = false
            )

            // All exercises are inserted, now send the toast message
            _uiEvent.send(
                UiEvent.ShowToast(
                    UiText.StringResource(resId = R.string.successfully_added)
                )
            )
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
        //used to store the number of the exercise by their order in case if some are skipped from inserting
        var currentIndex = 0
        val exercisesToAdd = mutableListOf<Exercise>()
        val exercisesToUpdate = mutableListOf<Exercise>()

        if (tableCellData!!.all { row ->
                row.all { cellState ->
                    cellState.value.currentText.isBlank()
                }
            }) {
            deleteAllExercisesByDate(date = date)
            return
        }

        tableCellData!!.forEach { rowData ->
            val number = (currentIndex + 1).toString()
            val name = rowData[0].value.currentText
            val sets = rowData[1].value.currentText
            val reps = rowData[2].value.currentText
            val load = rowData[3].value.currentText

            // Check if any of the values in the row are not blank
            if (name.isNotBlank() || sets.isNotBlank() || reps.isNotBlank() || load.isNotBlank()) {
                val existingExerciseIndex =
                    _exerciseState.value.exercises.indexOfFirst { it.number == number }

                if (existingExerciseIndex != -1) {
                    // Exercise with the same number exists, update it
                    val existingExercise =
                        _exerciseState.value.exercises[existingExerciseIndex]
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
                currentIndex++
            }
        }
        if (exercisesToAdd.isNotEmpty()) {
            addExercises(exercisesToAdd)
        }
        if (exercisesToUpdate.isNotEmpty()) {
            updateExercises(exercisesToUpdate)
        }
    }

    private fun getExercisesByDate(date: String) {
        getExercisesJob?.cancel()
        _exerciseState.value = exerciseState.value.copy(isLoading = true)

        getExercisesJob = exerciseUseCases.getExercisesByDate(date)
            .onEach { exercises ->
                _exerciseState.value = exerciseState.value.copy(
                    exercises = exercises,
                    isLoading = false
                )
            }.launchIn(viewModelScope)
    }

    private fun updateExercises(exercises: List<Exercise>) {
        _exerciseState.value = exerciseState.value.copy(isLoading = true)

        viewModelScope.launch {
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
            _exerciseState.value = exerciseState.value.copy(
                exercises = exercises,
                isLoading = false
            )
        }
    }

    private fun deleteAllExercisesByDate(date: String) {
        _exerciseState.value = exerciseState.value.copy(isLoading = true)

        viewModelScope.launch {
            exerciseUseCases.deleteAllExercisesByDate(date)

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
            exerciseUseCases.addExercise(exercise)
            _exerciseState.value = exerciseState.value.copy(
                exercises = exerciseState.value.exercises + exercise
            )
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
            hasUnsavedChanges =
            newText != cellState.value.currentText &&
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
            exerciseUseCases.updateExerciseByNumberAndDate(
                name,
                sets,
                reps,
                load,
                number,
                date
            )
            //After the exercise is updated inside database, create a exercise with the updated values
            //and find the same exercise inside the List in order to update the exercise's values inside the list as well
            val updatedExercise = Exercise(
                name = name,
                sets = sets,
                reps = reps,
                load = load,
                number = number,
                date = date
            )
            val updatedExerciseList = exerciseState.value.exercises.map { exercise ->
                if (exercise.number == number && exercise.date == date) {
                    updatedExercise
                } else {
                    exercise
                }
            }
            _exerciseState.value = exerciseState.value.copy(
                exercises = updatedExerciseList
            )
        }
    }
}