package de.turtlemastery.tapbar.Profiles

import androidx.compose.runtime.Composable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.*
import de.turtlemastery.tapbar.Presets
import java.awt.Toolkit

abstract class Profile : Presets() {

    val screenSize = Toolkit.getDefaultToolkit().screenSize

    abstract val type : Profiles


    @Composable
    abstract fun create()




}