package com.mbj.composepaging.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.rememberImagePainter
import com.mbj.composepaging.R
import com.mbj.composepaging.data.remote.model.RickMorty
import com.mbj.composepaging.ui.theme.ComposePagingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePagingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel: MainViewModel = hiltViewModel()
                    val pagingRickMorty = viewModel.rickMorty.collectAsLazyPagingItems()

                    Greeting(
                        rickMortyItem = pagingRickMorty,
                        combinedLoadStates = pagingRickMorty.loadState,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(rickMortyItem: LazyPagingItems<RickMorty>, combinedLoadStates: CombinedLoadStates, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            rickMortyItem.retry()
        }) {
            Column {
                Text(
                    "Click Me",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    tint = Color.Yellow
                )
            }

            LazyColumn(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when {
                    combinedLoadStates.refresh is LoadState.Loading -> {
                        /**
                         * 새로 고침 중일 때 로딩 상태를 표시
                         */
                        item {
                            Text("로딩 중...", modifier = Modifier.fillMaxWidth())
                        }
                    }

                    combinedLoadStates.append is LoadState.Loading -> {
                        /**
                         * 추가 데이터를 로드 중일 때 로딩 상태를 표시
                         */
                        item {
                            Text("더 불러오는 중...", modifier = Modifier.fillMaxWidth())
                        }
                    }

                    combinedLoadStates.refresh is LoadState.Error -> {
                        /**
                         * 새로 고침 중에 오류가 발생했을 때 오류 상태를 표시
                         */
                        val errorMessage =
                            (combinedLoadStates.refresh as LoadState.Error).error.localizedMessage
                        item {
                            Text("로딩 오류: $errorMessage", modifier = Modifier.fillMaxWidth())
                        }
                    }

                    combinedLoadStates.append is LoadState.Error -> {
                        /**
                         * 추가 데이터를 로드 중에 오류가 발생했을 때 오류 상태를 표시
                         */
                        val errorMessage =
                            (combinedLoadStates.append as LoadState.Error).error.localizedMessage
                        item {
                            Text("더 불러오기 오류: $errorMessage", modifier = Modifier.fillMaxWidth())
                        }
                    }

                    else -> {
                        /**
                         * 로딩 상태가 아닐 때 페이징 아이템을 표시
                         */
                        itemsIndexed(rickMortyItem) { index, item ->
                            if (item != null) {
                                Log.d("@@ PagingTest", "Item at index $index: ${item.name}")
                                RickMortyItem(rickMortyItem = item)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RickMortyItem(rickMortyItem: RickMorty) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val painter = rememberImagePainter(
                data = rickMortyItem.image,
                builder = {
                    crossfade(durationMillis = 1000)
                    error(R.drawable.ic_placeholder)
                    placeholder(R.drawable.ic_placeholder)
                }
            )
            Image(
                painter = painter,
                contentDescription = "Rick and Morty",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = rickMortyItem.name!!,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "성별: ${rickMortyItem.gender}")

        }
    }
}
