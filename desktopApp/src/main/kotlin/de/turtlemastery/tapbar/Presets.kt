package de.turtlemastery.tapbar

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

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

        Button(
            shape = shape,
            onClick = onClick,
            modifier = modifier,
            interactionSource = interactionSource,
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (isHovered) {
                    Color(80, 80, 80)
                } else {
                    Color.Transparent
                }
            ),
            elevation = ButtonDefaults.elevation(0.dp)
        ) {
            content()
        }
    }
}