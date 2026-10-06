package de.turtlemastery.tapbar.Profs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import de.turtlemastery.tapbar.BarManager
import de.turtlemastery.tapbar.CP
import de.turtlemastery.tapbar.Profile
import de.turtlemastery.tapbar.Profiles

class DefaultProf : Profile() {
    override val type = Profiles.DEFAULT


    @Composable
    override fun create() {

        var color1 by remember { mutableStateOf(CP.default[0]) }
        var color2 by remember { mutableStateOf(CP.default[1]) }
        var color3 by remember { mutableStateOf(CP.default[4]) }
        var color4 by remember { mutableStateOf(CP.default[8]) }

        val contentWidth = with(LocalDensity.current){(BarManager.currentProfile.width).toDp()}-contentPadding
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
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
                            .fillMaxSize(),
                    ){
                        Spacer(modifier = Modifier.height(8.dp))
                        toggleProfilesWindowButton(contentWidth,30.dp,Icons.Default.Home,color1)
                        Spacer(modifier = Modifier.height(8.dp))
                        toggleSettingsWindowButton(contentWidth,30.dp,Icons.Default.Settings,color3)
                        Spacer(modifier = Modifier.height(8.dp))
                        toggleTouchButton(contentWidth,30.dp,Icons.Default.TouchApp,Color.Gray,color4)
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .height(20.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color(32, 31, 30),
                            shape = RoundedCornerShape(corners)
                        ),
                    contentAlignment = Alignment.Center

                ) {
                    closeButton()
                }
            }

            Box(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color(32, 31, 30),
                        shape = RoundedCornerShape(corners)
                    ),
                contentAlignment = Alignment.Center

            ) {
                minimizeButton()
            }
        }
    }
}