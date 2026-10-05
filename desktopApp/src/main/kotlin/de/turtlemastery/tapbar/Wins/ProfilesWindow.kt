package de.turtlemastery.tapbar.Wins

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import de.turtlemastery.tapbar.BarManager
import de.turtlemastery.tapbar.CP
import de.turtlemastery.tapbar.Presets
import de.turtlemastery.tapbar.Profiles


class ProfilesWindow : Presets(){

    @Composable
    fun window(){
        val density = LocalDensity.current
        Window(
            onCloseRequest = { BarManager.showPofilesWindow = false },
            title = "ProfilesWindow",
            undecorated = true,
            resizable = false,
            alwaysOnTop = false,
            transparent = true,
            state = rememberWindowState(
                width = 100.dp,
                height = 42.dp,
                position = WindowPosition(x = with(density){ BarManager.currentProfile.width.toDp()},y = 6.dp)
            )
        ){
            content()
        }
    }

    @Composable
    fun content(){
        var color1 by remember { mutableStateOf(CP.default[0]) }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.Black,
                        shape = RoundedCornerShape(
                            topEnd = 8.dp,
                            bottomEnd = 8.dp
                        )
                    ),
            ){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(3.dp)
                        .background(
                            color = Color(32, 31, 30),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.CenterStart
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start=3.dp)
                            ,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ){
                        if(BarManager.currentProfile != Profiles.DEFAULT){
                            DefaultButton(
                                modifier = Modifier
                                    .width(36.dp)
                                    .fillMaxHeight(),

                                onClick = {
                                    BarManager.currentProfile = Profiles.DEFAULT
                                    BarManager.showPofilesWindow = false
                                    BarManager.applyBar()
                                }
                            ){
                                Icon(
                                    tint = color1,
                                    imageVector = Icons.Default.Home,
                                    contentDescription = null,
                                )
                            }
                        }
                        if(BarManager.currentProfile != Profiles.KRITA){
                            DefaultButton(
                                modifier = Modifier
                                    .width(36.dp)
                                    .fillMaxHeight(),
                                onClick = {
                                    BarManager.currentProfile = Profiles.KRITA
                                    BarManager.showPofilesWindow = false
                                    BarManager.applyBar()
                                }
                            ){
                                Icon(
                                    tint = color1,
                                    imageVector = Icons.Default.Brush,
                                    contentDescription = null,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

