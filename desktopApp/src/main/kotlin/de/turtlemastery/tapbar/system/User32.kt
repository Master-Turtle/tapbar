package de.turtlemastery.tapbar.system

import com.sun.jna.Native
import com.sun.jna.WString
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.FunctionMapper
import com.sun.jna.win32.StdCallLibrary
import com.sun.jna.win32.W32APIOptions
import com.sun.jna.Function
import com.sun.jna.Callback
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.WinDef.RECT

interface User32 : StdCallLibrary {


    companion object {

        val INSTANCE =
            Native.load(
                "user32",
                User32::class.java
            )
    }


    fun getSystemMetrics(
        index: Int
    ): Int


    fun SetWindowPos(
        hWnd: WinDef.HWND?,
        hWndInsertAfter: WinDef.HWND?,
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        flags: Int
    ): Boolean

    fun GetWindowRect(
        hWnd: WinDef.HWND?,
        rect: WinDef.RECT
    ): Boolean

    fun RegisterWindowMessageW(
        message: WString
    ): Int

    fun IsWindow(
        hWnd: WinDef.HWND
    ): Boolean

    fun GetClassNameW(
        hWnd: WinDef.HWND,
        buffer: CharArray,
        maxCount: Int
    ): Int

    fun EnumChildWindows(
        hWndParent: WinDef.HWND,
        lpEnumFunc: EnumWindowsProc,
        lParam: Pointer?
    ): Boolean

    interface EnumWindowsProc : Callback {

        fun callback(
            hwnd: WinDef.HWND,
            lParam: Pointer?
        ): Boolean
    }

    fun GetWindowLongPtrW(
        hWnd: WinDef.HWND,
        nIndex: Int
    ): Pointer

    fun SetWindowLongPtrW(
        hWnd: WinDef.HWND,
        nIndex: Int,
        newLong: Pointer
    ): Pointer

    fun CallWindowProcW(
        prevWndFunc: Pointer,
        hWnd: WinDef.HWND,
        msg: Int,
        wParam: Pointer?,
        lParam: Pointer?
    ): Long

    fun SystemParametersInfoW(
        action: Int,
        param: Int,
        rect: RECT,
        winIni: Int
    ): Boolean

}