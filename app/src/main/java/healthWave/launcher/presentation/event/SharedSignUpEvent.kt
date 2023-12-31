package healthWave.launcher.presentation.event

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import healthWave.core.notifications.MotivationalNotificationsService
import healthWave.data.local.database.entity.User

sealed class SharedSignUpEvent {
    data class ValidateSignUpSplashScreen(val navigator: DestinationsNavigator) :
        SharedSignUpEvent()

    data class ValidateSignUpFirstScreen @OptIn(ExperimentalComposeUiApi::class)
    constructor(
        val firstNameFocusRequester: FocusRequester,
        val lastNameFocusRequester: FocusRequester,
        val softwareKeyboardController: SoftwareKeyboardController?,
        val navigator: DestinationsNavigator,
        val user: User
    ) : SharedSignUpEvent()

    data class ValidateSignUpSecondScreen(
        val ageFocusRequester: FocusRequester,
        val heightFocusRequester: FocusRequester,
        val weightFocusRequester: FocusRequester,
        val navigator: DestinationsNavigator,
        val user: User
    ) : SharedSignUpEvent()

    data class ValidateSignUpThirdScreen(
        val selectedIndexResults: Int,
        val resultsList: List<String>,
        val navigator: DestinationsNavigator,
        val user: User
    ) : SharedSignUpEvent()

    data class CalculateTheTDEE(val user: User) : SharedSignUpEvent()

    data class UpdateUserFirstAndLastName(
        val firstName: String,
        val lastName: String
    ) : SharedSignUpEvent()

    data class ApplyTheme(val value: String) : SharedSignUpEvent()

    data class ScheduleOrCancelNotifications(
        val notificationsChoice: String,
        val notificationService: MotivationalNotificationsService
    ) : SharedSignUpEvent()

    data object GetGoalCalories : SharedSignUpEvent()
    data object GetNewApplicationTheme : SharedSignUpEvent()
    data object GetNewNotificationsChoice : SharedSignUpEvent()

}
