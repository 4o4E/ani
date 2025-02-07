/*
 * Ani
 * Copyright (C) 2022-2024 Him188
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.him188.ani.app.ui.settings

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

@Composable
fun BoxWithSaveButton(
    showButton: Boolean,
    onClickSave: () -> Unit,
    buttonHeightOffset: @Composable () -> Dp,
    modifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    content: @Composable RowScope.(width: Dp) -> Unit,
) {
    BoxWithConstraints {
        Row(modifier) {
            val contentWeight by animateFloatAsState(if (showButton) 0.85f else 1.0f)

            content(contentWeight * this@BoxWithConstraints.maxWidth)

            Box(
                Modifier
                    .width((1 - contentWeight) * this@BoxWithConstraints.maxWidth)
                    .padding(start = 4.dp, top = 4.dp) // align to center
                    .fillMaxHeight(),
                contentAlignment = Alignment.BottomEnd
            ) {
                // TODO:  BoxWithSaveButton
//                @Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")
//                val containerColor by rememberUpdatedState(if (enabled) colors.containerColor else colors.disabledContainerColor)
//                Box(
//                    buttonModifier
//                        .background(containerColor)
//                        .clip(AppTheme.shapes.small)
//                        .width(40.dp)
//                        .height(this@BoxWithConstraints.maxHeight + buttonHeightOffset())
//                        .border(BorderStroke(1.dp, color = AppTheme.colorScheme.primary), shape = AppTheme.shapes.small)
//                        .clickable(
//                            remember { MutableInteractionSource() },
//                            rememberRipple(),
//                            enabled = enabled,
//                            onClick = onClickSave,
//                        ),
//                    contentAlignment = Alignment.Center
//                ) {
//                    @Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")
//                    val contentColor by colors.contentColor(enabled)
//                    Icon(
//                        Res.painter.check,
//                        LocalI18n.current.getString("preferences.save.changes"),
//                        Modifier.size(24.dp),
//                        tint = contentColor,
//                    )
//                }
            }
        }
    }
}
