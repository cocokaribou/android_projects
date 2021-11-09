package com.example.jetpack_compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.example.jetpack_compose.ui.theme.Jetpack_composeTheme

class MainActivity2 : ComponentActivity() {
    @ExperimentalUnitApi
    override fun onCreate(savedInstanceState: Bundle?) {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        super.onCreate(savedInstanceState)
        Intent()
        setContent {
            Jetpack_composeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Box {
                        Column {
                            Title()
                            InitLazyRow(list)
                            InitLazyColumn(list)
                        }
                        Text(
                            text = "test tes",
                            fontSize = TextUnit(100f, TextUnitType.Sp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            textAlign = TextAlign.Right,
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun Title() {
    Column(
        modifier = Modifier
            .border(Dp(0.2f), androidx.compose.ui.graphics.Color.Black)
            .padding(Dp(10f))
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(text = "Made with Android Jetpack Compose🤖")
    }
}

@Composable
fun InitLazyRow(intList: List<Int>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(Dp(3f))
    ) {
        items(intList) { i ->
            itemCompo(i)
        }
    }
}

@Composable
fun InitLazyColumn(intList: List<Int>) {
    val state = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.padding(top = Dp(20f)),
        verticalArrangement = Arrangement.spacedBy(Dp(3f))
    ) {
        items(intList) { i ->
            itemCompo(i)
        }
    }
}

@Composable
fun itemCompo(i: Int) {
    Box(
        modifier = Modifier.padding(start = Dp(15f), top = Dp(15f)),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "icon"
        )
        Text(
            text = i.toString(),
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Jetpack_composeTheme {
        Title()
    }
}