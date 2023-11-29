package healthWave.fragments.programs.presentation

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import healthWave.core.util.HelperFunctions
import healthWave.core.util.HelperFunctions.Companion.initializeProgramsList
import javax.inject.Inject

@HiltViewModel
class ProgramsViewModel @Inject constructor() : ViewModel() {
    private var numberOfDays by mutableStateOf(0)
    var selectedIndexDays by mutableStateOf(-1)
    var program = ProgramsListItem(title = "", text = "", days = 0)
    var isProgramExpanded by mutableStateOf(false)

    private val programsList = initializeProgramsList()

    val spinnerList = HelperFunctions.createListForSpinner(0..7) { number ->
        when (number) {
            0 -> "All programs"
            1 -> "$number day split"
            else -> "$number days split"
        }
    }

    val filteredProgramsList: List<ProgramsListItem> by derivedStateOf {
        programsList.filter { program ->
            (program.days == numberOfDays) || (numberOfDays == 0)
        }
    }

    fun onEvent(event: ProgramsEvent) {
        when (event) {
            is ProgramsEvent.OnProgramsItemSelected -> onProgramsItemSelected(event.index, event.item)
            is ProgramsEvent.OnProgramsListHeaderClicked -> onProgramsListHeaderClicked(event.item)
            is ProgramsEvent.OnProgramsListItemCardClicked -> onProgramsListItemCardClicked()
        }
    }
    private fun onProgramsItemSelected(index: Int, item: String) {
        selectedIndexDays = index
        numberOfDays = if (selectedIndexDays == 0) 0
        else item.substringBefore(" day").toInt()
    }

    private fun onProgramsListHeaderClicked(item: ProgramsListItem) {
        program = item
        isProgramExpanded = !isProgramExpanded
    }

    private fun onProgramsListItemCardClicked() {
        isProgramExpanded = false
    }
}