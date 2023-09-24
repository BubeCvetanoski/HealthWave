package healthWave.launcher.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import com.example.healthwave.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import healthWave.core.util.HelperFunctions.Companion.initializeOutlinedTextFieldColors
import healthWave.core.util.UiEvent
import healthWave.core.util.UiText
import healthWave.data.local.database.entity.User
import healthWave.destinations.SignUpFirstScreenDestination
import healthWave.destinations.SignUpSecondScreenDestination
import healthWave.ui.components.BlurredSignUpBackground
import healthWave.ui.components.BottomTextViewsSignUpScreen
import healthWave.ui.components.CustomEditTextField
import healthWave.ui.theme.transparent_color


@OptIn(ExperimentalComposeUiApi::class)
@Destination
@Composable
fun SignUpFirstScreen(
    navigator: DestinationsNavigator,
    user: User
) {
    val keyBoardController = LocalSoftwareKeyboardController.current

    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val firstNameFocusRequester = remember { FocusRequester() }
    val lastNameFocusRequester = remember { FocusRequester() }

    val colors = initializeOutlinedTextFieldColors()
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        BlurredSignUpBackground()
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .background(transparent_color),
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
                    validateSignUpFirstScreen(
                        firstNameFocusRequester,
                        lastNameFocusRequester,
                        keyBoardController,
                        navigator,
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
                    validateSignUpFirstScreen(
                        firstNameFocusRequester,
                        lastNameFocusRequester,
                        keyBoardController,
                        navigator,
                        user
                    )
                }
                BottomTextViewsSignUpScreen(secondText = stringResource(id = R.string.step_1))
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
private fun validateSignUpFirstScreen(
    firstNameFocusRequester: FocusRequester,
    lastNameFocusRequester: FocusRequester,
    softwareKeyboardController: SoftwareKeyboardController?,
    navigator: DestinationsNavigator,
    user: User
) {
    if (user.firstName == "" || user.firstName.length < 2) {
        UiEvent.ShowToast(
            UiText.StringResource(resId = R.string.fill_in_first_name)
        )
        firstNameFocusRequester.requestFocus()
        return
    }
    if (user.lastName == "" || user.lastName.length < 2) {
        UiEvent.ShowToast(
            UiText.StringResource(resId = R.string.fill_in_last_name)
        )
        lastNameFocusRequester.requestFocus()
        return
    }
    softwareKeyboardController?.hide()
    navigator.navigate(SignUpSecondScreenDestination(user = user)) {
        popUpTo(SignUpFirstScreenDestination.route) {
            inclusive = true
        }
    }
}