package com.nasaImages.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.nasaImages.R
import com.nasaImages.model.MainViewModel
import com.nasaImages.ui.theme.Blue
import com.nasaImages.ui.theme.White

@Composable
fun ErrorModal(viewModel: MainViewModel) {
    Dialog(onDismissRequest = { viewModel.setErrorModalVisible(false) }) {
        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = 10.dp
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.error_title),
                    modifier = Modifier.padding(20.dp)
                )
                Text(
                    text = stringResource(id = R.string.error_description),
                    modifier = Modifier.padding(20.dp, 0.dp, 20.dp,20.dp)
                )
                Text(
                    text = stringResource(id = R.string.error_code, viewModel.errorCode.collectAsState().value),
                    modifier = Modifier.padding(20.dp, 0.dp, 20.dp,20.dp)
                )
                Button(
                    onClick = { viewModel.setErrorModalVisible(false) },
                    modifier = Modifier
                        .fillMaxWidth(0.3F)
                        .height(65.dp)
                        .padding(0.dp, 0.dp, 0.dp, 20.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Blue)
                ) {
                    Text(
                        text = stringResource(id = R.string.OK),
                        color = White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}