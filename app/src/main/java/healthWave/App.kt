package healthWave

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
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
import healthWave.launcher.presentation.viewmodel.SharedUserViewModel
import healthWave.ui.components.AnimatedBottomNavigationBar
import healthWave.ui.components.CustomNavigationDrawer
import healthWave.ui.components.CustomTopAppBar
import healthWave.ui.components.CustomYesNoDialog
import healthWave.ui.components.SampleScaffold
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(notificationService: MotivationalNotificationsService) {
    val engine = rememberNavHostEngine()
    val navController = engine.rememberNavController()
    val currentDestination = navController.appCurrentDestinationAsState().value

    val sharedUserViewModel: SharedUserViewModel = hiltViewModel()
    val user = sharedUserViewModel.userState.collectAsState()
    val scope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val shouldShowScaffoldBar = remember { mutableStateOf(false) }
    val shouldShowNotificationsDialog = remember { mutableStateOf(false) }
    val shouldShowThemeDialog = remember { mutableStateOf(false) }

    val baseColor = remember { mutableStateOf(Color.Unspecified) }
    val detailsColor = remember { mutableStateOf(Color.Unspecified) }
    val backgroundColor = remember { mutableStateOf(Color.Unspecified) }
    val itemsColor = remember { mutableStateOf(Color.Unspecified) }

    val newTheme = remember { mutableStateOf("") }
    val newNotificationsChoice = remember { mutableStateOf("") }

    newTheme.value = sharedUserViewModel.getNewApplicationTheme()
    newNotificationsChoice.value = sharedUserViewModel.getNewNotificationsChoice()

    baseColor.value = sharedUserViewModel.getHealthWaveColors().first
    detailsColor.value = sharedUserViewModel.getHealthWaveColors().second
    backgroundColor.value = sharedUserViewModel.getCurrentApplicationThemeColors().first
    itemsColor.value = sharedUserViewModel.getCurrentApplicationThemeColors().second

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
                    id = -1)
            }
        }

    val onMenuClicked = {
        scope.launch {
            drawerState.open()
        }
    }

    val onMyProfileClicked = {
        scope.launch {
            navController.navigate(MyProfileScreenDestination(id = fragmentDestinationIdentifier.id))
            drawerState.close()
        }
    }

    val onSetUpANewGoalClicked = {
        scope.launch {
            val newUser = user.let {
                User(
                    firstName = it.value.firstName,
                    lastName = it.value.lastName,
                    id = it.value.id
                )
            }
            navController.navigate(SignUpSecondScreenDestination(user = newUser))
            drawerState.close()
        }
    }

    val onAppearanceDialogConfirm = {
        scope.launch {
            sharedUserViewModel.saveTheme(themeValue = newTheme.value)
        }
    }

    val onNotificationDialogConfirm = {
        scope.launch {
            sharedUserViewModel.saveNotificationsChoice(notificationsChoice = newNotificationsChoice.value)

            if (newNotificationsChoice.value == "ON") {
                notificationService.scheduleMotivationalNotifications()
            } else {
                notificationService.cancelMotivationalNotifications()
            }
        }
    }

    val onNotificationsClicked = {
        scope.launch {
            shouldShowNotificationsDialog.value = true
            drawerState.close()
        }
    }

    val onApplicationsThemeClicked = {
        scope.launch {
            shouldShowThemeDialog.value = true
            drawerState.close()
        }
    }

    if (shouldShowScaffoldBar.value) {
        CustomNavigationDrawer(
            backgroundColor = itemsColor.value,
            drawerState = drawerState,
            content = {
                SampleScaffold(
                    topBar = {
                        CustomTopAppBar(
                            title = fragmentDestinationIdentifier.title,
                            backgroundColor = backgroundColor.value,
                            barColor = baseColor.value
                        ) { onMenuClicked() }
                    },
                    bottomBar = {
                        AnimatedBottomNavigationBar(
                            backgroundColor = backgroundColor.value,
                            barColor = baseColor.value,
                            ballColor = detailsColor.value,
                            destinationIndex = fragmentDestinationIdentifier.id,
                            navController = navController
                        )
                    }
                ) { paddingValues ->
                    DestinationsNavHost(
                        engine = engine,
                        navController = navController,
                        navGraph = NavGraphs.root,
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                    )
                }
            },
            onMyProfileClicked = onMyProfileClicked,
            onSetUpANewGoalClicked = onSetUpANewGoalClicked,
            onNotificationsClicked = onNotificationsClicked,
            onApplicationsThemeClicked = onApplicationsThemeClicked
        )
        if (shouldShowNotificationsDialog.value) {
            ShowNotificationsDialog(
                containerColor = baseColor.value,
                turnOnOrOff = newNotificationsChoice.value,
                onDismiss = { shouldShowNotificationsDialog.value = false },
                onConfirm = { onNotificationDialogConfirm() }
            )
        }
        if (shouldShowThemeDialog.value) {
            ShowAppearanceDialog(
                containerColor = baseColor.value,
                turnDarkOrLight = newTheme.value,
                onDismiss = { shouldShowThemeDialog.value = false },
                onConfirm = { onAppearanceDialogConfirm() }
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
    onConfirm: () -> Job) {
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
    onConfirm: () -> Job
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