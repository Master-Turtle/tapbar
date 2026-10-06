package de.turtlemastery.tapbar.Profs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import de.turtlemastery.tapbar.BarManager
import de.turtlemastery.tapbar.CP
import de.turtlemastery.tapbar.Profile
import de.turtlemastery.tapbar.Profiles

class KritaProf : Profile(){

    override val type = Profiles.KRITA

    @Composable
    override fun create() {
        val contentWidth = with(LocalDensity.current){(BarManager.currentProfile.width).toDp()}-contentPadding
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
            )
            {
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
                    ) {
                        Row(
                            modifier = Modifier
                                .height(40.dp)
                                .fillMaxWidth()
                        ){

                            toggleProfilesWindowButton(contentWidth,30.dp,Icons.Default.Brush, CP.default[0])

                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .height(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ){
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .width(contentWidth/2-(4.dp))
                            .background(
                                color = Color(32, 31, 30),
                                shape = RoundedCornerShape(corners)
                            ),
                        contentAlignment = Alignment.Center

                    ) {
                        minimizeButton()
                    }
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .width(contentWidth/2-(2.dp))
                            .background(
                                color = Color(32, 31, 30),
                                shape = RoundedCornerShape(corners)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        closeButton()
                    }
                }
        }
    }
}