package cn.losefeather.compose.ui.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun BottomNavigationBar(index: Int = 0, onClick: (Int)->Unit  ,iconList: MutableList<NavigationBarInfo>){
    var selectedItem by remember { mutableIntStateOf(index) }
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.White,
        modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues())
    ) {
        for ((i, item) in iconList.withIndex()){
            BottomNavigationItem(
                icon = {
                    AsyncImage(model =
                    if(selectedItem == index) {
                        item.selectedIconUrl
                    }else{
                        item.unselectedIconUrl
                    }, contentDescription = item.info,)
                },
                label = { Text(item.info, color = if(selectedItem == index) Color.Black else Color.Gray, fontSize = if(selectedItem == index) 20.sp else 18.sp)},
                selected = selectedItem == index,
                onClick = { selectedItem = index
                    onClick.invoke(index)
                }
            )
        }
    }
}

data class NavigationBarInfo(val info: String, val selectedIconUrl:String , val unselectedIconUrl: String)