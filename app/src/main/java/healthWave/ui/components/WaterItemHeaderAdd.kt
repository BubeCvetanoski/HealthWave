package healthWave.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.LocalDrink
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthwave.R
import healthWave.fragments.calorieTracker.presentation.viewmodel.WaterState
import healthWave.ui.theme.black_color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterItemHeaderAdd(
    modifier: Modifier = Modifier,
    waterItemBorderColor: Color,
    outlinedTextFieldColors: TextFieldColors,
    backgroundColor: Color,
    takeWaterIntake: WaterState,
    onChangeWaterMilliliters: (String) -> Unit,
    onInsertWater: () -> Unit
) {
    Box {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(5.dp))
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = waterItemBorderColor
                        ),
                        shape = RoundedCornerShape(5.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(100.dp)
                            .background(backgroundColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.TwoTone.LocalDrink,
                            contentDescription = stringResource(id = R.string.water_icon),
                            tint = black_color,
                            modifier = Modifier
                                .padding(5.dp)
                                .size(100.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Row(modifier = Modifier.padding(5.dp)) {
                        Text(
                            text = stringResource(id = R.string.amount),
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        OutlinedTextField(
                            value = takeWaterIntake.milliliters,
                            onValueChange = { newMilliliters ->
                                onChangeWaterMilliliters(newMilliliters)
                            },
                            singleLine = true,
                            maxLines = 1,
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .wrapContentHeight(Alignment.CenterVertically)
                                .width(90.dp)
                                .padding(5.dp),
                            colors = outlinedTextFieldColors,
                            shape = RoundedCornerShape(5.dp),
                            keyboardOptions = KeyboardOptions(
                                imeAction = if (takeWaterIntake.milliliters.isNotBlank()) {
                                    ImeAction.Done
                                } else ImeAction.Default,
                                keyboardType = KeyboardType.Number
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    onInsertWater()
                                    defaultKeyboardAction(ImeAction.Done)
                                }
                            )
                        )
                        Text(
                            text = "ml",
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}