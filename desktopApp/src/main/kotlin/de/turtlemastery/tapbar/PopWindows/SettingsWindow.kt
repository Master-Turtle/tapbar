package de.turtlemastery.tapbar.PopWindows

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import de.turtlemastery.tapbar.BarManager
import de.turtlemastery.tapbar.Presets
import de.turtlemastery.tapbar.Profiles
import java.awt.event.WindowEvent
import java.awt.event.WindowFocusListener

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape


private enum class SettingsTab(val title: String) {
    GENERAL("General"),
    TEST("Test"),
}
class SettingsWindow : Presets() {
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
                width = 300.dp,
                height = 500.dp,
                position = WindowPosition(x = with(density){ BarManager.currentProfile.width.toDp()},y = 50.dp)
            )
        ){
            LaunchedEffect(window) {
                window.addWindowFocusListener(object : WindowFocusListener {
                    override fun windowGainedFocus(e: WindowEvent?) {}
                    override fun windowLostFocus(e: WindowEvent?) {
                        BarManager.showSettingsWindow = false;
                    }
                })
            }
            content()
        }
    }

    @Composable
    fun content() {
        var selectedTab by remember { mutableStateOf(SettingsTab.GENERAL) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.Black,
                    shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                )
        ) {
            TabBar(
                selected = selectedTab,
                onSelect = { selectedTab = it }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 3.dp, end = 3.dp, bottom = 3.dp)
                    .background(
                        color = Color(32, 31, 30),
                        shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                    ),
            ) {
                when (selectedTab) {
                    SettingsTab.GENERAL -> GeneralSettings()
                    SettingsTab.TEST -> Text("test", color = Color.White)
                }
            }
        }
    }

    @Composable
    private fun GeneralSettings(){

    }

    @Composable
    private fun TabBar(
        selected: SettingsTab,
        onSelect: (SettingsTab) -> Unit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(Color.Black, RoundedCornerShape(topEnd = 8.dp)),
            horizontalArrangement = Arrangement.spacedBy(-8.dp * 2),
            verticalAlignment = Alignment.Bottom

        ) {
            SettingsTab.entries.forEach { tab ->
                val isSelected = tab == selected
                Box(
                    modifier = Modifier
                        .clickable { onSelect(tab) }
                        .background(
                            if (isSelected) Color(32, 31, 30) else Color.Transparent,
                            ChromeTabShape(topRadius = 8.dp, flare = 8.dp)
                        )
                        .padding(horizontal = 14.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab.title,
                        color = if (isSelected) Color.White else Color.Gray,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

class ChromeTabShape(
    private val topRadius: Dp = 8.dp,
    private val flare: Dp = 8.dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val r = with(density) { topRadius.toPx() }
        val f = with(density) { flare.toPx() }
        val w = size.width
        val h = size.height

        val path = Path().apply {
            moveTo(0f, h)
            // unten links: Schwung nach außen
            quadraticTo(f, h, f, h - f)
            lineTo(f, r)
            // oben links
            quadraticTo(f, 0f, f + r, 0f)
            lineTo(w - f - r, 0f)
            // oben rechts
            quadraticTo(w - f, 0f, w - f, r)
            lineTo(w - f, h - f)
            // unten rechts: Schwung nach außen
            quadraticTo(w - f, h, w, h)
            close()
        }
        return Outline.Generic(path)
    }
}
