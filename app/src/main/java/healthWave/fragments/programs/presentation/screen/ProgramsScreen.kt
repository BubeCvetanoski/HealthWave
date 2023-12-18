package healthWave.fragments.programs.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthwave.R
import com.ramcosta.composedestinations.annotation.Destination
import healthWave.core.util.HelperFunctions
import healthWave.fragments.programs.presentation.viewmodel.ProgramsViewModel
import healthWave.fragments.programs.presentation.event.ProgramsEvent
import healthWave.ui.components.LargeDropdownSpinner
import healthWave.ui.components.ProgramsListItemCard
import healthWave.ui.components.ProgramsListItemHeader
import healthWave.ui.theme.HealthWaveColorScheme
import healthWave.ui.theme.black_color

@Destination
@Composable
fun ProgramsScreen(
    programsViewModel: ProgramsViewModel = hiltViewModel()
) {
    val spinnerColors = HelperFunctions.initializeOutlinedTextFieldColors(textColor = black_color)
    val state = programsViewModel.programsState.collectAsState().value

    BackHandler {}
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = HealthWaveColorScheme.backgroundColor
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            LargeDropdownSpinner(
                label = stringResource(id = R.string.number_of_days),
                colors = spinnerColors,
                items = programsViewModel.spinnerList,
                selectedIndex = state.selectedIndexDays,
                onItemSelected = { index, item ->
                    programsViewModel.onEvent(
                        ProgramsEvent.OnProgramsItemSelected(index, item)
                    )
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn {
                items(programsViewModel.filteredProgramsList) { item ->
                    ProgramsListItemHeader(
                        program = item,
                        onClick = {
                            programsViewModel.onEvent(
                                ProgramsEvent.OnProgramsListHeaderClicked(item)
                            )
                        }
                    )
                }
            }
        }
        if (state.isProgramExpanded) {
            ProgramsListItemCard(
                program = state.program,
                onClick = {
                    programsViewModel.onEvent(
                        ProgramsEvent.OnProgramsListItemCardClicked
                    )
                }
            )
        }
    }
}

data class ProgramsListItem(
    val title: String,
    val text: String,
    val days: Int
)