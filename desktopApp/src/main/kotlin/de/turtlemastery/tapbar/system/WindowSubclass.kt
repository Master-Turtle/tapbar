package de.turtlemastery.tapbar.system

import com.sun.jna.Pointer
import com.sun.jna.platform.win32.WinDef.HWND
import com.sun.jna.platform.win32.BaseTSD

object WindowSubclass {
    private val TAPBAR_SUBCLASS_ID =
        Pointer.createConstant(1)
    private lateinit var subclassProc: SubclassProc
    private var installed = false

    fun install(hwnd: HWND) {

        if(installed){
            println("Subclass already installed")
            return
        }

        subclassProc = object : SubclassProc {

            override fun callback(
                hWnd: HWND,
                uMsg: Int,
                wParam: Pointer?,
                lParam: Pointer?,
                uIdSubclass: BaseTSD.ULONG_PTR,
                dwRefData: BaseTSD.ULONG_PTR
            ): Long {

                println("Windows message: $uMsg")

                return ComCtl32.INSTANCE.DefSubclassProc(
                    hWnd,
                    uMsg,
                    wParam,
                    lParam
                )
            }
        }

        println("Installing subclass...")

        installed =
            ComCtl32.INSTANCE.SetWindowSubclass(
                hwnd,
                subclassProc,
                TAPBAR_SUBCLASS_ID,
                null
            )


        println(
            println(
                "Result= $installed error=${Kernel32.INSTANCE.GetLastError()}"
            )
        )
    }


    fun findCanvas(hwnd: HWND): HWND? {

        var result: HWND? = null

        User32.INSTANCE.EnumChildWindows(
            hwnd,
            object : User32.EnumWindowsProc {

                override fun callback(
                    child: HWND,
                    lParam: Pointer?
                ): Boolean {

                    val buffer = CharArray(256)

                    User32.INSTANCE.GetClassNameW(
                        child,
                        buffer,
                        buffer.size
                    )

                    val className =
                        String(buffer).trim('\u0000')


                    if(className == "SunAwtCanvas"){
                        result = child
                        return false
                    }

                    return true
                }
            },
            null
        )

        return result
    }



    fun printWindowTree(hwnd: HWND, level: Int = 0) {

        val indent = " ".repeat(level * 2)

        val buffer = CharArray(256)

        User32.INSTANCE.GetClassNameW(
            hwnd,
            buffer,
            buffer.size
        )

        println(
            "$indent HWND=${hwnd.pointer} class=${String(buffer).trim('\u0000')}"
        )


        User32.INSTANCE.EnumChildWindows(
            hwnd,
            object : User32.EnumWindowsProc {

                override fun callback(
                    child: HWND,
                    lParam: Pointer?
                ): Boolean {

                    printWindowTree(
                        child,
                        level + 1
                    )

                    return true
                }
            },
            null
        )
    }
}

