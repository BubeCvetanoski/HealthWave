package healthWave.launcher.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthwave.R
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
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
import healthWave.ui.theme.blue_color_level_1
import healthWave.ui.theme.blue_color_level_2
import healthWave.ui.theme.blue_color_level_6
import healthWave.ui.theme.dark_mode_background_color
import healthWave.ui.theme.dark_mode_items_color
import healthWave.ui.theme.light_mode_background_color
import healthWave.ui.theme.light_mode_items_color
import healthWave.ui.theme.pink_color_level_1
import healthWave.ui.theme.pink_color_level_2
import healthWave.ui.theme.pink_color_level_6
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

    private val _themeState = mutableStateOf("")
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
            is SharedSignUpEvent.ValidateSignUpSplashScreen -> {
                validateSplashScreen(
                    navigator = event.navigator
                )
            }

            is SharedSignUpEvent.ValidateSignUpFirstScreen -> {
                validateSignUpFirstScreen(
                    firstNameFocusRequester = event.firstNameFocusRequester,
                    lastNameFocusRequester = event.lastNameFocusRequester,
                    softwareKeyboardController = event.softwareKeyboardController,
                    navigator = event.navigator,
                    user = event.user
                )
            }

            is SharedSignUpEvent.ValidateSignUpSecondScreen -> {
                validateSignUpSecondScreen(
                    ageFocusRequester = event.ageFocusRequester,
                    heightFocusRequester = event.heightFocusRequester,
                    weightFocusRequester = event.weightFocusRequester,
                    navigator = event.navigator,
                    user = event.user
                )
            }

            is SharedSignUpEvent.ValidateSignUpThirdScreen -> {
                validateSignUpThirdScreen(
                    selectedIndexResults = event.selectedIndexResults,
                    resultsList = event.resultsList,
                    navigator = event.navigator,
                    user = event.user
                )
            }

            is SharedSignUpEvent.CalculateTheTDEE -> {
                calculateTheTDEE(
                    user = event.user
                )
            }
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

    fun updateUserFirstAndLastName(firstName: String, lastName: String) {
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

    fun getGoalCalories(): String {
        return _userState.value.calories
    }

    fun getHealthWaveColors(): Pair<Color, Color> {
        return when (_userState.value.gender) {
            "Male" -> Pair(blue_color_level_2, blue_color_level_6)
            "Female" -> Pair(pink_color_level_2, pink_color_level_6)
            else -> Pair(blue_color_level_2, blue_color_level_6)
        }
    }

    fun getHealthWaveFirstLevelColor(): Color {
        return when (_userState.value.gender) {
            "Male" -> blue_color_level_1
            "Female" -> pink_color_level_1
            else -> blue_color_level_1
        }
    }

    //Datastore functions
    private fun readApplicationTheme() {
        readApplicationThemeJob?.cancel()
        readApplicationThemeJob = dataStoreUseCases.readApplicationTheme()
            .onEach { theme ->
                _themeState.value = theme
            }
            .launchIn(viewModelScope)
    }

    fun saveTheme(themeValue: String) {
        viewModelScope.launch {
            dataStoreUseCases.saveApplicationTheme(themeValue)
            _themeState.value = themeValue
        }
    }

    fun getNewApplicationTheme(): String {
        return if (_themeState.value == "LIGHT MODE") "DARK MODE"
        else "LIGHT MODE"
    }

    fun getCurrentApplicationThemeColors(): Pair<Color, Color> {
        return when (_themeState.value) {
            "DARK MODE" -> Pair(dark_mode_background_color, dark_mode_items_color)
            "LIGHT MODE" -> Pair(light_mode_background_color, light_mode_items_color)
            else -> Pair(light_mode_background_color, light_mode_items_color)
        }
    }

    private fun readNotificationsChoice() {
        readNotificationsChoiceJob?.cancel()
        readNotificationsChoiceJob = dataStoreUseCases.readNotificationsChoice()
            .onEach { notificationsChoice ->
                _notificationsChoice.value = notificationsChoice
            }
            .launchIn(viewModelScope)
    }

    fun saveNotificationsChoice(notificationsChoice: String) {
        viewModelScope.launch {
            dataStoreUseCases.saveNotificationsChoice(notificationsChoice)
            _notificationsChoice.value = notificationsChoice
        }
    }

    fun getNewNotificationsChoice(): String {
        return if (_notificationsChoice.value == "OFF") "ON"
        else "OFF"
    }
}