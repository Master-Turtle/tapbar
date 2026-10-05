package de.turtlemastery.tapbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.*
import java.awt.Toolkit

abstract class Profile : Presets() {


    val corners : Dp = 8.dp
    val contentPadding : Dp = 4.dp

    abstract val type : Profiles


    @Composable
    abstract fun create()




}