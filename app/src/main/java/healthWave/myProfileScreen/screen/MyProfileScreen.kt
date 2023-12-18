package healthWave.myProfileScreen.screen

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import healthWave.core.util.HelperFunctions.Companion.initializeInformativeTextItem
import healthWave.data.local.database.entity.User
import healthWave.launcher.presentation.event.SharedSignUpEvent
import healthWave.launcher.presentation.viewmodel.SharedUserViewModel
import healthWave.myProfileScreen.event.MyProfileEvent
import healthWave.myProfileScreen.viewmodel.MyProfileViewModel
import healthWave.ui.components.InformativeTextComposable
import healthWave.ui.components.UpdateNameDialog
import healthWave.ui.theme.HealthWaveColorScheme
import healthWave.ui.theme.black_color
import kotlinx.coroutines.launch

@Destination
@Composable
fun MyProfileScreen(
    sharedUserViewModel: SharedUserViewModel = hiltViewModel(),
    myProfileViewModel: MyProfileViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    id: Int
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val user = sharedUserViewModel.userState.collectAsState().value
    val showUpdateNameDialog = myProfileViewModel.showUpdateNameDialog.value

    val informationItems = context.initializeInformativeTextItem(
        user = user
    )

    val myProfilePic: ImageVector = when (user.gender) {
        stringResource(id = R.string.male) -> Icons.Filled.Man
        stringResource(id = R.string.female) -> Icons.Filled.Girl
        else -> Icons.Filled.Man
    }

    val onSaveClicked: (String, String) -> Unit = { firstName, lastName ->
        if (myProfileViewModel.onEvent(MyProfileEvent.ValidateFirstAndLastName(firstName, lastName)) as Boolean) {
            scope.launch {
                sharedUserViewModel.onEvent(
                    SharedSignUpEvent.UpdateUserFirstAndLastName(firstName, lastName)
                )
            }
            myProfileViewModel.onEvent(MyProfileEvent.DismissEditNameDialog)
        }
    }

    BackHandler { myProfileViewModel.onEvent(MyProfileEvent.OnBackClicked(id, navigator)) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HealthWaveColorScheme.backgroundColor),
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
                    .background(HealthWaveColorScheme.backgroundColor)
                    .align(Alignment.BottomCenter)
            ) {
                Icon(
                    imageVector = myProfilePic,
                    contentDescription = stringResource(id = R.string.gender_symbol),
                    tint = HealthWaveColorScheme.detailsElementsColor,
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
                text = user.firstName + " " + user.lastName,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = stringResource(id = R.string.edit_name),
                tint = HealthWaveColorScheme.detailsElementsColor,
                modifier = Modifier.clickable { myProfileViewModel.onEvent(MyProfileEvent.OnEditNameIconClicked) }
            )
            if (showUpdateNameDialog) {
                ShowUpdateNameDialog(
                    user = user,
                    onSaveClicked = onSaveClicked,
                    onDismiss = { myProfileViewModel.onEvent(MyProfileEvent.DismissEditNameDialog) }
                )
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
                    text = it.text
                )
            }
            Button(
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = HealthWaveColorScheme.baseElementsColor,
                    contentColor = black_color
                ),
                onClick = { myProfileViewModel.onEvent(MyProfileEvent.OnBackClicked(id, navigator)) }
            ) {
                Text(
                    text = stringResource(id = R.string.back),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun ShowUpdateNameDialog(
    user: User,
    onSaveClicked: (firstName: String, lastName: String) -> Unit,
    onDismiss: () -> Unit
) {
    UpdateNameDialog(
        user = user,
        onSaveClicked = onSaveClicked,
        onDismiss = onDismiss
    )
}