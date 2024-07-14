package com.lyfe.android.core.navigation

import androidx.navigation.NamedNavArgument

sealed class LyfeScreens(
	val route: String,
	navArguments: List<NamedNavArgument> = emptyList()
) {
	val name: String = route.appendArguments(navArguments)

	object Home : LyfeScreens("home")

	object Feed : LyfeScreens("feed")

	// 나중에 id값으로 변경 예정
	object FeedDetail : LyfeScreens("feed/detail")

	object NotificationList : LyfeScreens("Alarm")

	object Profile : LyfeScreens("profile")

	object CreatePhotoPost : LyfeScreens("post/photo/create")

	object CreateTextPost : LyfeScreens("post/text/create")

	object SelectAlbum : LyfeScreens("album/select")

	object ProfileEdit : LyfeScreens("profileEdit")

	object Login : LyfeScreens("login")

	object CreateNickname : LyfeScreens("nickname/create")

	object SignUpTerms : LyfeScreens("terms/signup")

	object ServiceTerms : LyfeScreens("terms/service")

	object PersonalInfoTermsScreen : LyfeScreens("terms/personal-info")

	object SignUpComplete : LyfeScreens("signup/complete")

	object Setting : LyfeScreens("setting")

	object Feedback : LyfeScreens("feedback")
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