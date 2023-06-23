package com.compose.practical.ui.mutliSelectGrid

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.onLongClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.toIntRect
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive


@Preview(showBackground = false)
@Composable
fun PhotoGridPreview() {
    MaterialTheme {
        PhotoGrid()
    }

}

fun Modifier.photoGridDragHandler(
    lazyGridState: LazyGridState,
    selectedIds: MutableState<Set<Int>>,
    autoScrollSpeed: MutableState<Float>,
    autoScrollThreshold: Float
) = pointerInput(Unit) {
    var initialKey: Int? = null
    var currentKey: Int? = null
    detectDragGesturesAfterLongPress(
        onDragStart = { offset ->
            lazyGridState.gridItemKeyAtPosition(offset)?.let { key ->
                if (!selectedIds.value.contains(key)) {
                    initialKey = key
                    currentKey = key
                    selectedIds.value = selectedIds.value + key
                }

            }
        },
        onDragCancel = { initialKey = null; autoScrollSpeed.value = 0f },
        onDragEnd = { initialKey = null ; autoScrollSpeed.value = 0f},
        onDrag = { change, _ ->
            if (initialKey != null) {

                val distFromBottom =
                    lazyGridState.layoutInfo.viewportSize.height - change.position.y
                val distFromTop = change.position.y
                autoScrollSpeed.value = when {
                    distFromBottom < autoScrollThreshold -> autoScrollThreshold - distFromBottom
                    distFromTop < autoScrollThreshold -> -(autoScrollThreshold - distFromTop)
                    else -> 0f
                }

                lazyGridState.gridItemKeyAtPosition(change.position)?.let { key ->

                    if (currentKey != key) {

                        selectedIds.value = selectedIds.value
                            .minus(initialKey!!..currentKey!!)
                            .minus(currentKey!!..initialKey!!)
                            .plus(initialKey!!..key)
                            .plus(key..initialKey!!)
                        currentKey = key
                    }

                }
            }


        }
    )
}

fun LazyGridState.gridItemKeyAtPosition(hitPoint: Offset): Int? =
    layoutInfo.visibleItemsInfo.find { itemInfo ->
        itemInfo.size.toIntRect().contains(hitPoint.round() - itemInfo.offset)
    }?.key as? Int

@Composable
private fun PhotoGrid() {
    val photos by rememberSaveable { mutableStateOf(List(100) { it }) }
    val selectedIds = rememberSaveable {
        mutableStateOf(emptySet<Int>())
    }
    val inSelectionMode by remember {
        derivedStateOf { selectedIds.value.isNotEmpty() }
    }

    val state = rememberLazyGridState()

    val autoScrollSpeed = remember { mutableStateOf(0f) }
    // Executing the scroll
    LaunchedEffect(autoScrollSpeed.value) {
        if (autoScrollSpeed.value != 0f) {
            while (isActive) {
                state.scrollBy(autoScrollSpeed.value)
                delay(10)
            }
        }
    }

    LazyVerticalGrid(
        state = state,
        columns = GridCells.Adaptive(minSize = 128.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        modifier = Modifier.photoGridDragHandler(state, selectedIds, autoScrollSpeed = autoScrollSpeed,
        autoScrollThreshold =  with(LocalDensity.current){ 40.dp.toPx()}
        )
    ) {
        items(photos, key = { it }) { id ->

            val selected = selectedIds.value.contains(id)


            ImageItem(
                selected = selected,
                inSelectionMode = inSelectionMode,
                modifier = Modifier
                    .semantics {
                        if (!inSelectionMode) {
                            onLongClick("Select") {
                                selectedIds.value += id
                                true
                            }
                        }
                    }
                    .then(if (inSelectionMode) {
                        Modifier.toggleable(
                            value = selected,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null, // do not show a ripple
                            onValueChange = {
                                if (it) {
                                    selectedIds.value += id
                                } else {
                                    selectedIds.value -= id
                                }
                            }
                        )
                    } else Modifier)
            )


        }
    }
}


@Composable
private fun ImageItem(
    selected: Boolean, inSelectionMode: Boolean, modifier: Modifier
) {

    Surface(
        tonalElevation = 3.dp,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = modifier.aspectRatio(1f)
    ) {

        if (inSelectionMode) {
            if (selected) {
                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
            } else {
                Icon(imageVector = Icons.Default.RadioButtonUnchecked, contentDescription = null)
            }
        } else {

        }


    }

}