package healthWave.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.healthwave.R
import healthWave.core.util.HelperFunctions.Companion.initializeOutlinedTextFieldColors
import healthWave.core.util.UiEvent
import healthWave.core.util.UiText
import healthWave.data.local.database.entity.User
import healthWave.ui.theme.HealthWaveColorScheme
import healthWave.ui.theme.black_color

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UpdateNameDialog(
    user: User,
    onSaveClicked: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    val keyBoardController = LocalSoftwareKeyboardController.current
    val firstName = remember { mutableStateOf(user.firstName) }
    val lastName = remember { mutableStateOf(user.lastName) }
    val firstNameFocusRequester = remember { FocusRequester() }
    val lastNameFocusRequester = remember { FocusRequester() }

    val buttonsModifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)

    val colors = initializeOutlinedTextFieldColors()

    AlertDialog(
        title = {
            Text(
                text = stringResource(id = R.string.enter_details),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomEditTextField(
                    value = firstName.value,
                    newValue = {
                        firstName.value = it
                        user.firstName = it
                    },
                    label = stringResource(id = R.string.first_name),
                    focusRequester = firstNameFocusRequester,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    colors = colors
                ) {
                    addFocusOnNext(
                        firstNameFocusRequester,
                        lastNameFocusRequester,
                        keyBoardController,
                        user
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                CustomEditTextField(
                    value = lastName.value,
                    newValue = {
                        lastName.value = it
                        user.lastName = it
                    },
                    label = stringResource(id = R.string.last_name),
                    focusRequester = lastNameFocusRequester,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    colors = colors
                ) {
                    addFocusOnNext(
                        firstNameFocusRequester,
                        lastNameFocusRequester,
                        keyBoardController,
                        user
                    )
                }
            }
        },
        containerColor = HealthWaveColorScheme.baseElementsColor,
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = buttonsModifier
            ) {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = HealthWaveColorScheme.baseElementsColor,
                        contentColor = black_color
                    ),
                    onClick = {
                        onSaveClicked(firstName.value, lastName.value)
                    }
                ) {
                    Text(text = stringResource(id = R.string.save))
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = HealthWaveColorScheme.baseElementsColor,
                        contentColor = black_color
                    ),
                    onClick = { onDismiss() }
                ) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    )
}

@OptIn(ExperimentalComposeUiApi::class)
private fun addFocusOnNext(
    firstNameFocusRequester: FocusRequester,
    lastNameFocusRequester: FocusRequester,
    softwareKeyboardController: SoftwareKeyboardController?,
    user: User
) {
    if (user.firstName == "") {
        firstNameFocusRequester.requestFocus()
    }
    if (user.lastName == "") {
        lastNameFocusRequester.requestFocus()
    }
    UiEvent.ShowToast(
        UiText.StringResource(resId = R.string.in_order_to_proceed_click)
    )
    softwareKeyboardController?.hide()
}