package healthWave.fragments.trainingTracker.presentation.event

import androidx.compose.runtime.MutableState
import androidx.compose.ui.focus.FocusRequester
import healthWave.data.local.database.entity.Exercise
import healthWave.fragments.trainingTracker.presentation.screen.TableCellDataItem

sealed class TrainingTrackerEvent {
    data class GetExerciseByDate(val date: String) : TrainingTrackerEvent()
    data class UpdateExercises(val exercises: List<Exercise>) : TrainingTrackerEvent()
    data class UpdateCellData(val numberOfExercises: Int) : TrainingTrackerEvent()
    data class InitializeTableCellData(
        val numberOfExercises: Int,
        val defaultItem: TableCellDataItem
    ) : TrainingTrackerEvent()

    data class ApplyClicked(val date: String) : TrainingTrackerEvent()
    data class CustomTableTextChanged(
        val cellState: MutableState<TableCellDataItem>,
        val newText: String,
        val rowIndex: Int,
        val columnIndex: Int
    ) : TrainingTrackerEvent()

    data class CustomTableNextClicked(
        val rowIndex: Int,
        val columnIndex: Int,
        val rows: Int,
        val focusRequester: FocusRequester,
        val focusRequesters: List<FocusRequester>
    ) : TrainingTrackerEvent()

    data object ClearTableCellData : TrainingTrackerEvent()

}
