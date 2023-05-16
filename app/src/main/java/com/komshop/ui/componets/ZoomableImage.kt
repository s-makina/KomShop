package com.komshop.ui.componets

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import com.skydoves.landscapist.coil.CoilImage
import java.lang.Math.PI
import java.lang.Math.cos
import java.lang.Math.sin
import kotlin.math.roundToInt

@Composable
fun ImagePreview(link: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        var angle by remember { mutableStateOf(0f) }
        var zoom by remember { mutableStateOf(1f) }
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        CoilImage(
            imageModel = link,
            contentDescription = "image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .graphicsLayer(
                    scaleX = zoom,
                    scaleY = zoom,
                    rotationZ = angle
                )
                .pointerInput(Unit) {
                    detectTransformGestures(
                        onGesture = { _, pan, gestureZoom, gestureRotate ->
                            angle += gestureRotate
                            zoom *= gestureZoom
                            val x = pan.x * zoom
                            val y = pan.y * zoom
                            val angleRad = angle * PI / 180.0
                            offsetX += (x * cos(angleRad) - y * sin(angleRad)).toFloat()
                            offsetY += (x * sin(angleRad) + y * cos(angleRad)).toFloat()
                        }
                    )
                }
                .fillMaxSize()
        )
    }
}