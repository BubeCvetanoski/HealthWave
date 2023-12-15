package healthWave.fragments.programs.presentation.event

import healthWave.fragments.programs.presentation.screen.ProgramsListItem

sealed class ProgramsEvent {
    data class ProgramsItemSelected(val index: Int, val item: String) : ProgramsEvent()
    data class ProgramsListHeaderClicked(val item: ProgramsListItem) : ProgramsEvent()
    data object ProgramsListItemCardClicked : ProgramsEvent()
}
