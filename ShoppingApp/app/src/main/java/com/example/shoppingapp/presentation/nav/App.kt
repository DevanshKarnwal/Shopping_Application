package com.example.shoppingapp.presentation.nav

import GetAllProductsScreenUi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.shoppingapp.presentation.screens.AllCategoryScreenUi
import com.example.shoppingapp.presentation.screens.CartScreenUi
import com.example.shoppingapp.presentation.screens.CheckOutScreenUi
import com.example.shoppingapp.presentation.screens.EachItemScreenUi
import com.example.shoppingapp.presentation.screens.HomeScreenUi
import com.example.shoppingapp.presentation.screens.LogInScreen
import com.example.shoppingapp.presentation.screens.NotificationScreenUi
import com.example.shoppingapp.presentation.screens.ProfileScreenUi
import com.example.shoppingapp.presentation.screens.SignUpScreen
import com.example.shoppingapp.presentation.screens.WishListScreenUi
import com.google.firebase.auth.FirebaseAuth

@Composable
fun App(
    firebaseAuth: FirebaseAuth
) {
    val navController = rememberNavController()
    val selectedItem = remember { mutableIntStateOf(0) }
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


//    Scaffold(
//        modifier = Modifier.fillMaxSize(),
//        bottomBar = {
//
//        }
//    ) {
//
//    }




    NavHost(
        navController = navController,
        startDestination = startScreen
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
                EachItemScreenUi()
            }

        }
    }
}


data class BottomItem(
    val name : String,
    val icon : ImageVector,
    val selectedIcon : ImageVector
)