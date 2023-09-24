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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.healthwave.R
import healthWave.core.util.UiEvent
import healthWave.core.util.UiText
import healthWave.data.local.database.entity.User
import healthWave.launcher.presentation.viewmodel.UserViewModel
import healthWave.ui.theme.black_color
import healthWave.ui.theme.blue_color_level_6
import healthWave.ui.theme.gray_level_1
import healthWave.ui.theme.pink_color_level_6
import healthWave.ui.theme.transparent_color
import healthWave.ui.theme.white_color
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UpdateNameDialog(
    containerColor: Color,
    user: User,
    userViewModel: UserViewModel,
    onDismiss: () -> Unit
) {
    val keyBoardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val firstName = remember { mutableStateOf(user.firstName) }
    val lastName = remember { mutableStateOf(user.lastName) }
    val firstNameFocusRequester = remember { FocusRequester() }
    val lastNameFocusRequester = remember { FocusRequester() }
    val detailsColor = remember { mutableStateOf(blue_color_level_6) }

    val onSaveClicked = {
        if (onValidate(
                user.firstName,
                user.lastName
            )
        ) {
            scope.launch {
                userViewModel.updateUserFirstAndLastName(
                    user.firstName,
                    user.lastName
                )
            }
            onDismiss()
        }
    }

    val buttonsModifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)

    detailsColor.value = when (user.gender) {
        stringResource(id = R.string.male) -> blue_color_level_6
        stringResource(id = R.string.female) -> pink_color_level_6
        else -> blue_color_level_6
    }

    val colors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = white_color,
        cursorColor = gray_level_1,
        containerColor = transparent_color,
        focusedBorderColor = detailsColor.value,
        unfocusedBorderColor = detailsColor.value,
        focusedLabelColor = detailsColor.value,
        unfocusedLabelColor = white_color
    )

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
                    onClick = {
                        onSaveClicked()
                    }
                ) {
                    Text(text = stringResource(id = R.string.save))
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = containerColor,
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

private fun onValidate(firstName: String, lastName: String): Boolean {
    if (firstName == "" || firstName.length < 2) {
        UiEvent.ShowToast(
            UiText.StringResource(resId = R.string.fill_in_first_name)
        )
        return false
    }
    if (lastName == "" || lastName.length < 2) {
        UiEvent.ShowToast(
            UiText.StringResource(resId = R.string.fill_in_last_name)
        )
        return false
    }
    return true
}