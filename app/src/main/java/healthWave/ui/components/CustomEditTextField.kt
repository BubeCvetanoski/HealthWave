package healthWave.ui.components

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomEditTextField(
    value: String,
    newValue: (value: String) -> Unit,
    label: String,
    focusRequester: FocusRequester,
    keyboardOptions: KeyboardOptions,
    colors: TextFieldColors,
    onNext: (() -> Unit)?
) {
    OutlinedTextField(
        modifier = Modifier
            .width(215.dp)
            .wrapContentHeight(align = CenterVertically)
            .imePadding()
            .focusRequester(focusRequester),
        value = value,
        onValueChange = newValue,
        textStyle = TextStyle(fontSize = 14.sp),
        label = { Text(text = label, fontSize = 10.sp) },
        singleLine = true,
        shape = RoundedCornerShape(25.dp),
        colors = colors,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onNext = {
                if (onNext != null) {
                    onNext()
                }
            }
        )
    )
}