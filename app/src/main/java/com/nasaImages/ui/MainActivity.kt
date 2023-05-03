package com.nasaImages.ui

import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nasaImages.R
import com.nasaImages.model.Item
import com.nasaImages.model.MainViewModel
import com.nasaImages.repository.NasaImagesRepositoryImpl
import com.nasaImages.ui.theme.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

val mainViewModel = MainViewModel(NasaImagesRepositoryImpl())

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val listState = rememberLazyListState()

            NasaImagesTheme {
                val focusManager = LocalFocusManager.current
                if (!mainViewModel.imageInfoVisible.collectAsState().value) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(LightBackground)
                            .pointerInput(Unit) {
                                detectTapGestures { focusManager.clearFocus() }
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SearchLayout(viewModel = mainViewModel, focusManager = focusManager)
                        InfoText(viewModel = mainViewModel)
                        ProgressIndicator(viewModel = mainViewModel)
                        ImageList(viewModel = mainViewModel, listState = listState)
                        ErrorModalHolder(viewModel = mainViewModel)
                    }
                } else {
                    ImageInfoModalHolder(viewModel = mainViewModel)
                }
            }
        }

        onBackPressedDispatcher.addCallback {
            mainViewModel.setImageInfoVisible(false)
        }
    }
}

@Composable
fun SearchLayout(viewModel: MainViewModel, focusManager: FocusManager) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchBar(viewModel = viewModel)
        SearchButton(viewModel = viewModel, focusManager = focusManager)
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
                tint = DarkGray
            )
        },
        placeholder = {
            Text(text = stringResource(id = R.string.search), color = DarkGray)
        }
    )
}

@Composable
fun SearchButton(viewModel: MainViewModel, focusManager: FocusManager) {
    Button(
        onClick = {
            viewModel.performSearch(1)
            focusManager.clearFocus()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(91.dp)
            .padding(0.dp, 20.dp, 20.dp, 10.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Blue)
    ) {
        Text(
            text = stringResource(id = R.string.search),
            color = White,
            fontSize = 16.sp,
            maxLines = 1
        )
    }
}

@Composable
fun InfoText(viewModel: MainViewModel) {
    if (viewModel.infoTextVisible.collectAsState().value) {
        Text(
            text = stringResource(id = R.string.no_results),
            color = DarkGray
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
fun ImageList(viewModel: MainViewModel, listState: LazyListState) {
    val listItems = rememberSaveable { mutableStateOf<List<Item>>(listOf()) }
    viewModel.searchResult.collectAsState().value?.collection?.items?.let {
        listItems.value = it
    }

    LazyColumn(state = listState) {
        items(listItems.value) { item ->
            ImageRow(viewModel = viewModel, item = item)
        }
        item {
            NavigationView(viewModel = viewModel, listState = listState)
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
                    itemData?.dateCreated ?: ""
                )
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
fun NavigationView(viewModel: MainViewModel, listState: LazyListState) {
    val currentPage = viewModel.currentPage.collectAsState().value
    val coroutineScope = rememberCoroutineScope()

    if (viewModel.navigationVisible.collectAsState().value) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 0.dp, 0.dp, 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    viewModel.performSearch(currentPage - 1)
                    coroutineScope.launch {
                        listState.scrollToItem(0)
                    }
                },
                modifier = Modifier
                    .height(60.dp)
                    .padding(0.dp, 10.dp, 0.dp, 10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Blue)
            ) {
                Text(
                    text = stringResource(id = R.string.back),
                    color = White,
                    fontSize = 16.sp
                )
            }
            Text(
                text = stringResource(id = R.string.page_number, currentPage),
                modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp),
                color = DarkGray
            )
            Button(
                onClick = {
                    viewModel.performSearch(currentPage + 1)
                    coroutineScope.launch {
                        listState.scrollToItem(0)
                    }
                },
                modifier = Modifier
                    .height(60.dp)
                    .padding(0.dp, 10.dp, 0.dp, 10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Blue)
            ) {
                Text(
                    text = stringResource(id = R.string.next),
                    color = White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun ErrorModalHolder(viewModel: MainViewModel) {
    if (viewModel.errorModalVisible.collectAsState().value) {
        ErrorModal(viewModel = viewModel)
    }
}

@Composable
fun ImageInfoModalHolder(viewModel: MainViewModel) {
    if (viewModel.imageInfoVisible.collectAsState().value) {
        ImageInfoView(viewModel = viewModel)
    }
}