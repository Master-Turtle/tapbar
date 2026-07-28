package de.turtlemastery.tapbar.Profiles

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

class KritaProf :Profile(){

    override val type = Profiles.KRITA

    @Composable
    override fun create() {
        Column {
            Text("Krita Profil")
            Button(onClick = {}) {
                Text("Button")
            }
        }
    }
}