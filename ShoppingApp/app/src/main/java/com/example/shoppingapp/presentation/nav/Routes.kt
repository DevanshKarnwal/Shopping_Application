package com.example.shoppingapp.presentation.nav

import kotlinx.serialization.Serializable


sealed class SubNavigation{

    @Serializable
    object LoginSignUpScreenRoutes : SubNavigation()

    @Serializable
    object HomeScreenRoutes : SubNavigation()


}



sealed class Routes {
    @Serializable
    object LoginScreenRoute : Routes()

    @Serializable
    object SignUpScreenRoute : Routes()

    @Serializable
    object HomeScreenRoute : Routes()

    @Serializable
    object NotificationScreenRoute : Routes()

    @Serializable
    object ProfileScreenRoute : Routes()

    @Serializable
    object WishListScreenRoute : Routes()

    @Serializable
    object CartScreenRoute : Routes()

    @Serializable
    object CheckOutScreenRoute : Routes()

    @Serializable
    data class SeeAllProductsScreenRoute(val categoryName: String) : Routes()

    @Serializable
    object SeeAllCategoriesScreenRoute : Routes()

    @Serializable
    object EachItemScreenRoute : Routes()
}