package cn.losefeather.compose.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import cn.losefeather.compose.R
import cn.losefeather.compose.ui.theme.Orange50
import kotlinx.coroutines.delay

@Composable
fun HomePage() {


}

@Composable
fun CustomConstraintLayout() {
    Box(modifier = Modifier.background(Color.Red)) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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
                    .constrainAs(buttonSearch) {
                        end.linkTo(parent.end, margin = 5.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(0.dp,5.dp,0.dp,5.dp)
                    .background(Orange50, shape = RoundedCornerShape(10.dp)), // 橘黄色背景和圆角
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)) // 橘黄色
            ) {
                Text(text = "搜索", color = Color.White) // 白色文字
            }


        }
    }
}


@Composable
fun AnimatedVerticalCarousel(modifier: Modifier = Modifier) {
    val texts = listOf("Google 摄像机", "拍照", "扫描")
    var currentIndex by remember { mutableIntStateOf(0) }
    var nextIndex by remember { mutableStateOf(1) }

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
        Box(modifier = Modifier.offset(y = offsetY.value.dp)) {
            Text(text = texts[currentIndex], color = Color.Black)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomConstraintLayout() {
    CustomConstraintLayout()
}
