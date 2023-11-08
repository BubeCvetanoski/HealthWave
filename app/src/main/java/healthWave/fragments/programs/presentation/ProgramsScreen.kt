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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthwave.R
import com.ramcosta.composedestinations.annotation.Destination
import healthWave.core.util.HelperFunctions
import healthWave.core.util.Programs
import healthWave.launcher.presentation.viewmodel.SharedUserViewModel
import healthWave.ui.components.LargeDropdownSpinner
import healthWave.ui.components.ProgramsListItemCard
import healthWave.ui.components.ProgramsListItemHeader
import healthWave.ui.theme.black_color
import healthWave.ui.theme.gray_level_1
import healthWave.ui.theme.transparent_color

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ProgramsScreen(
    sharedUserViewModel: SharedUserViewModel = hiltViewModel()
) {
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

    val baseColor = remember { mutableStateOf(Color.Unspecified) }
    val detailsColor = remember { mutableStateOf(Color.Unspecified) }
    val backgroundColor = remember { mutableStateOf(Color.Unspecified) }
    val itemsColor = remember { mutableStateOf(Color.Unspecified) }

    baseColor.value = sharedUserViewModel.getHealthWaveColors().first
    detailsColor.value = sharedUserViewModel.getHealthWaveColors().second
    backgroundColor.value = sharedUserViewModel.getCurrentApplicationThemeColors().first
    itemsColor.value = sharedUserViewModel.getCurrentApplicationThemeColors().second

    val spinnerColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = black_color,
        cursorColor = gray_level_1,
        containerColor = transparent_color,
        focusedBorderColor = detailsColor.value,
        unfocusedBorderColor = detailsColor.value,
        focusedLabelColor = detailsColor.value,
        unfocusedLabelColor = black_color
    )

    BackHandler {}
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = backgroundColor.value
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
                onItemSelected = { index, item ->
                    selectedIndexDays = index
                    numberOfDays =
                        if (selectedIndexDays == 0) 0 // It's 0 because on 0 index all programs should be shown
                        else item.substringBefore(" day").toInt()
                },
                onValidate = {}
            )
            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn {
                items(programsList.filter { program->
                    (program.days == numberOfDays) || (numberOfDays == 0)
                }) { item ->
                    ProgramsListItemHeader(
                        cardHeaderColor = itemsColor.value,
                        program = item
                    ) {
                        program = item
                        isProgramExpanded = !isProgramExpanded
                    }
                }
            }
        }
        if (isProgramExpanded) {
            ProgramsListItemCard(
                program = program,
                themeColor = backgroundColor.value
            ) {
                isProgramExpanded = false
            }
        }
    }
}

data class ProgramsListItem(
    val title: String,
    val text: String,
    val days: Int
)

fun initializeProgramsList(): List<ProgramsListItem> {
    return listOf(
        ProgramsListItem(
            title = "Program 1",
            text = Programs.PROGRAM1,
            days = 1
        ),
        ProgramsListItem(
            title = "Program 2",
            text = Programs.PROGRAM2,
            days = 1
        ),
        ProgramsListItem(
            title = "Program 3",
            text = Programs.PROGRAM3,
            days = 2
        ),
        ProgramsListItem(
            title = "Program 4",
            text = Programs.PROGRAM4,
            days = 2
        ),
        ProgramsListItem(
            title = "Program 5",
            text = Programs.PROGRAM5,
            days = 3
        ),
        ProgramsListItem(
            title = "Program 6",
            text = Programs.PROGRAM6,
            days = 3
        ),
        ProgramsListItem(
            title = "Program 7",
            text = Programs.PROGRAM7,
            days = 3
        ),
        ProgramsListItem(
            title = "Program 8",
            text = Programs.PROGRAM8,
            days = 4
        ),
        ProgramsListItem(
            title = "Program 9",
            text = Programs.PROGRAM9,
            days = 4
        ),
        ProgramsListItem(
            title = "Program 10",
            text = Programs.PROGRAM10,
            days = 4
        ),
        ProgramsListItem(
            title = "Program 11",
            text = Programs.PROGRAM11,
            days = 5
        ),
        ProgramsListItem(
            title = "Program 12",
            text = Programs.PROGRAM12,
            days = 5
        ),
        ProgramsListItem(
            title = "Program 13",
            text = Programs.PROGRAM13,
            days = 5
        ),
        ProgramsListItem(
            title = "Program 14",
            text = Programs.PROGRAM14,
            days = 6
        ),
        ProgramsListItem(
            title = "Program 15",
            text = Programs.PROGRAM15,
            days = 6
        ),
        ProgramsListItem(
            title = "Program 16",
            text = Programs.PROGRAM16,
            days = 6
        ),
        ProgramsListItem(
            title = "Program 17",
            text = Programs.PROGRAM17,
            days = 7
        ),
        ProgramsListItem(
            title = "Program 18",
            text = Programs.PROGRAM18,
            days = 7
        ),
        ProgramsListItem(
            title = "Program 19",
            text = Programs.PROGRAM19,
            days = 7
        ),
        ProgramsListItem(
            title = "Program 20",
            text = Programs.PROGRAM20,
            days = 1
        ),
        ProgramsListItem(
            title = "Program 21",
            text = Programs.PROGRAM21,
            days = 2
        ),
        ProgramsListItem(
            title = "Program 22",
            text = Programs.PROGRAM22,
            days = 3
        ),
        ProgramsListItem(
            title = "Program 23",
            text = Programs.PROGRAM23,
            days = 4
        ),
        ProgramsListItem(
            title = "Program 24",
            text = Programs.PROGRAM24,
            days = 5
        ),
        ProgramsListItem(
            title = "Program 25",
            text = Programs.PROGRAM25,
            days = 6
        )
    )
}