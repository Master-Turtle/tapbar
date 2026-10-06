package de.turtlemastery.tapbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

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
import de.turtlemastery.tapbar.PopWindows.ProfilesWindow
import de.turtlemastery.tapbar.PopWindows.SettingsWindow
import java.awt.SystemColor.window
import java.awt.event.WindowEvent
import java.awt.event.WindowFocusListener


val ComposeWindow.hwnd: HWND
    get() = HWND(Pointer.createConstant(windowHandle))

fun main() = application {

    BarManager.taskRunner.startTask(WinTasks.ENABLETOUCH.value)

    if (BarManager.shouldExit) {
        BarManager.taskRunner.startTask(WinTasks.ENABLETOUCH.value)
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

            window.addWindowFocusListener(object : WindowFocusListener {
                override fun windowGainedFocus(e: WindowEvent?) {
                    BarManager.focused = true
                }

                override fun windowLostFocus(e: WindowEvent?) {
                    BarManager.focused = false
                }
            })
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
    if(BarManager.showSettingsWindow){
        val settingsWindow = remember { SettingsWindow() }
        settingsWindow.window()
    }

}





