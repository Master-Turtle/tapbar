package de.turtlemastery.tapbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.ui.Modifier
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.WinDef.HWND
import de.turtlemastery.tapbar.Profiles.DefaultProf
import de.turtlemastery.tapbar.Profiles.KritaProf
import de.turtlemastery.tapbar.Profiles.Profiles

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
        undecorated = true,
        resizable = false,
        alwaysOnTop = true,
        transparent = true,
    ){
        if(BarManager.showPofilesWindow){
            Window(
                onCloseRequest = { BarManager.showPofilesWindow = false },
                title = "ProfilesWindow",
                undecorated = true,
                resizable = false,
                alwaysOnTop = false,
                transparent = true,
                state = rememberWindowState(
                    width = 100.dp,
                    height = 50.dp,
                    position = WindowPosition.Aligned(Alignment.TopStart)
                )
            ){
                val profilesWindow = remember { ProfilesWindow()}
                profilesWindow.content()
            }
        }
        val hwnd = remember(this.window) {

            val composeWindow =
                this.window as ComposeWindow

            HWND(
                Pointer(
                    composeWindow.windowHandle
                )
            )
        }
        LaunchedEffect(Unit) {
            BarManager.register(hwnd)
        }
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black)
        ){
            if(BarManager.registered){
                val profile = remember(BarManager.currentProfile) {
                    when (BarManager.currentProfile) {
                        Profiles.DEFAULT -> DefaultProf()
                        Profiles.KRITA -> KritaProf()
                    }
                }
                BarManager.changeApplyedWidth(BarManager.currentProfile.width)
                profile.create()
            }
        }
    }
}





