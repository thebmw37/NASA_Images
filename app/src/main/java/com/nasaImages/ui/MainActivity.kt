package com.nasaImages.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nasaImages.R
import com.nasaImages.ui.theme.White
import com.nasaImages.model.MainViewModel
import com.nasaImages.ui.theme.DarkBlue
import com.nasaImages.ui.theme.LightBackground
import com.nasaImages.ui.theme.NasaImagesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainViewModel = MainViewModel()

        setContent {
            NasaImagesTheme {
                val focusManager = LocalFocusManager.current

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(LightBackground)
                        .pointerInput(Unit) {
                            detectTapGestures { focusManager.clearFocus() }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SearchBar(viewModel = mainViewModel)
                    SearchButton(viewModel = mainViewModel)
                }
            }
        }
    }
}

@Composable
fun SearchBar(viewModel: MainViewModel) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 20.dp, 20.dp, 20.dp),
        value = viewModel.searchQuery.collectAsState().value,
        onValueChange = { viewModel.updateSearchQuery(it) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = White
        ),
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = "",
                modifier = Modifier.height(25.dp),
                tint = LightBackground)
        }
    )
}

@Composable
fun SearchButton(viewModel: MainViewModel) {
    Button(
        onClick = { viewModel.performSearch() },
        modifier = Modifier.fillMaxWidth(0.3F).height(50.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = DarkBlue)
    ) {
        Text(
            text = "Search",
            color = White,
            fontSize = 16.sp
        )
    }
}