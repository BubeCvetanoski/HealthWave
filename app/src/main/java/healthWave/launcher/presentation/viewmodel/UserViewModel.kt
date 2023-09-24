package healthWave.launcher.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import healthWave.data.local.database.entity.User
import healthWave.launcher.domain.useCase.database.UserUseCases
import healthWave.launcher.domain.useCase.datastore.DataStoreUseCases
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val dataStoreUseCases: DataStoreUseCases
) : ViewModel() {

    private val _userState = MutableStateFlow(User())
    val userState = _userState.asStateFlow()

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

    //Database functions
    private fun getUser() {
        getUserJob?.cancel()
        getUserJob = userUseCases.getUser()
            .onEach { user ->
                if (user != null && user.id != null) {
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
            }.launchIn(viewModelScope)
    }

    fun updateUser(updatedUser: User) {
        viewModelScope.launch {
            userUseCases.updateUser(updatedUser)
        }
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

    fun updateUserFirstAndLastName(firstName: String, lastName: String) {
        viewModelScope.launch {
            userUseCases.updateUserFirstAndLastName(firstName, lastName)
            _userState.value = userState.value.copy(
                firstName = firstName,
                lastName = lastName
            )
        }
    }

    fun addUser(user: User) {
        viewModelScope.launch {
            userUseCases.addUser(user)
        }
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