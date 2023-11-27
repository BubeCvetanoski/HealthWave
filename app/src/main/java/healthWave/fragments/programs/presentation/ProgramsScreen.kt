package healthWave.fragments.programs.presentation

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.healthwave.R
import com.ramcosta.composedestinations.annotation.Destination
import healthWave.core.util.HelperFunctions
import healthWave.core.util.HelperFunctions.Companion.initializeProgramsList
import healthWave.ui.components.LargeDropdownSpinner
import healthWave.ui.components.ProgramsListItemCard
import healthWave.ui.components.ProgramsListItemHeader
import healthWave.ui.theme.HealthWaveColorScheme
import healthWave.ui.theme.black_color

@Destination
@Composable
fun ProgramsScreen() {
    var selectedIndexDays by remember { mutableStateOf(-1) }
    var numberOfDays by remember { mutableStateOf(0) }
    val spinnerList = HelperFunctions.createListForSpinner(0..7) { number ->
        when (number) {
            0 -> "All programs"
            1 -> "$number day split"
            else -> "$number days split"
        }
    }
    var isProgramExpanded by remember { mutableStateOf(false) }
    var program = ProgramsListItem(title = "", text = "", days = 0)
    val programsList = initializeProgramsList()

    val onProgramsItemSelected: (index: Int, item: String) -> Unit = { index, item ->
        selectedIndexDays = index
        numberOfDays =
            if (selectedIndexDays == 0) 0 // It's 0 because on 0 index all programs should be shown
            else item.substringBefore(" day").toInt()
    }

    val onProgramsListHeaderClicked: (item: ProgramsListItem) -> Unit = { item ->
        program = item
        isProgramExpanded = !isProgramExpanded
    }

    val onProgramsListItemCardClicked: () -> Unit = {
        isProgramExpanded = false
    }

    val spinnerColors = HelperFunctions.initializeOutlinedTextFieldColors(textColor = black_color)

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
                items = spinnerList,
                selectedIndex = selectedIndexDays,
                onItemSelected = onProgramsItemSelected
            )
            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn {
                items(programsList.filter { program ->
                    (program.days == numberOfDays) || (numberOfDays == 0)
                }) { item ->
                    ProgramsListItemHeader(
                        program = item,
                        onClick = { onProgramsListHeaderClicked(item) }
                    )
                }
            }
        }
        if (isProgramExpanded) {
            ProgramsListItemCard(
                program = program,
                onClick = onProgramsListItemCardClicked
            )
        }
    }
}

data class ProgramsListItem(
    val title: String,
    val text: String,
    val days: Int
)