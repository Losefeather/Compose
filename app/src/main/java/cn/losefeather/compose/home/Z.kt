package cn.losefeather.compose.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ListItem
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.losefeather.compose.R
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PreviewCustomConstraintLayout2() {
    MainScreen2()
}

@Composable
fun MainScreen2() {
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    // SwipeRefresh with LazyColumn
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            isRefreshing = true
            coroutineScope.launch {
                // Simulate a network request or data loading
                delay(2000) // Simulate a delay
                isRefreshing = false
            }
        },
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = scrollState
        ) {

            item {
                if (isRefreshing) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Green),
                        // Apply offset for bounce effect
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Refreshing...", color = Color.Black, fontSize = 18.sp)
                    }
                }
            }
            item {
                CustomConstraintLayout()
            }
            item {
                ImageBanner()
            }
            item {
                TabLayout2()
            }
        }
    }
}

@Composable
fun ImageBanner() {
    val images = listOf(
        "https://img-pre.ivsky.com/img/tupian/pre/201707/17/jinmendaqiao-012.jpg",
        "https://img-pre.ivsky.com/img/tupian/pre/201805/17/golden_gate_bridge-005.jpg"
    )

    val pagerState = rememberPagerState {
        images.size
    }

    // 自动播放
    LaunchedEffect(Unit) {
        while (true) {
            delay(2000) // 每2秒切换一次
            if (pagerState.currentPage < images.size - 1) {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            } else {
                pagerState.animateScrollToPage(0)
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(images[page])
                .build(),
            contentDescription = stringResource(R.string.app_name),
        )
    }
}

@Composable
fun TabLayout2() {
    // Tab titles
    val tabTitles = listOf("Tab 1", "Tab 2", "Tab 3")
    val pagerState = rememberPagerState { tabTitles.size }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth()) {
        // TabRow
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth(),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                )
            }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(title) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp) // Set a fixed height
        ) { page ->
            TabContent(page)
        }
    }
}

@Composable
fun TabContent(page: Int) {
    val items = List(20) { "Item #$it in Tab $page" } // Sample data for RecyclerView
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        items(items.size) { index ->
            ListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { /* Handle item click */ },
                headlineContent = {
                    Text(text = items[index])
                }
            )
        }
    }
}