package healthWave.launcher.presentation.screen

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthwave.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import healthWave.core.util.HelperFunctions.Companion.CollectUiEvents
import healthWave.core.util.HelperFunctions.Companion.initializeOutlinedTextFieldColors
import healthWave.core.util.HelperFunctions.Companion.navigateTo
import healthWave.data.local.database.entity.User
import healthWave.destinations.SignUpFirstScreenDestination
import healthWave.destinations.SignUpSecondScreenDestination
import healthWave.launcher.presentation.event.SharedSignUpEvent
import healthWave.launcher.presentation.viewmodel.SharedUserViewModel
import healthWave.ui.components.BlurredSignUpBackground
import healthWave.ui.components.BottomTextViewsSignUpScreen
import healthWave.ui.components.CustomEditTextField
import healthWave.ui.components.LargeDropdownSpinner
import healthWave.ui.theme.transparent_color


@Destination
@Composable
fun SignUpSecondScreen(
    navigator: DestinationsNavigator,
    user: User,
    sharedUserViewModel: SharedUserViewModel = hiltViewModel()
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val context = LocalContext.current

        val age = remember { mutableStateOf("") }
        val height = remember { mutableStateOf("") }
        val weight = remember { mutableStateOf("") }
        val ageFocusRequester = remember { FocusRequester() }
        val heightFocusRequester = remember { FocusRequester() }
        val weightFocusRequester = remember { FocusRequester() }

        val activityList = listOf(
            stringResource(id = R.string.inactive),
            stringResource(id = R.string.light_active),
            stringResource(id = R.string.moderate_active),
            stringResource(id = R.string.very_active),
            stringResource(id = R.string.extra_active)
        )
        var selectedIndexActivity by remember { mutableStateOf(-1) }
        val outlinedTextFieldColors = initializeOutlinedTextFieldColors()

        CollectUiEvents(
            uiEvent = sharedUserViewModel.uiEvent,
            viewModel = sharedUserViewModel,
            context = context
        )

        if (user.id != null) {
            BackHandler {}
        } else {
            BackHandler {
                user.firstName = ""
                user.lastName = ""
                user.gender = ""
                navigateTo(
                    screen = SignUpFirstScreenDestination(user = user),
                    popUpToRoute = SignUpSecondScreenDestination.route,
                    navigator = navigator
                )
            }
        }
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
                    value = age.value,
                    newValue = {
                        age.value = it
                        user.age = it
                    },
                    label = stringResource(id = R.string.age),
                    onlyIntegerValues = true,
                    focusRequester = ageFocusRequester,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    colors = outlinedTextFieldColors
                ) {
                    sharedUserViewModel.onEvent(
                        SharedSignUpEvent.ValidateSignUpSecondScreen(
                            ageFocusRequester,
                            heightFocusRequester,
                            weightFocusRequester,
                            navigator,
                            user
                        )
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                CustomEditTextField(
                    value = height.value,
                    newValue = {
                        height.value = it
                        user.height = it
                    },
                    label = stringResource(id = R.string.height),
                    onlyIntegerValues = true,
                    focusRequester = heightFocusRequester,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    colors = outlinedTextFieldColors
                ) {
                    sharedUserViewModel.onEvent(
                        SharedSignUpEvent.ValidateSignUpSecondScreen(
                            ageFocusRequester,
                            heightFocusRequester,
                            weightFocusRequester,
                            navigator,
                            user
                        )
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                CustomEditTextField(
                    value = weight.value,
                    newValue = {
                        weight.value = it
                        user.weight = it
                    },
                    label = stringResource(id = R.string.weight),
                    onlyIntegerValues = true,
                    focusRequester = weightFocusRequester,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    colors = outlinedTextFieldColors
                ) {
                    sharedUserViewModel.onEvent(
                        SharedSignUpEvent.ValidateSignUpSecondScreen(
                            ageFocusRequester,
                            heightFocusRequester,
                            weightFocusRequester,
                            navigator,
                            user
                        )
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                LargeDropdownSpinner(
                    label = stringResource(id = R.string.weekly_activity),
                    colors = outlinedTextFieldColors,
                    items = activityList,
                    selectedIndex = selectedIndexActivity,
                    expanded = sharedUserViewModel.expandedActivitySpinner,
                    onItemSelected = { index, item ->
                        selectedIndexActivity = index
                        user.activity = item
                    },
                    onValidate = {
                        sharedUserViewModel.onEvent(
                            SharedSignUpEvent.ValidateSignUpSecondScreen(
                                ageFocusRequester,
                                heightFocusRequester,
                                weightFocusRequester,
                                navigator,
                                user
                            )
                        )
                    }
                )
                BottomTextViewsSignUpScreen(secondText = stringResource(id = R.string.step_2))
            }
        }
    }
}