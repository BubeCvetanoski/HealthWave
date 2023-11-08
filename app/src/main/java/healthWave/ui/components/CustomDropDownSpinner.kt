package healthWave.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.healthwave.R
import healthWave.ui.theme.black_color
import healthWave.ui.theme.blue_color_level_2
import healthWave.ui.theme.gray_level_1
import healthWave.ui.theme.transparent_color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> LargeDropdownSpinner(
    modifier: Modifier = Modifier,
    label: String,
    colors: TextFieldColors,
    selectedIndex: Int = -1,
    enabled: Boolean = true,
    expanded: Boolean = false,
    notSetLabel: String? = null,
    items: List<T>,
    onItemSelected: (index: Int, item: T) -> Unit,
    selectedItemToString: (T) -> String = { it.toString() },
    onValidate: () -> Unit,
    drawItem: @Composable (T, Boolean, Boolean, () -> Unit) -> Unit = { item, selected, itemEnabled, onClick ->
        LargeDropdownSpinnerItem(
            text = item.toString(),
            selected = selected,
            enabled = itemEnabled,
            onClick = onClick,
        )
    }
) {
    var _expanded by remember { mutableStateOf(false) }

    if (expanded) {
        _expanded = true
    }

    val icon = if (_expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Box(modifier = modifier.height(IntrinsicSize.Min)) {
        OutlinedTextField(
            modifier = Modifier
                .width(215.dp)
                .wrapContentHeight(align = CenterVertically),
            value = items.getOrNull(selectedIndex)?.let { selectedItemToString(it) } ?: "",
            onValueChange = { },
            readOnly = true,
            enabled = enabled,
            textStyle = TextStyle(fontSize = 14.sp),
            label = { Text(text = label, fontSize = 10.sp) },
            singleLine = true,
            shape = RoundedCornerShape(25.dp),
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(id = R.string.drop_down),
                    tint = gray_level_1,
                    modifier = Modifier
                        .clickable { _expanded = !_expanded }
                )
            },
            colors = colors
        )
        // Transparent clickable surface on top of OutlinedTextField
        Surface(
            modifier = Modifier
                .width(215.dp)
                .fillMaxHeight()
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(25.dp))
                .clickable(enabled = enabled) { _expanded = true },
            color = transparent_color,
        ) {}
    }

    if (_expanded) {
        Dialog(
            onDismissRequest = { _expanded = false },
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
            ) {
                val listState = rememberLazyListState()
                if (selectedIndex > -1) {
                    LaunchedEffect("ScrollToSelected") {
                        listState.scrollToItem(index = selectedIndex)
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .width(215.dp)
                        .wrapContentHeight(align = CenterVertically),
                    state = listState
                ) {
                    if (notSetLabel != null) {
                        item {
                            LargeDropdownSpinnerItem(
                                text = notSetLabel,
                                selected = false,
                                enabled = false,
                                onClick = { },
                            )
                        }
                    }
                    itemsIndexed(items) { index, item ->
                        val selectedItem = index == selectedIndex
                        drawItem(
                            item,
                            selectedItem,
                            true
                        ) {
                            onItemSelected(index, item)
                            onValidate()
                            _expanded = false
                        }

                        if (index < items.lastIndex) {
                            Divider(modifier = Modifier.padding(horizontal = 16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LargeDropdownSpinnerItem(
    text: String,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val contentColor = when {
        !enabled -> black_color
        selected -> blue_color_level_2
        else -> black_color
    }

    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Box(modifier = Modifier
            .clickable(enabled) { onClick() }
            .fillMaxWidth()
            .padding(16.dp)) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
            )
        }
    }
}