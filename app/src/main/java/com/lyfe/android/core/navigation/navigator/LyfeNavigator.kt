package com.lyfe.android.core.navigation.navigator

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.lyfe.android.core.navigation.NavigationCommand
import com.lyfe.android.core.navigation.NavigationCommand.NavigateToRoute
import com.lyfe.android.core.navigation.NavigationCommand.NavigateUp
import com.lyfe.android.core.navigation.NavigationCommand.NavigateUpWithResult
import com.lyfe.android.core.navigation.NavigationCommand.PopUpToRoute
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onSubscription

abstract class LyfeNavigator : Navigator() {

	// 일반적인 navigate
	abstract fun navigate(route: String, optionBuilder: (NavOptionsBuilder.() -> Unit)? = null)

	// 결과값을 가지고 돌아가야하는 경우
	abstract fun <T> navigateBackWithResult(key: String, result: T, route: String?)

	// 일반적인 popup
	abstract fun popUpTo(route: String, inclusive: Boolean)

	// Single Instance 형태로 동작
	abstract fun navigateAndroidClearBackStack(route: String)

	suspend fun handleNavigationCommands(navController: NavController) {
		navigationCommands
			.onSubscription { this@LyfeNavigator.navControllerFlow.value = navController }
			.onCompletion { this@LyfeNavigator.navControllerFlow.value = null }
			.collect { navController.handleLyfeNavigationCommand(it) }
	}

	private fun NavController.handleLyfeNavigationCommand(navigationCommand: NavigationCommand) {
		when (navigationCommand) {
			is NavigateToRoute -> {
				navigate(navigationCommand.route, navigationCommand.options)
			}

			is NavigateUp -> {
				navigateUp()
			}

			is PopUpToRoute -> popBackStack(
				navigationCommand.route,
				navigationCommand.inclusive
			)

			is NavigateUpWithResult<*> -> {
				navUpWithResult(navigationCommand)
			}
		}
	}

	private fun NavController.navUpWithResult(
		navigationCommand: NavigateUpWithResult<*>
	) {
		val backStackEntry =
			navigationCommand.route?.let { getBackStackEntry(it) }
				?: previousBackStackEntry
		backStackEntry?.savedStateHandle?.set(
			navigationCommand.key,
			navigationCommand.result
		)

		navigationCommand.route?.let {
			popBackStack(it, false)
		} ?: run {
			navigateUp()
		}
	}
}