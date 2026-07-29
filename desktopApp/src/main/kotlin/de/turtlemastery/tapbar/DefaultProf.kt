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
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Remove
import androidx.compose.ui.Modifier

class DefaultProf :Profile() {
    override val type = Profiles.DEFAULT


    @Composable
    override fun create() {

        var color1 by remember { mutableStateOf(CP.default[0]) }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ){
            if(!BarManager.minimized){
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                        .background(
                            color = Color(32, 31, 30),
                            shape = RoundedCornerShape(corners)
                        ),

                    ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ){
                        DefaultButton(

                            onClick = {
                                BarManager.showPofilesWindow = !BarManager.showPofilesWindow
                            }
                        ){
                            Icon(
                                tint = color1,
                                imageVector = Icons.Default.Home,
                                contentDescription = null,
                            )
                        }


                    }
                }

                Box(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .height(25.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color(32, 31, 30),
                            shape = RoundedCornerShape(corners)
                        ),
                    contentAlignment = Alignment.Center

                ) {
                    DefaultButton(
                        onClick = {
                            BarManager.closeBar()
                        }
                    ){
                        Icon(
                            modifier = Modifier
                                .size(20.dp),
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .height(25.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color(32, 31, 30),
                        shape = RoundedCornerShape(corners)
                    ),
                contentAlignment = Alignment.Center

            ) {
                DefaultButton(
                    onClick = {
                        BarManager.minimized = !BarManager.minimized
                        when (BarManager.minimized) {
                            true -> {
                                BarManager.minimize(0, BarManager.screenSize.height-100,30,30)
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
                        )
                    }else{
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}