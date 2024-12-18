package cn.losefeather.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cn.losefeather.compose.chat.CharPage
import cn.losefeather.compose.discovery.DiscoveryPage
import cn.losefeather.compose.home.HomePage
import cn.losefeather.compose.mine.MinePage
import cn.losefeather.compose.ui.component.BottomNavigationBar
import cn.losefeather.compose.ui.component.NavigationBarInfo
import cn.losefeather.compose.ui.theme.Compose2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Compose2Theme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(
                            0, onClick =  { index -> router(index, navController) },
                            iconList = ArrayList<NavigationBarInfo>(
                            ).apply { 
                                add(NavigationBarInfo("Home","",""))
                                add(NavigationBarInfo("Discovery","",""))
                                add(NavigationBarInfo("Chat","",""))
                                add(NavigationBarInfo("Mine","",""))
                            },)
                    }) { innerPadding ->
                    NavHost(navController = navController, startDestination = HOME_PAGE, modifier = Modifier.padding(innerPadding)) {
                        composable(HOME_PAGE) { HomePage() }
                        composable(DISCOVERY_PAGE) { DiscoveryPage() }
                        composable(CHAT_PAGE) { CharPage() }
                        composable(MINE_PAGE) { MinePage() }
                    }
                }
            }
        }
    }

    private fun router(index: Int, navController: NavController){
        when(index){
            0 ->{
                navController.navigate(HOME_PAGE)
            }
            1->{
                navController.navigate(DISCOVERY_PAGE)
            }
            2->{
                navController.navigate(CHAT_PAGE)
            }
            3->{
                navController.navigate(MINE_PAGE)
            }
            else ->{

            }
        }
    }
}
