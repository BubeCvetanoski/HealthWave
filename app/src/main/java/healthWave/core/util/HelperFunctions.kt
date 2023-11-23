package healthWave.core.util

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import com.example.healthwave.R
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import healthWave.ui.components.TableCellHeaderItem
import healthWave.ui.theme.blue_color_level_2
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

        fun showToast(
            context: Context,
            message: String,
            duration: Int = Toast.LENGTH_SHORT
        ) {
            Toast.makeText(context, message, duration).show()
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
        fun initializeOutlinedTextFieldColors(): TextFieldColors {
            return TextFieldDefaults.outlinedTextFieldColors(
                textColor = white_color,
                cursorColor = gray_level_1,
                containerColor = transparent_color,
                focusedBorderColor = blue_color_level_2,
                unfocusedBorderColor = blue_color_level_2,
                focusedLabelColor = blue_color_level_2,
                unfocusedLabelColor = white_color
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
                                message = event.message.asString(context),
                                duration = 10
                            )
                        }
                    }
                }
            }
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