package healthWave.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import healthWave.ui.theme.black_color
import healthWave.ui.theme.transparent_color
import kotlinx.coroutines.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNavigationDrawer(
    backgroundColor: Color,
    drawerState: DrawerState,
    content: @Composable () -> Unit,
    onMyProfileClicked: () -> Job,
    onSetUpANewGoalClicked: () -> Job,
    onNotificationsClicked: () -> Job,
    onApplicationsThemeClicked: () -> Job
) {
    val items = initializeNavigationDrawerItems()
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 50.dp, 0.dp)
                    .fillMaxHeight(),
                drawerContainerColor = backgroundColor
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = item.title)
                        },
                        selected = index == selectedItemIndex,
                        onClick = {
                            selectedItemIndex = index
                            when (index) {
                                0 -> onMyProfileClicked()
                                1 -> onSetUpANewGoalClicked()
                                2 -> onNotificationsClicked()
                                3 -> onApplicationsThemeClicked()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                tint = black_color,
                                contentDescription = item.title
                            )
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding),
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = transparent_color,
                            unselectedContainerColor = transparent_color
                        )
                    )
                }
            }
        },
        drawerState = drawerState,
        content = content
    )
}

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val switchState: Boolean = false
)

fun initializeNavigationDrawerItems(): List<NavigationItem> {
    return listOf(
        NavigationItem(
            title = "My profile",
            icon = Icons.Filled.PersonPin
        ),
        NavigationItem(
            title = "Set up a new goal",
            icon = Icons.Filled.AddCircle
        ),
        NavigationItem(
            title = "Notifications",
            icon = Icons.Filled.Notifications
        ),
        NavigationItem(
            title = "Application's theme",
            icon = Icons.Filled.DarkMode
        )
    )
}