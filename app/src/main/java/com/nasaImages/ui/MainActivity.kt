package com.nasaImages.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.compose.AsyncImage
import com.nasaImages.R
import com.nasaImages.model.Item
import com.nasaImages.model.MainViewModel
import com.nasaImages.ui.theme.*

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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SearchBar(viewModel = mainViewModel)
                        SearchButton(viewModel = mainViewModel, focusManager = focusManager)
                    }
                    ProgressIndicator(viewModel = mainViewModel)
                    ImageList(viewModel = mainViewModel)
                    ImageInfoModalHolder(viewModel = mainViewModel)
                }
            }
        }
    }
}

@Composable
fun SearchBar(viewModel: MainViewModel) {
    TextField(
        modifier = Modifier
            .fillMaxWidth(0.7F)
            .height(90.dp)
            .padding(20.dp, 20.dp, 5.dp, 10.dp),
        value = viewModel.searchQuery.collectAsState().value,
        onValueChange = { viewModel.updateSearchQuery(it) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = White,
            focusedLabelColor = Blue,
            focusedIndicatorColor = Blue,
            textColor = Black,
            unfocusedLabelColor = Blue,
            unfocusedIndicatorColor = Blue,
            cursorColor = Blue
        ),
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = "",
                modifier = Modifier.height(20.dp),
                tint = DarkGray)
        },
        placeholder = { Text(text = "Search", color = DarkGray) }
    )
}

@Composable
fun SearchButton(viewModel: MainViewModel, focusManager: FocusManager) {
    Button(
        onClick = {
            viewModel.performSearch()
            focusManager.clearFocus()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(91.dp)
            .padding(0.dp, 20.dp, 20.dp, 10.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Blue)
    ) {
        Text(
            text = "Search",
            color = White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun ProgressIndicator(viewModel: MainViewModel) {
    if (viewModel.progressIndicatorVisible.collectAsState().value) {
        CircularProgressIndicator(
            modifier = Modifier
                .padding(0.dp, 20.dp, 0.dp, 0.dp),
            color = Blue
        )
    }
}

@Composable
fun ImageList(viewModel: MainViewModel) {
    val listItems = viewModel.searchResult.collectAsState().value?.collection?.items
    
    listItems?.let { 
        LazyColumn {
            items(it) { item ->
                ImageRow(viewModel = viewModel, item = item)
            }
        }
    }
}

@Composable
fun ImageRow(viewModel: MainViewModel, item: Item) {
    val itemData = item.data?.get(0)
    val itemLinks = item.links?.get(0)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(10.dp, 0.dp, 10.dp, 7.dp)
            .clickable {
                viewModel.updateImageInfo(
                    itemLinks?.href ?: "",
                    itemData?.title ?: "",
                    itemData?.description ?: "",
                    itemData?.dateCreated ?: "")
                viewModel.setImageInfoVisible(true)
            },
        elevation = 10.dp,
        backgroundColor = White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Column(modifier = Modifier
                .width(140.dp)
                .padding(10.dp, 10.dp, 0.dp, 10.dp)
            ) {
                AsyncImage(
                    model = itemLinks?.href ?: "",
                    contentDescription = "",
                    modifier = Modifier
                        .width(120.dp)
                        .height(80.dp),
                    placeholder = painterResource(id = R.drawable.placeholder_icon),
                    fallback = painterResource(id = R.drawable.placeholder_icon)
                )
            }
            Text(
                text = itemData?.title ?: "",
                modifier = Modifier.padding(0.dp, 10.dp, 10.dp, 10.dp)
            )
        }
    }
}

@Composable
fun ImageInfoModalHolder(viewModel: MainViewModel) {
    if (viewModel.imageInfoVisible.collectAsState().value) {
        ImageInfoModal(viewModel = viewModel)
    }
}


