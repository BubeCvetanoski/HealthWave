package healthWave.fragments.trainingTracker.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthwave.R
import dagger.hilt.android.lifecycle.HiltViewModel
import healthWave.core.util.HelperFunctions.Companion.getCurrentDateAsString
import healthWave.core.util.UiEvent
import healthWave.core.util.UiText
import healthWave.data.local.database.entity.Exercise
import healthWave.fragments.trainingTracker.domain.useCase.ExerciseUseCases
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

    fun addExercises(exercises: List<Exercise>) {
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

    fun getExercisesByDate(date: String) {
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

    fun updateExercises(exercises: List<Exercise>) {
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

    fun deleteAllExercisesByDate(date: String) {
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