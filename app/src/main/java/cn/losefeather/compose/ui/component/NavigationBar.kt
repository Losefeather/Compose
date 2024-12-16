package cn.losefeather.compose.ui.component

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import coil3.compose.AsyncImage

@Composable
fun BottomNavigationBar(index: Int = 0, onClick: (Int)->Unit  ,iconList: MutableList<NavigationBarInfo>){
    var selectedItem by remember { mutableIntStateOf(index) }
    BottomNavigation(
        backgroundColor = Color.Blue,
        contentColor = Color.White
    ) {
        for ((index, item) in iconList.withIndex()){
            BottomNavigationItem(
                icon = {
                    AsyncImage(model =
                    if(selectedItem == index) {
                        item.selectedIconUrl
                    }else{
                        item.unselectedIconUrl
                    }, contentDescription = item.info,)
                },
                label = { Text(item.info, color = if(selectedItem == index) Color.Blue else Color.Black) },
                selected = selectedItem == index,
                onClick = { selectedItem = index
                    onClick.invoke(index)
                }
            )
        }
    }
}

data class NavigationBarInfo(val info: String, val selectedIconUrl:String , val unselectedIconUrl: String)