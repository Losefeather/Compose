package cn.losefeather.compose.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.losefeather.compose.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TopBar(title:String = "", topBarListener: TopBarListener = DefaultTopBarListener()){
    CenterAlignedTopAppBar(
        title = {
            if(title.isNotEmpty()) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        },
        navigationIcon = {
            Box(
                modifier = Modifier
                    .clickable { topBarListener.onBackClick() }
                    .padding(8.dp) // 添加一些内边距
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground), // 替换为你的 drawable 资源
                    contentDescription = "返回",
                    modifier = Modifier.size(24.dp) // 设置图标大小
                )
            }
        }
    )
}

interface TopBarListener{
    fun onBackClick()
    fun onRightClick()
}

class DefaultTopBarListener: TopBarListener{
    override fun onBackClick() {

    }

    override fun onRightClick() {
    }

}