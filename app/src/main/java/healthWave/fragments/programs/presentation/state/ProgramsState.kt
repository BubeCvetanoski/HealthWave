package healthWave.fragments.programs.presentation.state

import healthWave.fragments.programs.presentation.screen.ProgramsListItem

data class ProgramsState(
    var numberOfDays: Int = 0,
    var selectedIndexDays: Int = -1,
    var program: ProgramsListItem = ProgramsListItem(title = "", text = "", days = 0),
    var isProgramExpanded: Boolean = false
)
