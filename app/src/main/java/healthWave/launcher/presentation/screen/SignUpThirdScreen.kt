package healthWave.launcher.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthwave.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import healthWave.core.util.HelperFunctions
import healthWave.core.util.HelperFunctions.Companion.CollectUiEvents
import healthWave.core.util.HelperFunctions.Companion.navigateTo
import healthWave.data.local.database.entity.User
import healthWave.destinations.SignUpSecondScreenDestination
import healthWave.destinations.SignUpThirdScreenDestination
import healthWave.launcher.presentation.event.SharedSignUpEvent
import healthWave.launcher.presentation.viewmodel.SharedUserViewModel
import healthWave.ui.components.BlurredSignUpBackground
import healthWave.ui.components.BottomTextViewsSignUpScreen
import healthWave.ui.components.LargeDropdownSpinner
import healthWave.ui.theme.transparent_color

@Destination
@Composable
fun SignUpThirdScreen(
    navigator: DestinationsNavigator,
    user: User,
    sharedUserViewModel: SharedUserViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    var selectedIndexResults by remember { mutableStateOf(-1) }
    val spinnerColors = HelperFunctions.initializeOutlinedTextFieldColors()

    val totalDailyEnergyExpenditure = sharedUserViewModel.onEvent(
            SharedSignUpEvent.CalculateTheTDEE(user)
        ) as Int //calories for Maintaining weight
    val losingWeightCalories = totalDailyEnergyExpenditure - 600
    val gainingWeightCalories = totalDailyEnergyExpenditure + 600

    val resultsList = listOf(
        "$losingWeightCalories calories -> Losing weight",
        "$totalDailyEnergyExpenditure calories -> Maintaining weight",
        "$gainingWeightCalories calories -> Gaining weight"
    )

    CollectUiEvents(
        uiEvent = sharedUserViewModel.uiEvent,
        viewModel = sharedUserViewModel,
        context = context
    )

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        BackHandler {
            user.age = ""
            user.height = ""
            user.weight = ""
            user.activity = ""
            user.calories = ""
            user.goal = ""
            navigateTo(
                screen = SignUpSecondScreenDestination(user = user),
                popUpToRoute = SignUpThirdScreenDestination.route,
                navigator = navigator
            )
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
                LargeDropdownSpinner(
                    label = stringResource(id = R.string.results),
                    colors = spinnerColors,
                    items = resultsList,
                    selectedIndex = selectedIndexResults,
                    onItemSelected = { index, _ ->
                        selectedIndexResults = index
                    },
                    onValidate = {
                        sharedUserViewModel.onEvent(
                            SharedSignUpEvent.ValidateSignUpThirdScreen(
                                selectedIndexResults,
                                resultsList,
                                navigator,
                                user,
                            )
                        )
                    }
                )
                BottomTextViewsSignUpScreen(secondText = stringResource(id = R.string.final_step))
            }
        }
    }
}