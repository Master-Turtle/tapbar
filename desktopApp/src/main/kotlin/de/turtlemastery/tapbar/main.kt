package de.turtlemastery.tapbar

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.runtime.remember
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.WinDef.HWND

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "tapbar",
        undecorated = true,
        resizable = false,
        alwaysOnTop = true,
    ){
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
    }
}


