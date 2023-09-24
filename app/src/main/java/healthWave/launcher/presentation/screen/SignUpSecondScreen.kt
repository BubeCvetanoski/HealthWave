package healthWave.launcher.presentation.screen

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import com.example.healthwave.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import healthWave.core.util.HelperFunctions.Companion.createListForSpinner
import healthWave.core.util.HelperFunctions.Companion.initializeOutlinedTextFieldColors
import healthWave.core.util.HelperFunctions.Companion.showToast
import healthWave.data.local.database.entity.User
import healthWave.destinations.SignUpFirstScreenDestination
import healthWave.destinations.SignUpSecondScreenDestination
import healthWave.destinations.SignUpThirdScreenDestination
import healthWave.ui.components.BlurredSignUpBackground
import healthWave.ui.components.BottomTextViewsSignUpScreen
import healthWave.ui.components.LargeDropdownSpinner
import healthWave.ui.theme.transparent_color

@Destination
@Composable
fun SignUpSecondScreen(
    navigator: DestinationsNavigator,
    user: User
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val context = LocalContext.current
        val spinnerColors = initializeOutlinedTextFieldColors()
        var selectedIndexGender by remember { mutableStateOf(-1) }
        var selectedIndexAge by remember { mutableStateOf(-1) }
        var selectedIndexWeight by remember { mutableStateOf(-1) }
        var selectedIndexHeight by remember { mutableStateOf(-1) }
        var selectedIndexActivity by remember { mutableStateOf(-1) }

        val ageList = createListForSpinner(10..69) { number ->
            if (number == 1) {
                "$number year old"
            } else {
                "$number years old"
            }
        }
        val heightList = createListForSpinner(100..250, "cm")
        val weightList = createListForSpinner(30..150, "kg")

        if (user.id != null) {
            BackHandler {}
        } else {
            BackHandler {
                user.firstName = ""
                user.lastName = ""
                navigator.navigate(SignUpFirstScreenDestination(user = user)) {
                    popUpTo(SignUpSecondScreenDestination.route) {
                        inclusive = true
                    }
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
                    label = stringResource(id = R.string.gender),
                    colors = spinnerColors,
                    items = listOf(
                        stringResource(id = R.string.male),
                        stringResource(id = R.string.female)
                    ),
                    selectedIndex = selectedIndexGender,
                    onItemSelected = { index, item ->
                        selectedIndexGender = index
                        user.gender = item
                    },
                    onValidate = {
                        validateSignUpSecondScreen(context, navigator, user)
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                LargeDropdownSpinner(
                    label = stringResource(id = R.string.age),
                    colors = spinnerColors,
                    items = ageList,
                    selectedIndex = selectedIndexAge,
                    onItemSelected = { index, item ->
                        selectedIndexAge = index
                        user.age = item
                    },
                    onValidate = {
                        validateSignUpSecondScreen(context, navigator, user)
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                LargeDropdownSpinner(
                    label = stringResource(id = R.string.height),
                    colors = spinnerColors,
                    items = heightList,
                    selectedIndex = selectedIndexHeight,
                    onItemSelected = { index, item ->
                        selectedIndexHeight = index
                        user.height = item
                    },
                    onValidate = {
                        validateSignUpSecondScreen(context, navigator, user)
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                LargeDropdownSpinner(
                    label = stringResource(id = R.string.weight),
                    colors = spinnerColors,
                    items = weightList,
                    selectedIndex = selectedIndexWeight,
                    onItemSelected = { index, item ->
                        selectedIndexWeight = index
                        user.weight = item
                    },
                    onValidate = {
                        validateSignUpSecondScreen(context, navigator, user)
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                LargeDropdownSpinner(
                    label = stringResource(id = R.string.weekly_activity),
                    colors = spinnerColors,
                    items = listOf(
                        stringResource(id = R.string.inactive),
                        stringResource(id = R.string.light_active),
                        stringResource(id = R.string.moderate_active),
                        stringResource(id = R.string.very_active),
                        stringResource(id = R.string.extra_active)
                    ),
                    selectedIndex = selectedIndexActivity,
                    onItemSelected = { index, item ->
                        selectedIndexActivity = index
                        user.activity = item
                    },
                    onValidate = {
                        validateSignUpSecondScreen(context, navigator, user)
                    }
                )
                BottomTextViewsSignUpScreen(secondText = stringResource(id = R.string.step_2))
            }
        }
    }
}

private fun validateSignUpSecondScreen(
    context: Context,
    navigation: DestinationsNavigator,
    user: User
) {
    return when {
        user.gender.isEmpty() -> showToast(
            context = context,
            context.getString(R.string.fill_in_gender)
        )

        user.age.isEmpty() -> showToast(
            context = context,
            context.getString(R.string.fill_in_age)
        )

        user.height.isEmpty() -> showToast(
            context = context,
            context.getString(R.string.fill_in_height)
        )

        user.weight.isEmpty() -> showToast(
            context = context,
            context.getString(R.string.fill_in_weight)
        )

        user.activity.isEmpty() -> showToast(
            context = context,
            context.getString(R.string.fill_in_activity)
        )

        else -> navigation.navigate(SignUpThirdScreenDestination(user = user)){
            popUpTo(SignUpSecondScreenDestination.route) {
                inclusive = true
            }
        }
    }
}