package healthWave.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import healthWave.ui.theme.white_color

@Composable
fun CustomSnackBar(message: String, arrangement: Arrangement.Vertical = Arrangement.Bottom, snackbarHostState: SnackbarHostState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = arrangement
    ) {
        SnackbarHost(
            hostState = snackbarHostState,
            snackbar = {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(2.dp, white_color),
                    modifier = Modifier
                        .padding(16.dp)
                        .wrapContentSize()
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Logo(
                            spaceBetweenElements = 0.dp,
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp),
                            logoNameFontSize = 10.sp
                        )
                        Text(
                            text = message,
                            color = white_color,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        )
    }
}
