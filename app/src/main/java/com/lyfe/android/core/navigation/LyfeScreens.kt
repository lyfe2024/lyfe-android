package com.lyfe.android.core.navigation

import androidx.navigation.NamedNavArgument

sealed class LyfeScreens(
	val route: String,
	navArguments: List<NamedNavArgument> = emptyList()
) {
	val name: String = route.appendArguments(navArguments)

	object Home : LyfeScreens("home")

	object Post : LyfeScreens("post")

	object Alarm : LyfeScreens("Alarm")

	object Profile : LyfeScreens("profile")

	object ProfileEdit : LyfeScreens("profileEdit")

	object Login : LyfeScreens("login")
}

private fun String.appendArguments(navArguments: List<NamedNavArgument>): String {
	val mandatoryArguments = navArguments.filter { it.argument.defaultValue == null }
		.takeIf { it.isNotEmpty() }
		?.joinToString(separator = "/", prefix = "/") { "{${it.name}}" }
		.orEmpty()

	val optionalArguments = navArguments.filter { it.argument.defaultValue != null }
		.takeIf { it.isNotEmpty() }
		?.joinToString(separator = "&", prefix = "?") { "${it.name}={${it.name}}" }
		.orEmpty()

	return "$this$mandatoryArguments$optionalArguments"
}