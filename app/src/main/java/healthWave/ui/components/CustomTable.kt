package healthWave.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.healthwave.R
import healthWave.core.util.HelperFunctions.Companion.CollectUiEvents
import healthWave.core.util.HelperFunctions.Companion.initializeTableCellHeaderItems
import healthWave.fragments.trainingTracker.presentation.event.TrainingTrackerEvent
import healthWave.fragments.trainingTracker.presentation.screen.TableCellDataItem
import healthWave.fragments.trainingTracker.presentation.viewmodel.ExerciseViewModel
import healthWave.ui.theme.HealthWaveColorScheme
import healthWave.ui.theme.black_color
import healthWave.ui.theme.transparent_color
import healthWave.ui.theme.white_color

@Composable
fun CustomTable(
    date: String,
    rows: Int,
    tableCellData: List<List<MutableState<TableCellDataItem>>>,
    exerciseViewModel: ExerciseViewModel
) {
    val context = LocalContext.current
    val tableCellHeaderItems = context.initializeTableCellHeaderItems()
    val focusRequesters = List(rows * 4) { remember { FocusRequester() } }

    val nameColumnWeight = .55f
    val restColumnWeight = .15f

    CollectUiEvents(
        uiEvent = exerciseViewModel.uiEvent,
        viewModel = exerciseViewModel,
        context = context
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            Modifier.fillMaxSize()
        ) {
            // Here is the header of the table
            item {
                Row(Modifier.background(HealthWaveColorScheme.itemsColor)) {
                    tableCellHeaderItems.forEach { headerItem ->
                        TableCell(
                            text = headerItem.text,
                            weight = headerItem.weight,
                            enabled = headerItem.enabled
                        )
                    }
                }
            }
            // Here are all the rows of the table.
            itemsIndexed(tableCellData) { rowIndex, rowData ->
                Row(Modifier.fillMaxWidth()) {
                    rowData.forEachIndexed { columnIndex, cellState ->

                        val focusRequester = focusRequesters[rowIndex * 4 + columnIndex]
                        TableCell(
                            text = cellState.value.currentText,
                            weight = if (columnIndex == 0) nameColumnWeight else restColumnWeight,
                            enabled = true,
                            modifier = Modifier
                                .background(
                                    if (cellState.value.hasUnsavedChanges)
                                        Color.Yellow
                                    else transparent_color
                                ),
                            focusRequester = focusRequester,
                            keyboardType = if (columnIndex == 0) KeyboardType.Text else KeyboardType.Number,
                            onNext = {
                                exerciseViewModel.onEvent(
                                    TrainingTrackerEvent.OnCustomTableNextClicked(
                                        rowIndex,
                                        columnIndex,
                                        rows,
                                        focusRequester,
                                        focusRequesters
                                    )
                                )
                            },
                            onTextChanged = { newText ->
                                exerciseViewModel.onEvent(
                                    TrainingTrackerEvent.OnCustomTableTextChanged(
                                        cellState,
                                        newText,
                                        rowIndex,
                                        columnIndex
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            FloatingActionButton(
                containerColor = black_color,
                elevation = FloatingActionButtonDefaults.elevation(4.dp),
                onClick = {
                    exerciseViewModel.onEvent(
                        TrainingTrackerEvent.ClearTableCellData
                    )
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = stringResource(id = R.string.clear),
                    tint = white_color
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            FloatingActionButton(
                containerColor = white_color,
                elevation = FloatingActionButtonDefaults.elevation(4.dp),
                onClick = {
                    exerciseViewModel.onEvent(
                        TrainingTrackerEvent.OnApplyClicked(date)
                    )
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = stringResource(id = R.string.apply),
                    tint = black_color
                )
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = FocusRequester.Cancel,
    keyboardType: KeyboardType = KeyboardType.Text,
    onNext: (() -> Unit)? = null,
    onTextChanged: (String) -> Unit = { }
) {
    BasicTextField(
        modifier = modifier
            .border(1.dp, Color.LightGray)
            .weight(weight)
            .padding(8.dp)
            .focusRequester(focusRequester),
        value = text,
        maxLines = 3,
        minLines = 3,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                if (onNext != null) {
                    onNext()
                }
            }
        ),
        onValueChange = onTextChanged
    )
}

data class TableCellHeaderItem(
    val text: String,
    val weight: Float,
    val enabled: Boolean,
    val columnId: Int,
    val onTextChanged: (String) -> Unit = { }
)