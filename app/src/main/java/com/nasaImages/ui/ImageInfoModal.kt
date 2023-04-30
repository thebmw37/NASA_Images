package com.nasaImages.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.nasaImages.R
import com.nasaImages.model.MainViewModel
import com.nasaImages.ui.theme.White

@Composable
fun ImageInfoModal(viewModel: MainViewModel) {
    Dialog(onDismissRequest = { viewModel.setImageInfoVisible(false) }) {
        val focusManager = LocalFocusManager.current

        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = 10.dp
        ) {
            /*Button(
                modifier = Modifier.fillMaxWidth(0.15F),
                onClick = { viewModel.setImageInfoVisible(false) },
                colors = ButtonDefaults.buttonColors(backgroundColor = White),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.close_icon),
                    contentDescription = ""
                )
            }*/

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = viewModel.image.collectAsState().value,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth(0.9F)
                        .padding(20.dp)
                )
                Text(text = viewModel.title.collectAsState().value, modifier = Modifier.padding(20.dp))
                Text(text = viewModel.description.collectAsState().value, modifier = Modifier.padding(20.dp))
                Text(text = viewModel.date.collectAsState().value, modifier = Modifier.padding(20.dp))

            }
        }
    }
}