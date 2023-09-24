package healthWave.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.healthwave.R
import healthWave.ui.theme.black_color

@Composable
fun CustomYesNoDialog(
    title: String,
    questionText: String,
    containerColor: Color,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val buttonsModifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)

    AlertDialog(
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Text(
                text = questionText,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )
        },
        containerColor = containerColor,
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = buttonsModifier
            ) {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = containerColor,
                        contentColor = black_color
                    ),
                    onClick = { onConfirm() }
                ) {
                    Text(text = stringResource(id = R.string.yes))
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = containerColor,
                        contentColor = black_color
                    ),
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text(text = stringResource(id = R.string.no))
                }
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    )
}