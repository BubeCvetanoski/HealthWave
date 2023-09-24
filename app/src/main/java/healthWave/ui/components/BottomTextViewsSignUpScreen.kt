package healthWave.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthwave.R
import healthWave.ui.theme.white_color

@Composable
fun BottomTextViewsSignUpScreen(
    secondText: String
) {
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = stringResource(id = R.string.please_enter_needed_info),
        color = white_color,
        fontSize = 10.sp
    )
    Spacer(modifier = Modifier.height(15.dp))
    Text(
        text = secondText,
        color = white_color,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold
    )
}