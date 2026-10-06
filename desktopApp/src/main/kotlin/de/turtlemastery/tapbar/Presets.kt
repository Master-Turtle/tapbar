package de.turtlemastery.tapbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import javax.swing.Icon

open class Presets {

    @Composable
    fun DefaultButton(
        shape: Shape = RoundedCornerShape(8.dp),
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit,
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isHovered by interactionSource.collectIsHoveredAsState()

        Box(
            modifier = modifier
                .clip(RoundedCornerShape(8.dp))
                .background(
                    if (isHovered && BarManager.focused) Color(80,80,80)
                    else Color.Transparent
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                )
                {
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }


    @Composable
    fun toggleProfilesWindowButton(width: Dp, height:Dp, icon : ImageVector, color1 : Color) {
        DefaultButton(
            modifier = Modifier
                .width(width)
                .height(height),
            onClick = {
                BarManager.showPofilesWindow = !BarManager.showPofilesWindow
            }
        ){
            Icon(
                tint = color1,
                imageVector = icon,
                contentDescription = null,
            )
        }
    }

    @Composable
    fun toggleSettingsWindowButton(width: Dp, height:Dp, icon : ImageVector, color1 : Color) {
        DefaultButton(
            modifier = Modifier
                .width(width)
                .height(height),
            onClick = {
                BarManager.showSettingsWindow = !BarManager.showSettingsWindow
            }
        ){
            Icon(
                tint = color1,
                imageVector = icon,
                contentDescription = null,
            )
        }
    }
    @Composable
    fun closeButton(){
        DefaultButton(
            modifier = Modifier
                .fillMaxSize(),
            onClick = {
                BarManager.closeBar()
            }
        ){
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
    @Composable
    fun minimizeButton(){
        DefaultButton(
            modifier = Modifier
                .fillMaxSize(),
            onClick = {
                BarManager.minimized = !BarManager.minimized
                when (BarManager.minimized) {
                    true -> {
                        BarManager.minimize(0, BarManager.screenSize.height-100,28,28)
                        BarManager.showPofilesWindow = false
                    }
                    false -> BarManager.maximize()
                }
            }
        ) {
            if(BarManager.minimized){
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }else{
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }

    @Composable
    fun toggleTouchButton(width: Dp, height:Dp, icon : ImageVector, color1 : Color, color2 : Color) {
        DefaultButton(
            modifier = Modifier
                .width(width)
                .height(height),
            onClick = {
                if(BarManager.touchScreen){
                    BarManager.touchScreen = false
                    BarManager.taskRunner.startTask(WinTasks.DISABLETOUCH.value)
                }
                else{
                    BarManager.touchScreen = true
                    BarManager.taskRunner.startTask(WinTasks.ENABLETOUCH.value)
                }
            }
        ){
            Icon(
                tint =  if (BarManager.touchScreen) color2 else color1,
                imageVector = icon,
                contentDescription = null,
            )
        }
    }




}