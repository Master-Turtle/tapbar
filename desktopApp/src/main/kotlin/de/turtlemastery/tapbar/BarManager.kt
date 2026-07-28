package de.turtlemastery.tapbar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sun.jna.platform.win32.WinDef.HWND
import com.sun.jna.platform.win32.WinDef.RECT
import com.sun.jna.platform.win32.User32
import de.turtlemastery.tapbar.Profiles.Profiles
import de.turtlemastery.tapbar.Windows.ABE_LEFT
import de.turtlemastery.tapbar.Windows.ABM_NEW
import de.turtlemastery.tapbar.Windows.ABM_QUERYPOS
import de.turtlemastery.tapbar.Windows.ABM_REMOVE
import de.turtlemastery.tapbar.Windows.ABM_SETPOS
import de.turtlemastery.tapbar.Windows.Dwmapi
import de.turtlemastery.tapbar.Windows.Shell32
import de.turtlemastery.tapbar.Windows.TapBarDATA

object BarManager {

    var showPofilesWindow by mutableStateOf(false)
    var currentProfile : Profiles  by mutableStateOf(Profiles.DEFAULT)
    var shouldExit by mutableStateOf(false)
    var registered by mutableStateOf(false)






    val data = TapBarDATA()
    var WIDTH = 42


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
        val height =
            User32.INSTANCE.GetSystemMetrics(1)

        val rect = RECT()

        rect.left = 0
        rect.top = 0
        rect.right = WIDTH
        rect.bottom = height


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
        User32.INSTANCE.SetWindowPos(
            data.hWnd,
            null,
            rect.left,
            rect.top,
            rect.right - rect.left,
            rect.bottom - rect.top,
            0x0010 or 0x0004 or 0x0020
        )
    }

    fun changeApplyedWidth(newWidth: Int){
        WIDTH = newWidth
        applyBar()
    }

    fun minimize(x:Int,y:Int,width:Int,height:Int){
        unapplyBar()
        User32.INSTANCE.SetWindowPos(
            data.hWnd,
            null,
            x,
            y,
            width,
            height,
            0x0010 or 0x0004
        )
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