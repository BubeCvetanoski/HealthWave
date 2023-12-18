package healthWave.myProfileScreen.event

import com.ramcosta.composedestinations.navigation.DestinationsNavigator

sealed class MyProfileEvent {
    data class OnBackClicked(val id: Int, val navigator: DestinationsNavigator): MyProfileEvent()
    data class ValidateFirstAndLastName(val firstName: String, val lastName: String): MyProfileEvent()
    data object OnEditNameIconClicked: MyProfileEvent()
    data object DismissEditNameDialog: MyProfileEvent()
}
