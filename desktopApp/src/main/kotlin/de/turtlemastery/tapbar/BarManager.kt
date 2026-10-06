package de.turtlemastery.tapbar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.sun.jna.WString
import com.sun.jna.platform.win32.WinDef.HWND
import com.sun.jna.platform.win32.WinDef.RECT
import de.turtlemastery.tapbar.system.Dwmapi
import de.turtlemastery.tapbar.system.ABE_LEFT
import de.turtlemastery.tapbar.system.ABM_GETTASKBARPOS
import de.turtlemastery.tapbar.system.ABM_NEW
import de.turtlemastery.tapbar.system.ABM_QUERYPOS
import de.turtlemastery.tapbar.system.ABM_REMOVE
import de.turtlemastery.tapbar.system.ABM_SETPOS
import de.turtlemastery.tapbar.system.SPI_GETWORKAREA
import de.turtlemastery.tapbar.system.Shell32
import de.turtlemastery.tapbar.system.TapBarDATA
import de.turtlemastery.tapbar.system.User32
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.awt.Toolkit



object BarManager {
    lateinit var barWindow: ComposeWindow

    var minimized by mutableStateOf(false)
    var showPofilesWindow by mutableStateOf(false)
    var showSettingsWindow by mutableStateOf(false)
    var currentProfile by mutableStateOf(Profiles.DEFAULT)
    var shouldExit by mutableStateOf(false)
    var registered = false
    var focused by mutableStateOf(true)


    var touchScreen by mutableStateOf(true)

    private var monitorThread: Thread? = null
    private var monitoring = false

    val APPBAR_CALLBACK =
        User32.INSTANCE.RegisterWindowMessageW(
            WString("TapBar_AppBar_Callback")
        )

    val data = TapBarDATA()
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val taskRunner = WinTaskRunner(CoroutineScope(SupervisorJob() + Dispatchers.Default))


    fun closeBar(){
        unapplyBar()
        shouldExit = true
    }

    fun attach(window: ComposeWindow) {
        barWindow = window
        register(window.hwnd)
        startBarMonitor()
    }

    fun register(hwnd: HWND?) {

        if(!registered){
            data.hWnd = hwnd
            data.uCallbackMessage = APPBAR_CALLBACK

            //println("AppBar callback id = $APPBAR_CALLBACK")

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
        rect.bottom = getWorkArea().bottom


        data.uEdge = ABE_LEFT
        data.rc = rect

        //println("Before QUERY: ${data.rc.left}, ${data.rc.top}, ${data.rc.right}, ${data.rc.bottom}")
        // Windows fragt den Bereich ab
        Shell32.INSTANCE.SHAppBarMessage(
            ABM_QUERYPOS,
            data
        )
        //println("After QUERY: ${data.rc.left}, ${data.rc.top}, ${data.rc.right}, ${data.rc.bottom}")
        // Position wirklich setzen
        Shell32.INSTANCE.SHAppBarMessage(
            ABM_SETPOS,
            data
        )
        //println("After SETPOS: ${data.rc.left}, ${data.rc.top}, ${data.rc.right}, ${data.rc.bottom}")
        data.rc.bottom = getWorkArea().bottom

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
            0x0040
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

    fun getWorkArea(): RECT {
        val rect = RECT()
        User32.INSTANCE.SystemParametersInfoW(SPI_GETWORKAREA, 0, rect, 0)
        return rect
    }

    fun startBarMonitor() {
        if (monitoring) return
        monitoring = true

        monitorThread = Thread {
            while (monitoring) {
                val rect = RECT()
                User32.INSTANCE.GetWindowRect(data.hWnd, rect)
                if (rect.left != data.rc.left ||
                    rect.top != data.rc.top ||
                    rect.right != data.rc.right ||
                    rect.bottom != data.rc.bottom){
                    if(!minimized){
                        println("changing window position due to missalignment")
                        applyBar()
                    }
                }
                Thread.sleep(200)
            }
        }
        monitorThread!!.isDaemon = true
        monitorThread!!.start()
    }

    fun stopBarMonitor() {
        monitoring = false
        monitorThread?.interrupt()
    }

}