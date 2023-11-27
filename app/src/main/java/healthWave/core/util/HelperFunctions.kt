package healthWave.core.util

import android.content.Context
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.EggAlt
import androidx.compose.material.icons.twotone.Icecream
import androidx.compose.material.icons.twotone.LocalDrink
import androidx.compose.material.icons.twotone.LunchDining
import androidx.compose.material.icons.twotone.RamenDining
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.healthwave.R
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import healthWave.data.local.database.entity.User
import healthWave.fragments.calorieTracker.presentation.screen.EatingOccasionItem
import healthWave.fragments.calorieTracker.presentation.state.OverviewState
import healthWave.fragments.programs.presentation.ProgramsListItem
import healthWave.ui.components.InformativeTextItem
import healthWave.ui.components.TableCellHeaderItem
import healthWave.ui.theme.HealthWaveColorScheme
import healthWave.ui.theme.gray_level_1
import healthWave.ui.theme.transparent_color
import healthWave.ui.theme.white_color
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class HelperFunctions {
    companion object {
        fun createListForSpinner(
            range: IntRange,
            text: (Int) -> String
        ): List<String> {
            return range.map { number ->
                text(number)
            }
        }

        fun createListForSpinner(
            range: IntRange,
            text: String
        ): List<String> {
            return range.map { number ->
                "$number $text"
            }
        }

        private fun showToast(
            context: Context,
            message: String
        ) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        fun getCurrentDateAsString(): String {
            val currentDate = Date()
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            return dateFormat.format(currentDate)
        }

        fun navigateTo(
            screen: Direction,
            popUpToRoute: String,
            navigator: DestinationsNavigator
        ) {
            navigator.navigate(screen) {
                popUpTo(popUpToRoute) {
                    inclusive = true
                }
            }
        }

        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun initializeOutlinedTextFieldColors(
            textColor: Color = white_color
        ): TextFieldColors {
            return TextFieldDefaults.outlinedTextFieldColors(
                textColor = textColor,
                cursorColor = gray_level_1,
                containerColor = transparent_color,
                focusedBorderColor = HealthWaveColorScheme.detailsElementsColor,
                unfocusedBorderColor = HealthWaveColorScheme.detailsElementsColor,
                focusedLabelColor = HealthWaveColorScheme.detailsElementsColor,
                unfocusedLabelColor = textColor
            )
        }

        @Composable
        fun CollectUiEvents(uiEvent: Flow<UiEvent>, viewModel: ViewModel, context: Context) {
            LaunchedEffect(viewModel) {
                uiEvent.collect { event ->
                    when (event) {
                        is UiEvent.ShowToast -> {
                            showToast(
                                context = context,
                                message = event.message.asString(context)
                            )
                        }
                    }
                }
            }
        }

        fun Context.initializeInformativeTextItem(
            user: User
        ): List<InformativeTextItem> {

            return listOf(
                InformativeTextItem(
                    title = getString(R.string.gender),
                    text = user.gender
                ),
                InformativeTextItem(
                    title = getString(R.string.age),
                    text = user.age
                ),
                InformativeTextItem(
                    title = getString(R.string.height),
                    text = user.height
                ),
                InformativeTextItem(
                    title = getString(R.string.weight),
                    text = user.weight
                ),
                InformativeTextItem(
                    title = getString(R.string.goal),
                    text = user.goal + " with " + user.calories + " calories"
                )
            )
        }

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

        fun Context.initializeEatingOccasionItems(
            overviewState: OverviewState
        ): List<EatingOccasionItem> {
            return listOf(
                EatingOccasionItem(
                    icon = Icons.TwoTone.EggAlt,
                    title = getString(R.string.breakfast),
                    calories = overviewState.breakfastCalories
                ),
                EatingOccasionItem(
                    icon = Icons.TwoTone.LunchDining,
                    title = getString(R.string.lunch),
                    calories = overviewState.lunchCalories
                ),
                EatingOccasionItem(
                    icon = Icons.TwoTone.RamenDining,
                    title = getString(R.string.dinner),
                    calories = overviewState.dinnerCalories
                ),
                EatingOccasionItem(
                    icon = Icons.TwoTone.Icecream,
                    title = getString(R.string.snack),
                    calories = overviewState.snackCalories
                ),
                EatingOccasionItem(
                    icon = Icons.TwoTone.LocalDrink,
                    title = getString(R.string.water),
                    calories = overviewState.overallWaterIntake
                )
            )
        }

        fun Context.initializeTableCellHeaderItems(): List<TableCellHeaderItem> {
            val nameColumnWeight = .55f
            val restColumnWeight = .15f

            return listOf(
                TableCellHeaderItem(
                    text = getString(R.string.exercise_name),
                    weight = nameColumnWeight,
                    enabled = false,
                    columnId = 0
                ),
                TableCellHeaderItem(
                    text = getString(R.string.sets),
                    weight = restColumnWeight,
                    enabled = false,
                    columnId = 1
                ),
                TableCellHeaderItem(
                    text = getString(R.string.reps),
                    weight = restColumnWeight,
                    enabled = false,
                    columnId = 2
                ),
                TableCellHeaderItem(
                    text = getString(R.string.load),
                    weight = restColumnWeight,
                    enabled = false,
                    columnId = 3
                )
            )
        }
    }
}