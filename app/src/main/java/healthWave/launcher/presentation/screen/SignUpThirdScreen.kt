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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthwave.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import healthWave.core.util.HelperFunctions
import healthWave.core.util.UiEvent
import healthWave.core.util.UiText
import healthWave.data.local.database.entity.User
import healthWave.destinations.CalorieTrackerScreenDestination
import healthWave.destinations.SignUpSecondScreenDestination
import healthWave.destinations.SignUpThirdScreenDestination
import healthWave.launcher.presentation.viewmodel.UserViewModel
import healthWave.ui.components.BlurredSignUpBackground
import healthWave.ui.components.BottomTextViewsSignUpScreen
import healthWave.ui.components.LargeDropdownSpinner
import healthWave.ui.theme.transparent_color

@Destination
@Composable
fun SignUpThirdScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    user: User
) {
    var selectedIndexResults by remember { mutableStateOf(-1) }

    val spinnerColors = HelperFunctions.initializeOutlinedTextFieldColors()

    val totalDailyEnergyExpenditure =
        calculateTheTDEE(user = user) //calories for Maintaining weight
    val losingWeightCalories = totalDailyEnergyExpenditure - 600
    val gainingWeightCalories = totalDailyEnergyExpenditure + 600

    val resultsList = listOf(
        "$losingWeightCalories calories -> Losing weight",
        "$totalDailyEnergyExpenditure calories -> Maintaining weight",
        "$gainingWeightCalories calories -> Gaining weight"
    )

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        BackHandler {
            user.gender = ""
            user.age = ""
            user.height = ""
            user.weight = ""
            user.activity = ""
            user.calories = ""
            user.goal = ""
            navigator.navigate(SignUpSecondScreenDestination(user = user)) {
                popUpTo(SignUpThirdScreenDestination.route) {
                    inclusive = true
                }
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
                LargeDropdownSpinner(
                    label = stringResource(id = R.string.results),
                    colors = spinnerColors,
                    items = resultsList,
                    selectedIndex = selectedIndexResults,
                    onItemSelected = { index, _ ->
                        selectedIndexResults = index
                    },
                    onValidate = {
                        validateSignUpThirdScreen(
                            userViewModel,
                            navigator,
                            selectedIndexResults,
                            resultsList,
                            user
                        )
                    }
                )
                BottomTextViewsSignUpScreen(secondText = stringResource(id = R.string.final_step))
            }
        }
    }
}

private fun validateSignUpThirdScreen(
    userViewModel: UserViewModel,
    navigator: DestinationsNavigator,
    selectedIndexResults: Int,
    resultsList: List<String>,
    user: User
) {
    if (selectedIndexResults <= -1) {
        UiEvent.ShowToast(
            UiText.StringResource(resId = R.string.select_one_of_the_options)
        )
        return
    }
    val selectedResult = resultsList[selectedIndexResults]
    val calories =
        selectedResult.substringBefore(" calories") //It takes the number before "calories"
    val goal = selectedResult.substringAfter(" -> ") //It takes the word after the "->"

    user.calories = calories
    user.goal = goal

    if (user.id != null) {
        userViewModel.updateUser(user)
    } else {
        userViewModel.addUser(user)
    }
    navigator.navigate(CalorieTrackerScreenDestination) {
        popUpTo(SignUpThirdScreenDestination.route) {
            inclusive = true
        }
    }
}

private fun calculateTheTDEE(user: User): Int {
    //BMR for Male = 5 + (10 x weight in kg) + (6.25 x height in cm) - (5 x age in years)
    //BMR for Female (10 x weight in kg) + (6.25 x height in cm) - (5 x age in years)
    //TDEE for level 1 = BMR * 1.2, level 2 = BMR * 1.375, level 3 = BMR * 1.55, level 4 = BMR * 1725, level 5 = BMR 1.9
    val age = user.age.split(" ")[0].toInt()
    val height = user.height.split(" ")[0].toInt()
    val weight = user.weight.split(" ")[0].toInt()
    val activity = user.activity.split(" ")[0]

    val basalMetabolicRate: Double = if (user.gender == "Male") {
        (5 + (10 * weight) + (6.25 * height) - (5 * age))
    } else {
        ((10 * weight) + (6.25 * height) - (5 * age) - 161)
    }

    return when (activity) {
        "Inactive" -> (basalMetabolicRate * 1.2).toInt()
        "Light" -> (basalMetabolicRate * 1.375).toInt()
        "Moderate" -> (basalMetabolicRate * 1.55).toInt()
        "Very" -> (basalMetabolicRate * 1.725).toInt()
        "Extra" -> (basalMetabolicRate * 1.9).toInt()
        else -> 0
    }
}