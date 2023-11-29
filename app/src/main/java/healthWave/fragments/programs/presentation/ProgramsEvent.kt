package healthWave.fragments.programs.presentation

sealed class ProgramsEvent {
    data class OnProgramsItemSelected(val index: Int, val item: String) : ProgramsEvent()
    data class OnProgramsListHeaderClicked(val item: ProgramsListItem) : ProgramsEvent()
    data object OnProgramsListItemCardClicked : ProgramsEvent()
}
