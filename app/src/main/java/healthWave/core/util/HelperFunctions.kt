package healthWave.core.util

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import healthWave.fragments.trainingTracker.presentation.screen.TableCellDataItem
import healthWave.ui.theme.blue_color_level_2
import healthWave.ui.theme.gray_level_1
import healthWave.ui.theme.transparent_color
import healthWave.ui.theme.white_color
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

        fun clearTableCellData(
            tableCellData: List<List<MutableState<TableCellDataItem>>>
        ) {
            tableCellData.forEach { row ->
                row.forEach { cellState ->
                    cellState.value = cellState.value.copy(
                        currentText = "",
                        previousText = "",
                        hasUnsavedChanges = false
                    )
                }
            }
        }

        // Function to find the index of the next empty cell in the table
        fun findNextEmptyCell(
            currentRowIndex: Int,
            currentColumnIndex: Int,
            rows: Int,
            tableCellData: List<List<MutableState<TableCellDataItem>>>
        ): Int {
            for (row in currentRowIndex until rows) {
                for (column in (if (row == currentRowIndex) currentColumnIndex + 1 else 0) until 4) {
                    if (tableCellData[row][column].value.currentText.isBlank()) {
                        return row * 4 + column
                    }
                }
            }
            return -1 // Return -1 if no empty cell is found
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
    }
}