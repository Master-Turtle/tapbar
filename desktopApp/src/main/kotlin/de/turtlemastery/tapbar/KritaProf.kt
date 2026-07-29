package de.turtlemastery.tapbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class KritaProf :Profile(){

    override val type = Profiles.KRITA

    @Composable
    override fun create() {
        Column {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                        .background(
                            color = Color(32, 31, 30),
                            shape = RoundedCornerShape(corners)
                        ),

                    ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

                    }
                }

        }
    }
}