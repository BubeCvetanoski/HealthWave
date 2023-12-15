package healthWave.launcher.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthwave.R
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import healthWave.core.notifications.MotivationalNotificationsService
import healthWave.core.util.Constants.DARK_MODE
import healthWave.core.util.Constants.LIGHT_MODE
import healthWave.core.util.HelperFunctions.Companion.navigateTo
import healthWave.core.util.UiEvent
import healthWave.core.util.UiText
import healthWave.data.local.database.entity.User
import healthWave.destinations.CalorieTrackerScreenDestination
import healthWave.destinations.SignUpFirstScreenDestination
import healthWave.destinations.SignUpSecondScreenDestination
import healthWave.destinations.SignUpThirdScreenDestination
import healthWave.destinations.SplashScreenDestination
import healthWave.launcher.domain.useCase.database.UserUseCases
import healthWave.launcher.domain.useCase.datastore.DataStoreUseCases
import healthWave.launcher.presentation.event.SharedSignUpEvent
import healthWave.ui.theme.HealthWaveColorScheme
import healthWave.ui.theme.blue_color_level_2
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedUserViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val dataStoreUseCases: DataStoreUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _userState = MutableStateFlow(User())
    val userState get() = _userState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _expandedGenderSpinner = mutableStateOf(false)
    val expandedGenderSpinner get() = _expandedGenderSpinner.value

    private val _expandedActivitySpinner = mutableStateOf(false)
    val expandedActivitySpinner get() = _expandedActivitySpinner.value

    private val _applicationTheme = mutableStateOf("")
    val applicationTheme get() = _applicationTheme
    private val _notificationsChoice = mutableStateOf("")

    private var getUserJob: Job? = null
    private var readApplicationThemeJob: Job? = null
    private var readNotificationsChoiceJob: Job? = null

    init {
        getUser()
        readApplicationTheme()
        readNotificationsChoice()
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun onEvent(event: SharedSignUpEvent): Any {
        return when (event) {
            is SharedSignUpEvent.ValidateSignUpSplashScreen -> validateSplashScreen(
                navigator = event.navigator
            )

            is SharedSignUpEvent.ValidateSignUpFirstScreen -> validateSignUpFirstScreen(
                firstNameFocusRequester = event.firstNameFocusRequester,
                lastNameFocusRequester = event.lastNameFocusRequester,
                softwareKeyboardController = event.softwareKeyboardController,
                navigator = event.navigator,
                user = event.user
            )

            is SharedSignUpEvent.ValidateSignUpSecondScreen -> validateSignUpSecondScreen(
                ageFocusRequester = event.ageFocusRequester,
                heightFocusRequester = event.heightFocusRequester,
                weightFocusRequester = event.weightFocusRequester,
                navigator = event.navigator,
                user = event.user
            )

            is SharedSignUpEvent.ValidateSignUpThirdScreen -> validateSignUpThirdScreen(
                selectedIndexResults = event.selectedIndexResults,
                resultsList = event.resultsList,
                navigator = event.navigator,
                user = event.user
            )

            is SharedSignUpEvent.CalculateTheTDEE -> calculateTheTDEE(
                user = event.user
            )

            is SharedSignUpEvent.UpdateUserFirstAndLastName -> updateUserFirstAndLastName(
                event.firstName,
                event.lastName
            )

            is SharedSignUpEvent.ApplyTheme -> applyTheme(
                event.value
            )
            is SharedSignUpEvent.ScheduleOrCancelNotifications -> scheduleOrCancelNotifications(
                event.notificationsChoice,
                event.notificationService
            )
            SharedSignUpEvent.GetGoalCalories -> getGoalCalories()
            SharedSignUpEvent.GetNewApplicationTheme -> getNewApplicationTheme()
            SharedSignUpEvent.GetNewNotificationsChoice -> getNewNotificationsChoice()

        }
    }

    //Database functions
    private fun getUser() {
        val savedStateValue = savedStateHandle.get<User>("UserState")

        if (savedStateValue != null) {
            _userState.value = savedStateValue
        } else {
            getUserJob?.cancel()
            getUserJob = userUseCases.getUser()
                .filterNotNull()
                .onEach { user ->
                    _userState.value = userState.value.copy(
                        firstName = user.firstName,
                        lastName = user.lastName,
                        gender = user.gender,
                        age = user.age,
                        height = user.height,
                        weight = user.weight,
                        activity = user.activity,
                        calories = user.calories,
                        goal = user.goal,
                        id = user.id
                    )
                }.launchIn(viewModelScope)
        }
    }

    private fun updateUser(updatedUser: User) {
        viewModelScope.launch {
            userUseCases.updateUser(updatedUser)
            _userState.value = userState.value.copy(
                gender = updatedUser.gender,
                age = updatedUser.age,
                height = updatedUser.height,
                weight = updatedUser.weight,
                activity = updatedUser.activity,
                calories = updatedUser.calories,
                goal = updatedUser.goal
            )
        }
    }

    private fun updateUserFirstAndLastName(firstName: String, lastName: String) {
        viewModelScope.launch {
            userUseCases.updateUserFirstAndLastName(firstName, lastName)
            _userState.value = userState.value.copy(
                firstName = firstName,
                lastName = lastName
            )
        }
    }

    private fun validateSignUpThirdScreen(
        selectedIndexResults: Int,
        resultsList: List<String>,
        navigator: DestinationsNavigator,
        user: User
    ) {
        if (selectedIndexResults <= -1) {
            viewModelScope.launch {
                _uiEvent.send(
                    UiEvent.ShowToast(
                        UiText.StringResource(resId = R.string.select_one_of_the_options)
                    )
                )
            }
            return
        }
        val selectedResult = resultsList[selectedIndexResults]
        val calories =
            selectedResult.substringBefore(" calories") //It takes the number before "calories"
        val goal = selectedResult.substringAfter(" -> ") //It takes the word after the "->"

        user.calories = calories
        user.goal = goal

        if (user.id != null) {
            updateUser(user)
        } else {
            addUser(user)
            HealthWaveColorScheme.initialize(
                gender = user.gender,
                applicationTheme = _applicationTheme.value
            )
        }
        navigateTo(
            screen = CalorieTrackerScreenDestination,
            popUpToRoute = SignUpThirdScreenDestination.route,
            navigator = navigator
        )
    }

    private fun validateSignUpSecondScreen(
        ageFocusRequester: FocusRequester,
        heightFocusRequester: FocusRequester,
        weightFocusRequester: FocusRequester,
        navigator: DestinationsNavigator,
        user: User
    ) {
        when {
            user.age.isEmpty() -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.ShowToast(
                            UiText.StringResource(resId = R.string.fill_in_age)
                        )
                    )
                }
                ageFocusRequester.requestFocus()
            }

            user.height.isEmpty() -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.ShowToast(
                            UiText.StringResource(resId = R.string.fill_in_height)
                        )
                    )
                }
                heightFocusRequester.requestFocus()
            }

            user.weight.isEmpty() -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.ShowToast(
                            UiText.StringResource(resId = R.string.fill_in_weight)
                        )
                    )
                }
                weightFocusRequester.requestFocus()
            }

            user.activity.isEmpty() -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.ShowToast(
                            UiText.StringResource(resId = R.string.fill_in_activity)
                        )
                    )
                }
                expandActivitySpinner()
            }

            else -> {
                navigateTo(
                    screen = SignUpThirdScreenDestination(user = user),
                    popUpToRoute = SignUpSecondScreenDestination.route,
                    navigator = navigator
                )
            }
        }
    }

    private fun expandGenderSpinner() {
        _expandedGenderSpinner.value = true
    }

    private fun expandActivitySpinner() {
        _expandedActivitySpinner.value = true
    }

    @OptIn(ExperimentalComposeUiApi::class)
    private fun validateSignUpFirstScreen(
        firstNameFocusRequester: FocusRequester,
        lastNameFocusRequester: FocusRequester,
        softwareKeyboardController: SoftwareKeyboardController?,
        navigator: DestinationsNavigator,
        user: User
    ) {
        when {

            user.firstName.isEmpty() || user.firstName.length < 2 -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.ShowToast(
                            UiText.StringResource(resId = R.string.fill_in_first_name)
                        )
                    )
                }
                firstNameFocusRequester.requestFocus()
            }

            user.lastName.isEmpty() || user.lastName.length < 2 -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.ShowToast(
                            UiText.StringResource(resId = R.string.fill_in_last_name)
                        )
                    )
                }
                lastNameFocusRequester.requestFocus()
            }

            user.gender.isEmpty() -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.ShowToast(
                            UiText.StringResource(resId = R.string.fill_in_gender)
                        )
                    )
                }
                expandGenderSpinner()
            }

            else -> {
                softwareKeyboardController?.hide()
                navigateTo(
                    screen = SignUpSecondScreenDestination(user = user),
                    popUpToRoute = SignUpFirstScreenDestination.route,
                    navigator = navigator
                )
            }
        }
    }

    private fun validateSplashScreen(navigator: DestinationsNavigator) {
        if (_userState.value.id != null) {
            navigateTo(
                screen = CalorieTrackerScreenDestination,
                popUpToRoute = SplashScreenDestination.route,
                navigator = navigator
            )
        } else {
            HealthWaveColorScheme.setDetailsElementsColor(
                color = blue_color_level_2
            )// se setira bojata vo sina vtoro nivo, bidejkji ako nema kreirano user znaci nema gender, znaci e prv vlez na aplikacijata i togash bojata na detalite
            // treba da bide sina, zaradi toa sto treba da se vklopuva so bojata na detalite pozadinata ( slikata Now or Never )
            navigateTo(
                screen = SignUpFirstScreenDestination(user = User()),
                popUpToRoute = SplashScreenDestination.route,
                navigator = navigator
            )
        }
    }

    private fun calculateTheTDEE(user: User): Int {
        //BMR for Male = 5 + (10 x weight in kg) + (6.25 x height in cm) - (5 x age in years)
        //BMR for Female (10 x weight in kg) + (6.25 x height in cm) - (5 x age in years)
        //TDEE for level 1 = BMR * 1.2, level 2 = BMR * 1.375, level 3 = BMR * 1.55, level 4 = BMR * 1725, level 5 = BMR 1.9
        val age = user.age.toInt()
        val height = user.height.toInt()
        val weight = user.weight.toInt()
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

    private fun addUser(user: User) {
        viewModelScope.launch {
            userUseCases.addUser(user)
            _userState.value = userState.value.copy(
                firstName = user.firstName,
                lastName = user.lastName,
                gender = user.gender,
                age = user.age,
                height = user.height,
                weight = user.weight,
                activity = user.activity,
                calories = user.calories,
                goal = user.goal,
                id = user.id
            )
        }
    }

    private fun getGoalCalories(): String {
        return _userState.value.calories
    }

    //Datastore functions
    private fun readApplicationTheme() {
        readApplicationThemeJob?.cancel()
        readApplicationThemeJob = dataStoreUseCases.readApplicationTheme()
            .onEach { theme ->
                _applicationTheme.value = theme
            }
            .launchIn(viewModelScope)
    }

    private fun applyTheme(themeValue: String) {
        viewModelScope.launch {
            dataStoreUseCases.saveApplicationTheme(themeValue)
            _applicationTheme.value = themeValue
        }
        setTheme(themeValue)
    }

    private fun setTheme(themeValue: String) {
        HealthWaveColorScheme.setBackgroundColor(themeValue)
        HealthWaveColorScheme.setItemsColor(themeValue)
    }

    private fun getNewApplicationTheme(): String {
        return if (_applicationTheme.value == LIGHT_MODE) DARK_MODE
        else LIGHT_MODE
    }

    private fun readNotificationsChoice() {
        readNotificationsChoiceJob?.cancel()
        readNotificationsChoiceJob = dataStoreUseCases.readNotificationsChoice()
            .onEach { notificationsChoice ->
                _notificationsChoice.value = notificationsChoice
            }
            .launchIn(viewModelScope)
    }

    private fun saveNotificationsChoice(notificationsChoice: String) {
        viewModelScope.launch {
            dataStoreUseCases.saveNotificationsChoice(notificationsChoice)
            _notificationsChoice.value = notificationsChoice
        }
    }

    private fun getNewNotificationsChoice(): String {
        return if (_notificationsChoice.value == "OFF") "ON"
        else "OFF"
    }

    private fun scheduleOrCancelNotifications(
        notificationsChoice: String,
        notificationService: MotivationalNotificationsService
    ) {
        saveNotificationsChoice(notificationsChoice = notificationsChoice)

        // Schedule or cancel notifications based on the user's choice
        if (notificationsChoice == "ON") {
            notificationService.scheduleMotivationalNotifications()
        } else {
            notificationService.cancelMotivationalNotifications()
        }
    }
}