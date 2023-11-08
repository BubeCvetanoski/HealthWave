package healthWave.fragments

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Girl
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.healthwave.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import healthWave.core.util.HelperFunctions.Companion.navigateTo
import healthWave.data.local.database.entity.User
import healthWave.destinations.CalorieTrackerScreenDestination
import healthWave.destinations.MyProfileScreenDestination
import healthWave.destinations.ProgramsScreenDestination
import healthWave.destinations.TrainingTrackerScreenDestination
import healthWave.launcher.presentation.viewmodel.SharedUserViewModel
import healthWave.ui.components.InformativeTextComposable
import healthWave.ui.components.InformativeTextItem
import healthWave.ui.components.UpdateNameDialog
import healthWave.ui.theme.black_color

@Destination
@Composable
fun MyProfileScreen(
    sharedUserViewModel: SharedUserViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    id: Int
) {
    val context = LocalContext.current
    val user = sharedUserViewModel.userState.collectAsState()
    val informationItems = initializeInformativeTextItem(
        user = user.value,
        context = context
    )
    val shouldShowCustomUpdateNameDialog = remember { mutableStateOf(false) }

    val baseColor = remember { mutableStateOf(Color.Unspecified) }
    val detailsColor = remember { mutableStateOf(Color.Unspecified) }
    val backgroundColor = remember { mutableStateOf(Color.Unspecified) }
    val itemsColor = remember { mutableStateOf(Color.Unspecified) }

    baseColor.value = sharedUserViewModel.getHealthWaveColors().first
    detailsColor.value = sharedUserViewModel.getHealthWaveColors().second
    backgroundColor.value = sharedUserViewModel.getCurrentApplicationThemeColors().first
    itemsColor.value = sharedUserViewModel.getCurrentApplicationThemeColors().second

    val myProfilePic: ImageVector = when (user.value.gender) {
        stringResource(id = R.string.male) -> Icons.Filled.Man
        stringResource(id = R.string.female) -> Icons.Filled.Girl
        else -> Icons.Filled.Man
    }

    val onIconClicked = {
        shouldShowCustomUpdateNameDialog.value = true
    }

    val onBack = {
        when (id) {
            0 -> {
                navigateTo(
                    screen = CalorieTrackerScreenDestination,
                    popUpToRoute = MyProfileScreenDestination.route,
                    navigator = navigator
                )
            }

            1 -> {
                navigateTo(
                    screen = TrainingTrackerScreenDestination,
                    popUpToRoute = MyProfileScreenDestination.route,
                    navigator = navigator
                )
            }

            2 -> {
                navigateTo(
                    screen = ProgramsScreenDestination,
                    popUpToRoute = MyProfileScreenDestination.route,
                    navigator = navigator
                )
            }
        }
    }

    BackHandler { onBack() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor.value),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        ) {
            //Images
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.my_profile_background),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(backgroundColor.value)
                    .align(Alignment.BottomCenter)
            ) {
                Icon(
                    imageVector = myProfilePic,
                    contentDescription = stringResource(id = R.string.gender_symbol),
                    tint = detailsColor.value,
                    modifier = Modifier
                        .size(width = 40.dp, height = 80.dp)
                        .align(Alignment.Center)
                )
            }
        }
        //First name, last name and edit icon
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = user.value.firstName + " " + user.value.lastName,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = stringResource(id = R.string.edit_name),
                tint = detailsColor.value,
                modifier = Modifier.clickable { onIconClicked() }
            )
            if (shouldShowCustomUpdateNameDialog.value) {
                ShowCustomUpdateNameDialog(baseColor.value, sharedUserViewModel, user.value) {
                    shouldShowCustomUpdateNameDialog.value = false
                }
            }
        }
        //Text fields under the name of the user
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            informationItems.forEach {
                InformativeTextComposable(
                    title = it.title,
                    text = it.text,
                    itemsColor.value
                )
            }
            Button(
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = baseColor.value,
                    contentColor = black_color
                ),
                onClick = { onBack() }
            ) {
                Text(
                    text = stringResource(id = R.string.back),
                    fontSize = 14.sp
                )
            }
        }
    }
}

private fun initializeInformativeTextItem(
    user: User,
    context: Context
): List<InformativeTextItem> {

    return listOf(
        InformativeTextItem(
            title = context.getString(R.string.gender),
            text = user.gender
        ),
        InformativeTextItem(
            title = context.getString(R.string.age),
            text = user.age
        ),
        InformativeTextItem(
            title = context.getString(R.string.height),
            text = user.height
        ),
        InformativeTextItem(
            title = context.getString(R.string.weight),
            text = user.weight
        ),
        InformativeTextItem(
            title = context.getString(R.string.goal),
            text = user.goal + " with " + user.calories + " calories"
        )
    )
}

@Composable
fun ShowCustomUpdateNameDialog(
    containerColor: Color,
    sharedUserViewModel: SharedUserViewModel,
    user: User,
    onDismiss: () -> Unit
) {
    UpdateNameDialog(
        containerColor = containerColor,
        sharedUserViewModel = sharedUserViewModel,
        user = user,
        onDismiss = onDismiss
    )
}