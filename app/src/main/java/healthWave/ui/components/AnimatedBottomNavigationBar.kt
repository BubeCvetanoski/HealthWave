package healthWave.ui.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.FoodBank
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.healthwave.R
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.ShapeCornerRadius
import com.exyte.animatednavbar.utils.toPxf
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.utils.isRouteOnBackStack
import healthWave.NavGraphs
import healthWave.destinations.CalorieTrackerScreenDestination
import healthWave.destinations.DirectionDestination
import healthWave.destinations.ProgramsScreenDestination
import healthWave.destinations.TrainingTrackerScreenDestination
import healthWave.ui.theme.white_color

@Composable
fun AnimatedBottomNavigationBar(
    backgroundColor: Color,
    barColor: Color,
    ballColor: Color,
    destinationIndex: Int = 0,
    navController: NavHostController
) {
    val customCornerRadius = shapeCornerRadius(
        topLeft = 50.dp,
        topRight = 50.dp
    )
    var selectedIndex by remember {
        mutableStateOf(destinationIndex)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        AnimatedNavigationBar(
            modifier = Modifier.height(64.dp),
            cornerRadius = customCornerRadius,
            ballAnimation = Parabolic(tween(300)),
            indentAnimation = Height(tween(300)),
            barColor = barColor,
            ballColor = ballColor,
            selectedIndex = selectedIndex
        ) {
            BottomNavigationBarItem.values().forEach { item ->
                val isCurrentDestOnBackStack = navController.isRouteOnBackStack(item.direction)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .noRippleClickable(
                            enabled = !isCurrentDestOnBackStack
                        ) {
                            selectedIndex = item.ordinal
                            if (!isCurrentDestOnBackStack) {
                                navController.navigate(item.direction) {
                                    // Pop up to the root of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(NavGraphs.root) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // re-selecting the same item
                                    launchSingleTop = true
                                    // Restore state when re-selecting a previously selected item
                                    restoreState = true
                                }
                            } else {
                                // When we click again on a bottom bar item and it was already selected
                                // we want to pop the back stack until the initial destination of this bottom bar item
                                navController.popBackStack(item.direction, false)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(26.dp),
                        contentDescription = stringResource(id = R.string.bottom_bar_icon),
                        imageVector = if (selectedIndex == item.ordinal) item.selectedIcon
                        else item.unselectedIcon,
                        tint = if (selectedIndex == item.ordinal) white_color
                        else backgroundColor
                    )
                }
            }
        }
    }
}

enum class BottomNavigationBarItem(
    val direction: DirectionDestination,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    CalorieTracker(CalorieTrackerScreenDestination, Icons.Filled.FoodBank, Icons.Outlined.FoodBank),
    TrainingTracker(TrainingTrackerScreenDestination, Icons.Filled.FitnessCenter, Icons.Outlined.FitnessCenter),
    Programs(ProgramsScreenDestination, Icons.Filled.Description, Icons.Outlined.Description)
}

fun Modifier.noRippleClickable(enabled: Boolean, onClick: () -> Unit): Modifier = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        enabled = enabled
    ) {
        onClick()
    }
}

@Composable
fun shapeCornerRadius(
    topLeft: Dp = 0.dp,
    topRight: Dp = 0.dp,
    bottomRight: Dp = 0.dp,
    bottomLeft: Dp = 0.dp
): ShapeCornerRadius {
    return ShapeCornerRadius(
        topLeft = topLeft.toPxf(),
        topRight = topRight.toPxf(),
        bottomRight = bottomRight.toPxf(),
        bottomLeft = bottomLeft.toPxf()
    )
}