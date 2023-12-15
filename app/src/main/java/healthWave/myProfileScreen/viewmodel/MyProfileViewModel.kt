package healthWave.myProfileScreen.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.healthwave.R
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import healthWave.core.util.HelperFunctions
import healthWave.core.util.UiEvent
import healthWave.core.util.UiText
import healthWave.destinations.CalorieTrackerScreenDestination
import healthWave.destinations.MyProfileScreenDestination
import healthWave.destinations.ProgramsScreenDestination
import healthWave.destinations.TrainingTrackerScreenDestination
import healthWave.myProfileScreen.event.MyProfileEvent
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor() : ViewModel() {

    private val _showUpdateNameDialog = mutableStateOf(false)
    val showUpdateNameDialog: State<Boolean> = _showUpdateNameDialog

    fun onEvent(event: MyProfileEvent): Any {
        return when(event) {
            is MyProfileEvent.BackClicked -> onBack(event.id, event.navigator)
            is MyProfileEvent.DismissEditNameDialog -> onDismissEditNameDialog()
            is MyProfileEvent.EditNameIconClicked -> onEditNameIconClicked()
            is MyProfileEvent.ValidateFirstAndLastName -> onValidate(event.firstName, event.lastName)
        }
    }

    private val onBack:(id: Int, navigator: DestinationsNavigator) -> Unit = { id, navigator ->
        when (id) {
            0 -> {
                HelperFunctions.navigateTo(
                    screen = CalorieTrackerScreenDestination,
                    popUpToRoute = MyProfileScreenDestination.route,
                    navigator = navigator
                )
            }

            1 -> {
                HelperFunctions.navigateTo(
                    screen = TrainingTrackerScreenDestination,
                    popUpToRoute = MyProfileScreenDestination.route,
                    navigator = navigator
                )
            }

            2 -> {
                HelperFunctions.navigateTo(
                    screen = ProgramsScreenDestination,
                    popUpToRoute = MyProfileScreenDestination.route,
                    navigator = navigator
                )
            }
        }
    }

    private fun onEditNameIconClicked() {
        _showUpdateNameDialog.value = true
    }

    private fun onDismissEditNameDialog() {
        _showUpdateNameDialog.value = false
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
}