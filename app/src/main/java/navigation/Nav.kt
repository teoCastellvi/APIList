package navigation

sealed class Routes(val route: String) {
    object Pantalla1:Routes("pantalla1")
    object Detail:Routes("Detail")
    object Favourites:Routes("Favourites")
}
