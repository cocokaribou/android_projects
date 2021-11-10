package com.example.jetpack_compose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.jetpack_compose.data.Bakery
import com.example.jetpack_compose.ui.theme.Jetpack_composeTheme
import org.burnoutcrew.reorderable.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity2 : ComponentActivity() {
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

    @ExperimentalUnitApi
    override fun onCreate(savedInstanceState: Bundle?) {
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
                    }
                }
            }
        }
    }

    private fun requestBakery(): MutableList<String> {
        val list = mutableListOf<String>()
        val bakeryApi = BakeryAPI.create()
        bakeryApi.getBakery().enqueue(object : Callback<Bakery> {
            override fun onFailure(call: Call<Bakery>, t: Throwable) {
                Log.e("youngin", "onFailure")
            }

            override fun onResponse(call: Call<Bakery>, response: Response<Bakery>) {
                response.body()!!.voObject.bakeries.forEach {
                    list.add(it.storeNm)
                }
            }
        })
        return list
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

@ExperimentalUnitApi
@Composable
fun InitLazyRow(list: List<Int>) {
    val state = rememberReorderState()
    val data = list.toMutableStateList()
    Box {
        LazyRow(
            state = state.listState,
            horizontalArrangement = Arrangement.spacedBy(Dp(3f)),
            modifier = Modifier
                .reorderable(state, { from, to -> data.move(from.index, to.index) })
        ) {
            items(data, {it}) { i ->
                Box(
                    modifier = Modifier
                        .padding(start = Dp(15f), top = Dp(15f))
                        .draggedItem(state.offsetByKey(i))
                        .detectReorderAfterLongPress(state),
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
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "LazyRow", color = Color(0xF000000), fontSize = TextUnit(80f, TextUnitType.Sp))
        }
    }
}

@ExperimentalUnitApi
@Composable
fun InitLazyColumn(list: List<Int>) {
    val state = rememberReorderState()
    val data = list.toMutableStateList()

    Box(modifier = Modifier.padding(top = 20.dp)) {
        // lazycolumn (recycler)
        LazyColumn(
            state = state.listState,
            modifier = Modifier
                .reorderable(state, { from, to -> data.move(from.index, to.index) }),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(list, {it}) { i ->
                Card(shape = RoundedCornerShape(10.dp), modifier = Modifier.padding(5.dp), elevation = 3.dp) {
                    Row(
                        Modifier
                            .background(Color(0xFFEFEFE8))
                            .padding(20.dp)
                            .fillMaxWidth()
                            .draggedItem(state.offsetByKey(i))
                            .detectReorderAfterLongPress(state)
                    ) {
                        Text("$i ")
                        Text("item")
                    }
                }
            }
        }
        // alpha text
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "LazyColumn",
                color = Color(0xF000000),
                fontSize = TextUnit(50f, TextUnitType.Sp),
                modifier = Modifier.width(35.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Jetpack_composeTheme {
        Title()
    }
}