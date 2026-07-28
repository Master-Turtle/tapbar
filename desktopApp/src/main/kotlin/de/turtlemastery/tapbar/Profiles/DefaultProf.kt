package de.turtlemastery.tapbar.Profiles

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
import de.turtlemastery.tapbar.BarManager
import de.turtlemastery.tapbar.CP
import kotlin.system.exitProcess

/*
DefaultButton(
    onClick = {
        }
    ){

    }
*/

class DefaultProf :Profile() {
    override val type = Profiles.DEFAULT

    val corners : Dp = 8.dp

    @Composable
    override fun create() {

        var minimize by remember { mutableStateOf(false) }

        var color1 by remember { mutableStateOf(CP.default[0]) }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ){
            if(!minimize){
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
                        minimize = !minimize
                        when (minimize) {
                            true -> BarManager.minimize(0, screenSize.height-100,30,30)
                            false -> BarManager.maximize()

                        }
                    }
                ) {
                    if(minimize){
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