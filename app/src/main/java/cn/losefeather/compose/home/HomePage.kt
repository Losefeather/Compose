package cn.losefeather.compose.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import cn.losefeather.compose.R
import cn.losefeather.compose.ui.theme.Orange50
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomePage() {
    MainScreen()
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomConstraintLayout() {
    CustomConstraintLayout()
}

@Composable
fun SwipeRefresh(isRefreshing: Boolean, onRefreshFunction: () -> Unit) {
    val state: SwipeRefreshState = rememberSwipeRefreshState(isRefreshing)
    SwipeRefresh(
        state,
        onRefresh = {
            onRefreshFunction.invoke()
        },
    ) {

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
            delay(2000) // 每3秒切换一次
            if (pagerState.currentPage < images.size - 1) {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            } else {
                pagerState.animateScrollToPage(0)
            }
        }
    }

    Column(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()// 设置轮播图的高度
        ) { page ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(images[page])
                    .build(),
                //placeholder = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = stringResource(R.string.app_name),
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullRefresh(isRefreshing: Boolean, onRefreshFunction: () -> Unit) {
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh = {
        onRefreshFunction.invoke()
    })
    Box(Modifier.pullRefresh(pullRefreshState, true)) {
        CustomConstraintLayout()
        LazyColumn(Modifier.fillMaxSize()) {

        }
        PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))

    }
}

@Composable
fun CustomConstraintLayout() {
    Box(modifier = Modifier
        .background(Color.Red)
        .padding(16.dp)) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color.White, shape = RoundedCornerShape(10.dp)) // 白色背景和圆角
        ) {
            // 创建约束布局的引用
            val (iconScan, divider, iconCamera, buttonSearch, carousel) = createRefs()

            // 扫码图标
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // 替换为你的扫码图标资源
                contentDescription = "Scan Icon",
                modifier = Modifier
                    .constrainAs(iconScan) {
                        start.linkTo(parent.start, margin = 5.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .size(24.dp) // 设置图标大小
            )

            // 分隔线
            Box(
                modifier = Modifier
                    .constrainAs(divider) {
                        start.linkTo(iconScan.end, margin = 1.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(carousel.start)
                    }
                    .width(1.dp)
                    .background(Color.Red)
            )

            // 启动轮播
            AnimatedVerticalCarousel(modifier = Modifier.constrainAs(carousel) {
                start.linkTo(divider.end, margin = 3.dp)
                end.linkTo(iconCamera.start, margin = 3.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            })

            // 照相机图标
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // 替换为你的照相机图标资源
                contentDescription = "Camera Icon",
                modifier = Modifier
                    .constrainAs(iconCamera) {
                        end.linkTo(buttonSearch.start, margin = 5.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .size(24.dp) // 设置图标大小
            )

            // 搜索按钮
            Button(
                onClick = { /* Handle search action */ },
                modifier = Modifier
                    .background(Orange50, shape = RoundedCornerShape(10.dp))
                    .constrainAs(buttonSearch) {
                        end.linkTo(parent.end, margin = 3.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .height(33.dp)
                    .aspectRatio(2f), // 橘黄色背景和圆角
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(text = "搜索", color = Color.White, fontSize = 13.sp) // 白色文字
            }
        }
    }
}

@Composable
fun AnimatedVerticalCarousel(modifier: Modifier = Modifier) {
    val texts = listOf("Google 摄像机", "拍照", "扫描")
    var currentIndex by remember { mutableIntStateOf(0) }
    var nextIndex by remember { mutableIntStateOf(1) }

    // 动画控制
    val offsetY = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            offsetY.animateTo(
                targetValue = -20f, // 向上移动的距离
                animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )
            // 更新索引
            currentIndex = nextIndex
            nextIndex = (nextIndex + 1) % texts.size
            // 重置位置
            offsetY.snapTo(0f)
            delay(1000)
        }
    }

    // 显示当前文本
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp) // 设置高度以适应文本
            .clip(RoundedCornerShape(5.dp)) // 限制文本区域
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        // 使用 Box 来实现上下滚动效果
        Box(modifier = Modifier.absoluteOffset(y = offsetY.value.dp)) {
            Text(text = texts[currentIndex], color = Color.Black)
        }
    }
}

@Composable
fun MainScreen() {
    var isRefreshing by remember { mutableStateOf(false) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    // SwipeRefresh with offset
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {
            offsetY = 200f
            isRefreshing = true
            coroutineScope.launch {
                // Simulate a network request or data loading
                delay(2000) // Simulate a delay
                isRefreshing = false
            }
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
//                .verticalScroll(state = scrollState, enabled = true)
//                .graphicsLayer { // Apply graphics layer for bounce effect
//                    translationY = offsetY
//                    Log.e("MainScreen", "MainScreen: graphicsLayer $translationY,    $offsetY")
//                }
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
                CustomConstraintLayout()
                ImageBanner()
                TabLayout()
            }
        }


        // Reset offset when refresh is complete
        LaunchedEffect(isRefreshing) {
            if (!isRefreshing) {
                offsetY = 0f // Reset offset when not refreshing
            }
        }
    }
}

@Composable
fun TabContent() {
    val items = List(20) { "Item #$it" } // Sample data for RecyclerView
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        items(items.size) {
            ListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { /* Handle item click */ },
                headlineContent = {
                }
            )
        }
    }
}

@Composable
fun TabLayout() {
    // Tab titles
    val tabTitles = listOf("Tab 1", "Tab 2", "Tab 3")
    val pagerState = rememberPagerState { tabTitles.size }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        // TabRow
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth(),
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
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
            modifier = Modifier.weight(1f) // Fill the remaining space
        ) { page ->
            TabContent()
        }
    }
}
