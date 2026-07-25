package de.turtlemastery.tapbar

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Pointer


interface Shell32 : Library {

    companion object {

        val INSTANCE =
            Native.load(
                "shell32",
                Shell32::class.java
            )
    }


    fun SHAppBarMessage(
        dwMessage: Int,
        pData: TapBarDATA
    ): Pointer
}
