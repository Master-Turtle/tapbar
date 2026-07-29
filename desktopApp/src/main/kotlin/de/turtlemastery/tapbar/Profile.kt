package de.turtlemastery.tapbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.*
import java.awt.Toolkit

abstract class Profile : Presets() {

    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val corners : Dp = 8.dp

    abstract val type : Profiles


    @Composable
    abstract fun create()




}