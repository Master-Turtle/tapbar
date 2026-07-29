package de.turtlemastery.tapbar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.awt.ComposeWindow
import com.sun.jna.platform.win32.WinDef.HWND
import com.sun.jna.platform.win32.WinDef.RECT
import com.sun.jna.platform.win32.User32
import de.turtlemastery.tapbar.system.Dwmapi
import de.turtlemastery.tapbar.system.ABE_LEFT
import de.turtlemastery.tapbar.system.ABM_NEW
import de.turtlemastery.tapbar.system.ABM_QUERYPOS
import de.turtlemastery.tapbar.system.ABM_REMOVE
import de.turtlemastery.tapbar.system.ABM_SETPOS
import de.turtlemastery.tapbar.system.Shell32
import de.turtlemastery.tapbar.system.TapBarDATA
import java.awt.Toolkit

object BarManager {

    lateinit var barWindow: ComposeWindow

    var minimized by mutableStateOf(false)
    var showPofilesWindow by mutableStateOf(false)
    var currentProfile by mutableStateOf(Profiles.DEFAULT)
    var shouldExit by mutableStateOf(false)
    var registered by mutableStateOf(false)


    val data = TapBarDATA()

    val screenSize = Toolkit.getDefaultToolkit().screenSize


    fun closeBar(){
        unapplyBar()
        shouldExit = true
    }

    fun register(hwnd: HWND?) {
        if(!registered){
            data.hWnd = hwnd

            Shell32.INSTANCE.SHAppBarMessage(
                ABM_NEW,
                data
            )
            registered = true
            applyBar()
        }
        else println("allready registerd")
    }

    fun applyBar() {
        if(!registered){
            println("cant appy without registerd")
            return
        }

        val rect = RECT()

        rect.left = 0
        rect.top = 0
        rect.right = currentProfile.width
        rect.bottom = screenSize.height


        data.uEdge = ABE_LEFT
        data.rc = rect

        // Windows fragt den Bereich ab
        Shell32.INSTANCE.SHAppBarMessage(
            ABM_QUERYPOS,
            data
        )
        // Position wirklich setzen
        Shell32.INSTANCE.SHAppBarMessage(
            ABM_SETPOS,
            data
        )

        setWindowPos(data.rc)
    }

    fun unapplyBar(){
        Shell32.INSTANCE.SHAppBarMessage(
            ABM_REMOVE,
            data
        )
        registered = false
    }

    fun setWindowPos(rect: RECT){
        barWindow.setBounds(
            rect.left,
            rect.top,
            rect.right - rect.left,
            rect.bottom - rect.top
        )
    }

    fun minimize(x:Int,y:Int,width:Int,height:Int){
        unapplyBar()
        barWindow.setBounds(x,y,width,height)
        roundCorners(true)
    }

    fun maximize(){
        register(data.hWnd)
        roundCorners(false)
    }


    fun roundCorners(rounded: Boolean){
        val cornerPreference = intArrayOf(2)
        // 0 = default
        // 1 = no round corners
        // 2 = round corners
        // 3 = large round corners

        if(rounded){
            Dwmapi.INSTANCE.DwmSetWindowAttribute(
                data.hWnd,
                Dwmapi.DWMWA_WINDOW_CORNER_PREFERENCE,
                cornerPreference,
                4)
        }else{
            Dwmapi.INSTANCE.DwmSetWindowAttribute(
                data.hWnd,
                Dwmapi.DWMWA_WINDOW_CORNER_PREFERENCE,
                intArrayOf(0),
                4)
        }
    }


}