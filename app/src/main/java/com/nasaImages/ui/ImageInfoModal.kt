package com.nasaImages.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nasaImages.R
import com.nasaImages.model.MainViewModel
import com.nasaImages.ui.theme.DarkGray
import com.nasaImages.ui.theme.LightBackground

@Composable
fun ImageInfoModal(viewModel: MainViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = LightBackground
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(0.15F),
                onClick = { viewModel.setImageInfoVisible(false) },
                colors = ButtonDefaults.buttonColors(backgroundColor = LightBackground),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back_arrow),
                    tint = DarkGray,
                    contentDescription = ""
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 30.dp, 0.dp, 0.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = viewModel.image.collectAsState().value,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth(0.9F)
                    .padding(20.dp)
            )
            Text(text = stringResource(
                id = R.string.title_string,
                viewModel.title.collectAsState().value),
                modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 10.dp)
            )
            Text(text = stringResource(
                id = R.string.description_string,
                viewModel.description.collectAsState().value),
                modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 10.dp)
            )
            Text(text = stringResource(
                id = R.string.date_string,
                viewModel.date.collectAsState().value),
                modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 10.dp)
            )

        }
    }
}