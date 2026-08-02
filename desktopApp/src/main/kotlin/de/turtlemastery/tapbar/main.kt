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
import de.turtlemastery.tapbar.system.ComCtl32
import de.turtlemastery.tapbar.system.DLLVERSIONINFO
import de.turtlemastery.tapbar.system.User32
import de.turtlemastery.tapbar.system.WindowSubclass

// test

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
        val hwnd = remember(this.window) {

            val composeWindow =
                this.window as ComposeWindow

            HWND(
                Pointer.createConstant(
                    composeWindow.windowHandle
                )
            )
        }
        LaunchedEffect(hwnd) {

            println("Parent:")
            WindowSubclass.printWindowTree(hwnd)

            println("HWND valid: ${hwnd.pointer != null}")

            val buffer = CharArray(256)

            User32.INSTANCE.GetClassNameW(
                hwnd,
                buffer,
                buffer.size
            )
            //WindowSubclass.install(hwnd)

            BarManager.barWindow = window


            BarManager.register(hwnd)
            BarManager.startBarMonitor()
        }

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
                    height = 42.dp,
                    position = WindowPosition(x = (BarManager.currentProfile.width * 0.75).dp,y = 6.dp)
                )
            ){
                val profilesWindow = remember { ProfilesWindow()}
                profilesWindow.content()
            }
        }


        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black)
        ){
            val profile = remember(BarManager.currentProfile) {
                when (BarManager.currentProfile) {
                    Profiles.DEFAULT -> DefaultProf()
                    Profiles.KRITA -> KritaProf()
                }
            }
            profile.create()
        }
    }


}





