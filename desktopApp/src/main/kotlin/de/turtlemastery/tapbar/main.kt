package de.turtlemastery.tapbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.WinDef.HWND
import de.turtlemastery.tapbar.Profs.DefaultProf
import de.turtlemastery.tapbar.Profs.KritaProf
import de.turtlemastery.tapbar.Wins.ProfilesWindow

val ComposeWindow.hwnd: HWND
    get() = HWND(Pointer.createConstant(windowHandle))

fun main() = application {

    if (BarManager.shouldExit) {
        LaunchedEffect(Unit) {
            Thread {
                Thread.sleep(2000)
                Runtime.getRuntime().halt(0)
            }.apply { isDaemon = true }.start()
            exitApplication()
        }
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "tapbar",
        resizable = false,
        undecorated = true,
        alwaysOnTop = true,
        transparent = true,
    ){
        LaunchedEffect(window) {
            BarManager.attach(window)
        }
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black),
        ){
            val profile = remember(BarManager.currentProfile) {
                when (BarManager.currentProfile) {
                    Profiles.DEFAULT -> DefaultProf()
                    Profiles.KRITA -> KritaProf()
                }
            }
            profile.create()
        }

        showWindows()

    }
}

@Composable
private fun showWindows(){
    if(BarManager.showPofilesWindow){
        val profilesWindow = remember { ProfilesWindow() }
        profilesWindow.window()
    }
}





