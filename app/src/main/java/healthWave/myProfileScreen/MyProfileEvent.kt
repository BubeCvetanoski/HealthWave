package healthWave.myProfileScreen

import com.ramcosta.composedestinations.navigation.DestinationsNavigator

sealed class MyProfileEvent {
    data class OnBack(val id: Int, val navigator: DestinationsNavigator): MyProfileEvent()
    data class OnValidate(val firstName: String, val lastName: String): MyProfileEvent()
    data object OnEditNameIconClicked: MyProfileEvent()
    data object OnDismissEditNameDialog: MyProfileEvent()
}
