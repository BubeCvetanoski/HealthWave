package healthWave.launcher.presentation.screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import healthWave.data.local.database.entity.User
import healthWave.destinations.CalorieTrackerScreenDestination
import healthWave.destinations.SignUpFirstScreenDestination
import healthWave.destinations.SplashScreenDestination
import healthWave.launcher.presentation.viewmodel.UserViewModel
import healthWave.ui.components.AnimatedLogo
import healthWave.ui.theme.black_color
import healthWave.ui.theme.blue_color_level_9
import healthWave.ui.theme.white_color
import kotlinx.coroutines.delay

@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val user = userViewModel.userState.collectAsState()
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1.3f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(2000L)

        chooseWhichScreenToNavigateTo(navigator, user.value)
    }
    Surface(color = blue_color_level_9, modifier = Modifier.fillMaxSize()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedLogo(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .scale(scale.value),
                logoNameFontSize = 30.sp,
                sloganNameFontSize = 8.sp,
                initialSloganColor = white_color,
                finalSloganColor = black_color
            )
        }
    }
}

private fun chooseWhichScreenToNavigateTo(navigator: DestinationsNavigator, user: User) {
    if (user.id != null) {
        navigator.navigate(CalorieTrackerScreenDestination) {
            popUpTo(SplashScreenDestination.route) {
                inclusive = true
            }
        }
    } else {
        navigator.navigate(SignUpFirstScreenDestination(user = user)) {
            popUpTo(SplashScreenDestination.route) {
                inclusive = true
            }
        }
    }
}