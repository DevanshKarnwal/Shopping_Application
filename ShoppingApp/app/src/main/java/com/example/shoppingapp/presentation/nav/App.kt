package com.example.shoppingapp.presentation.nav

import GetAllProductsScreenUi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.shoppingapp.presentation.screens.AllCategoryScreenUi
import com.example.shoppingapp.presentation.screens.CartScreenUi
import com.example.shoppingapp.presentation.screens.CheckOutScreenUi
import com.example.shoppingapp.presentation.screens.EachProductScreenUi
import com.example.shoppingapp.presentation.screens.HomeScreenUi
import com.example.shoppingapp.presentation.screens.LogInScreen
import com.example.shoppingapp.presentation.screens.NotificationScreenUi
import com.example.shoppingapp.presentation.screens.ProfileScreenUi
import com.example.shoppingapp.presentation.screens.SignUpScreen
import com.example.shoppingapp.presentation.screens.WishListScreenUi
import com.google.firebase.auth.FirebaseAuth
import com.example.bottombar.AnimatedBottomBar
import com.example.bottombar.components.BottomBarItem
import com.example.bottombar.model.IndicatorStyle

@Composable
fun App(
    firebaseAuth: FirebaseAuth
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var selectedItem  by remember { mutableIntStateOf(0) }
    val bottomNavItem = listOf(
        BottomItem(
            name = "Home",
            icon = Icons.Default.Home,
            selectedIcon = Icons.Filled.Home
        ),
        BottomItem(
            name = "WishList",
            icon = Icons.Default.Favorite,
            selectedIcon = Icons.Filled.Favorite
        ),
        BottomItem(
            name = "Cart",
            icon = Icons.Default.ShoppingCart,
            selectedIcon = Icons.Filled.ShoppingCart
        ),
        BottomItem(
            name = "Profile",
            icon = Icons.Default.Person,
            selectedIcon = Icons.Filled.Person
        ),

    )
    val startScreen = if (firebaseAuth.currentUser == null) {
        SubNavigation.LoginSignUpScreenRoutes
    }else{

        SubNavigation.HomeScreenRoutes
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {

            AnimatedBottomBar(
                selectedItem = selectedItem,
                itemSize = bottomNavItem.size,
                containerColor = Color.Transparent,
                indicatorStyle = IndicatorStyle.FILLED,
                indicatorColor = Color.LightGray,
                modifier = Modifier.padding(bottom = 50.dp)
            ){
                bottomNavItem.forEachIndexed {
                    index, item ->
                    BottomBarItem(
                        selected = selectedItem == index,
                        imageVector = item.icon,
                        label = item.name,
                        onClick = {
                           selectedItem = index
                            when(index){
                                0 -> {
                                    navController.navigate(Routes.HomeScreenRoute){
                                        popUpTo(SubNavigation.HomeScreenRoutes){
                                            inclusive = true
                                        }
                                    }
                                }
                                1 -> {
                                    navController.navigate(Routes.WishListScreenRoute){
                                        popUpTo(SubNavigation.HomeScreenRoutes){
                                            inclusive = true
                                        }
                                    }
                                }
                                2 -> {
                                    navController.navigate(Routes.CartScreenRoute){
                                        popUpTo(SubNavigation.HomeScreenRoutes){
                                            inclusive = true
                                        }
                                    }
                                }
                                3 -> {
                                    navController.navigate(Routes.ProfileScreenRoute){
                                        popUpTo(SubNavigation.HomeScreenRoutes){
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    ) {
        innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){

            NavHost(
                navController = navController,
                startDestination = startScreen,
            ){
                navigation<SubNavigation.LoginSignUpScreenRoutes>(startDestination = Routes.LoginScreenRoute){

                    composable<Routes.SignUpScreenRoute> {
                        SignUpScreen(navController= navController)
                    }
                    composable<Routes.LoginScreenRoute>(){
                        LogInScreen(navController = navController)
                    }
                }
                navigation<SubNavigation.HomeScreenRoutes>(startDestination = Routes.HomeScreenRoute){

                    composable<Routes.HomeScreenRoute> {
                        HomeScreenUi(navController = navController)
                    }

                    composable<Routes.NotificationScreenRoute> {
                        NotificationScreenUi()
                    }

                    composable<Routes.ProfileScreenRoute> {
                        ProfileScreenUi()
                    }
                    composable<Routes.WishListScreenRoute> {
                        WishListScreenUi()
                    }
                    composable<Routes.CartScreenRoute> {
                        CartScreenUi()
                    }
                    composable<Routes.CheckOutScreenRoute> {
                        CheckOutScreenUi()
                    }
                    composable<Routes.SeeAllProductsScreenRoute> {
                        val data = it.toRoute<Routes.SeeAllProductsScreenRoute>()
                        GetAllProductsScreenUi(data.categoryName)
                    }
                    composable<Routes.SeeAllCategoriesScreenRoute> {
                        AllCategoryScreenUi()
                    }
                    composable<Routes.EachItemScreenRoute> {
                        val data = it.toRoute<Routes.EachItemScreenRoute>()
                        EachProductScreenUi(data.id,navController = navController)
                    }

                }
            }
        }

    }





}


data class BottomItem(
    val name : String,
    val icon : ImageVector,
    val selectedIcon : ImageVector
)