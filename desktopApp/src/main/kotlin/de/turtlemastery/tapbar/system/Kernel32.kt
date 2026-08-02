package de.turtlemastery.tapbar.system
import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.win32.StdCallLibrary
interface Kernel32 : StdCallLibrary {

    companion object {

        val INSTANCE =
            Native.load(
                "kernel32",
                Kernel32::class.java
            )
    }


    fun GetLastError(): Int
}