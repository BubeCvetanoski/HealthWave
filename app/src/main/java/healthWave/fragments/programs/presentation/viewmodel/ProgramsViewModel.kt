package healthWave.fragments.programs.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import healthWave.core.util.HelperFunctions
import healthWave.core.util.HelperFunctions.Companion.initializeProgramsList
import healthWave.fragments.programs.presentation.event.ProgramsEvent
import healthWave.fragments.programs.presentation.screen.ProgramsListItem
import healthWave.fragments.programs.presentation.state.ProgramsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProgramsViewModel @Inject constructor() : ViewModel() {

    private val _programsState = MutableStateFlow(ProgramsState())
    val programsState get() = _programsState.asStateFlow()

    private val programsList = initializeProgramsList()
    var filteredProgramsList: List<ProgramsListItem> = programsList
        private set

    val spinnerList = HelperFunctions.createListForSpinner(0..7) { number ->
        when (number) {
            0 -> "All programs"
            1 -> "$number day split"
            else -> "$number days split"
        }
    }

    fun onEvent(event: ProgramsEvent) {
        when (event) {
            is ProgramsEvent.OnProgramsItemSelected -> onProgramsItemSelected(event.index, event.item)
            is ProgramsEvent.OnProgramsListHeaderClicked -> onProgramsListHeaderClicked(event.item)
            is ProgramsEvent.OnProgramsListItemCardClicked -> onProgramsListItemCardClicked()
        }
    }

    private fun updateFilteredProgramsList() {
        filteredProgramsList = programsList.filter { program ->
            (program.days == _programsState.value.numberOfDays) || (_programsState.value.numberOfDays == 0)
        }
    }
    private fun onProgramsItemSelected(index: Int, item: String) {
        _programsState.value = programsState.value.copy(
            selectedIndexDays = index,
            numberOfDays = if (index == 0) 0
            else item.substringBefore(" day").toInt()
        )
        updateFilteredProgramsList()
    }

    private fun onProgramsListHeaderClicked(item: ProgramsListItem) {
        val newState = programsState.value.copy(
            program = item,
            isProgramExpanded = !_programsState.value.isProgramExpanded
        )
        _programsState.value = newState
    }

    private fun onProgramsListItemCardClicked() {
        _programsState.value = programsState.value.copy(
            isProgramExpanded = false
        )
    }
}