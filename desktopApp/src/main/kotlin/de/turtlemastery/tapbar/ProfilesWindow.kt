package de.turtlemastery.tapbar

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


class ProfilesWindow : Presets(){

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
                        .padding(end = 3.dp,top = 3.dp, bottom = 3.dp)
                        .background(
                            color = Color(32, 31, 30),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.CenterStart
                ){
                    Row(
                        modifier = Modifier
                            //.fillMaxSize()
                            //.padding(start=3.dp)
                            ,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ){
                        if(BarManager.currentProfile != Profiles.DEFAULT){
                            DefaultButton(
                                modifier = Modifier
                                    .size(width = 36.dp, height = 36.dp),
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
                                    .size(width = 36.dp, height = 36.dp),
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

