package com.src.bottomnavigationexample

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.ref.WeakReference

fun BottomNavigationView.setupWithNavController(navController: NavController) {

    setOnNavigationItemSelectedListener { item ->
        val builder = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.animator.nav_default_enter_anim)
            .setExitAnim(R.animator.nav_default_exit_anim)
            .setPopEnterAnim(R.animator.nav_default_pop_enter_anim)
            .setPopExitAnim(R.animator.nav_default_pop_exit_anim)

        val options = builder.build()
        try {
            if (item.itemId != navController.currentDestination?.id) {
                navController.navigate(item.itemId, null, options)
            }
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    val weakReference = WeakReference<BottomNavigationView>(this)
    navController.addOnDestinationChangedListener(
        object : NavController.OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination, arguments: Bundle?
            ) {
                val view = weakReference.get()
                if (view == null) {
                    navController.removeOnDestinationChangedListener(this)
                    return
                }
                val menu = view.menu
                var h = 0
                val size = menu.size()
                while (h < size) {
                    val item = menu.getItem(h)
                    if (destination.id == item.itemId) {
                        item.isChecked = true
                    }
                    h++
                }
            }
        })
}
