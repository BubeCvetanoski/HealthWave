package healthWave

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthwave.R
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.rememberNavHostEngine
import healthWave.core.notifications.MotivationalNotificationsService
import healthWave.data.local.database.entity.User
import healthWave.destinations.CalorieTrackerScreenDestination
import healthWave.destinations.MyProfileScreenDestination
import healthWave.destinations.ProgramsScreenDestination
import healthWave.destinations.SignUpSecondScreenDestination
import healthWave.destinations.TrainingTrackerScreenDestination
import healthWave.launcher.presentation.event.SharedSignUpEvent
import healthWave.launcher.presentation.viewmodel.SharedUserViewModel
import healthWave.ui.components.AnimatedBottomNavigationBar
import healthWave.ui.components.CustomNavigationDrawer
import healthWave.ui.components.CustomTopAppBar
import healthWave.ui.components.CustomYesNoDialog
import healthWave.ui.components.SampleScaffold
import healthWave.ui.theme.HealthWaveColorScheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDestinations(
    notificationService: MotivationalNotificationsService,
    sharedUserViewModel: SharedUserViewModel = hiltViewModel()
) {
    val engine = rememberNavHostEngine()
    val navController = engine.rememberNavController()
    val currentDestination = navController.appCurrentDestinationAsState().value
    val scope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val user = sharedUserViewModel.userState.collectAsState().value
    val theme = sharedUserViewModel.applicationTheme.value
    val shouldShowScaffoldBar = remember { mutableStateOf(false) }
    val shouldShowNotificationsDialog = remember { mutableStateOf(false) }
    val shouldShowAppearanceDialog = remember { mutableStateOf(false) }

    val fragmentDestinationIdentifier: FragmentsDestinationIdentifier =
        when (currentDestination) {
            CalorieTrackerScreenDestination -> {
                shouldShowScaffoldBar.value = true
                FragmentsDestinationIdentifier(
                    title = stringResource(id = R.string.calorie_tracker),
                    id = 0
                )
            }

            TrainingTrackerScreenDestination -> {
                shouldShowScaffoldBar.value = true
                FragmentsDestinationIdentifier(
                    title = stringResource(id = R.string.training_tracker),
                    id = 1
                )
            }

            ProgramsScreenDestination -> {
                shouldShowScaffoldBar.value = true
                FragmentsDestinationIdentifier(
                    title = stringResource(id = R.string.recommended_programs),
                    id = 2
                )
            }

            else -> {
                shouldShowScaffoldBar.value = false
                FragmentsDestinationIdentifier(
                    title = stringResource(id = R.string.app_name),
                    id = -1
                )
            }
        }

    //it must be initialized here because it is the first time where the colors are accessed
    HealthWaveColorScheme.initialize(
        gender = user.gender,
        applicationTheme = theme
    )
    val healthWaveColors = HealthWaveColorScheme.instance

    if (shouldShowScaffoldBar.value) {
        CustomNavigationDrawer(
            backgroundColor = HealthWaveColorScheme.itemsColor,
            drawerState = drawerState,
            content = {
                SampleScaffold(
                    topBar = {
                        CustomTopAppBar(
                            title = fragmentDestinationIdentifier.title,
                            backgroundColor = HealthWaveColorScheme.backgroundColor,
                            barColor = HealthWaveColorScheme.baseElementsColor
                        ) {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    },
                    bottomBar = {
                        AnimatedBottomNavigationBar(
                            backgroundColor = HealthWaveColorScheme.backgroundColor,
                            barColor = HealthWaveColorScheme.baseElementsColor,
                            ballColor = HealthWaveColorScheme.detailsElementsColor,
                            destinationIndex = fragmentDestinationIdentifier.id,
                            navController = navController
                        )
                    }
                ) { paddingValues ->
                    CompositionLocalProvider(HealthWaveColorScheme.LocalAppTheme provides healthWaveColors) {
                        DestinationsNavHost(
                            engine = engine,
                            navController = navController,
                            navGraph = NavGraphs.root,
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize()
                        )
                    }
                }
            },
            onMyProfileClicked = {
                scope.launch {
                    navController.navigate(MyProfileScreenDestination(id = fragmentDestinationIdentifier.id))
                    drawerState.close()
                }
            },
            onSetUpANewGoalClicked = {
                scope.launch {
                    val newUser = User(
                        firstName = user.firstName,
                        lastName = user.lastName,
                        gender = user.gender,
                        id = user.id
                    )
                    navController.navigate(SignUpSecondScreenDestination(user = newUser))
                    drawerState.close()
                }
            },
            onNotificationsClicked = {
                scope.launch {
                    shouldShowNotificationsDialog.value = true
                    drawerState.close()
                }
            },
            onApplicationsThemeClicked = {
                scope.launch {
                    shouldShowAppearanceDialog.value = true
                    drawerState.close()
                }
            }
        )
        if (shouldShowNotificationsDialog.value) {
            ShowNotificationsDialog(
                containerColor = HealthWaveColorScheme.baseElementsColor,
                turnOnOrOff = sharedUserViewModel.onEvent(SharedSignUpEvent.GetNewNotificationsChoice) as String,
                onDismiss = { shouldShowNotificationsDialog.value = false },
                onConfirm = {
                    sharedUserViewModel.onEvent(
                        SharedSignUpEvent.ScheduleOrCancelNotifications(
                            notificationsChoice = sharedUserViewModel.onEvent(SharedSignUpEvent.GetNewNotificationsChoice) as String,
                            notificationService = notificationService
                        )
                    )
                }
            )
        }
        if (shouldShowAppearanceDialog.value) {
            ShowAppearanceDialog(
                containerColor = HealthWaveColorScheme.baseElementsColor,
                turnDarkOrLight = sharedUserViewModel.onEvent(SharedSignUpEvent.GetNewApplicationTheme) as String,
                onDismiss = { shouldShowAppearanceDialog.value = false },
                onConfirm = {
                    sharedUserViewModel.onEvent(
                        SharedSignUpEvent.ApplyTheme(
                            value = sharedUserViewModel.onEvent(SharedSignUpEvent.GetNewApplicationTheme) as String
                        )
                    )
                }
            )
        }
    } else {
        DestinationsNavHost(
            engine = engine,
            navController = navController,
            navGraph = NavGraphs.root,
            modifier = Modifier.fillMaxSize()
        )
    }
}

data class FragmentsDestinationIdentifier(
    val title: String,
    val id: Int
)

@Composable
fun ShowNotificationsDialog(
    containerColor: Color,
    turnOnOrOff: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    CustomYesNoDialog(
        title = stringResource(id = R.string.notifications),
        questionText = "Do you want to turn $turnOnOrOff the notifications?",
        containerColor = containerColor,
        onDismiss = onDismiss,
        onConfirm = {
            onConfirm()
            onDismiss()
        }
    )
}

@Composable
fun ShowAppearanceDialog(
    containerColor: Color,
    turnDarkOrLight: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    CustomYesNoDialog(
        title = stringResource(id = R.string.appearance),
        questionText = "Do you want to turn on the $turnDarkOrLight theme?",
        containerColor = containerColor,
        onDismiss = onDismiss,
        onConfirm = {
            onConfirm()
            onDismiss()
        }
    )
}