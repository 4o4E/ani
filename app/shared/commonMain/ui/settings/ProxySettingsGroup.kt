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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import me.him188.ani.app.app.AppSettings
import me.him188.ani.app.app.settings.ProxyMode
import me.him188.ani.app.i18n.LocalI18n
import me.him188.ani.app.ui.theme.AppTheme
import me.him188.ani.app.ui.theme.stronglyWeaken

@Composable
fun ColumnScope.ProxySettingsGroup(
    settings: AppSettings,
    disabledButtonText: @Composable () -> Unit = { Text(LocalI18n.current.getString("preferences.proxy.mode.disabled")) },
    disabledContent: (@Composable () -> Unit)? = null,
) {
    SettingsGroup({ Text(LocalI18n.current.getString("preferences.proxy")) }) {
        Row(
            Modifier.height(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LabelledRadioButton(settings.proxy.mode == ProxyMode.DISABLED, {
//                manager.mutate { copy(proxy = proxy.copy(mode = ProxyMode.DISABLED)) }
            }) {
                disabledButtonText()
            }

            LabelledRadioButton(settings.proxy.mode == ProxyMode.HTTP, {
//                manager.mutate { copy(proxy = proxy.copy(mode = ProxyMode.HTTP)) }
            }) {
                Text(LocalI18n.current.getString("preferences.proxy.mode.http"))
            }

            LabelledRadioButton(settings.proxy.mode == ProxyMode.SOCKS, {
//                manager.mutate { copy(proxy = proxy.copy(mode = ProxyMode.SOCKS)) }
            }) {
                Text(LocalI18n.current.getString("preferences.proxy.mode.socks"))
            }
        }

        val enter = fadeIn() + expandVertically(tween(durationMillis = 200, delayMillis = 200))
        val exit = fadeOut() + shrinkVertically(tween(durationMillis = 200))
        if (disabledContent != null) {
            AnimatedVisibility(
                settings.proxy.mode == ProxyMode.DISABLED,
                enter = enter,
                exit = exit,
            ) {
                Row(Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
                    ProvideTextStyle(AppTheme.typography.bodyMedium.run { copy(color = color.stronglyWeaken()) }) {
                        disabledContent()
                    }
                }
            }
        }

        AnimatedVisibility(
            settings.proxy.mode == ProxyMode.HTTP,
            enter = enter,
            exit = exit,
        ) {
            Row(Modifier.padding(vertical = 8.dp, horizontal = 8.dp).height(48.dp)) {
                var value by remember { mutableStateOf(settings.proxy.http.url) }
                OutlinedTextFieldWithSaveButton(
                    value = value,
                    onValueChange = { value = it },
                    showButton = value != settings.proxy.http.url,
                    onClickSave = {
//                        manager.updateProxyHttpUrl(value)
                    },
                    label = {
                        Text(LocalI18n.current.getString("preferences.proxy.http.url"))
                    }
                )
            }
        }

        AnimatedVisibility(
            settings.proxy.mode == ProxyMode.SOCKS,
            enter = enter,
            exit = exit,
        ) {
            Row(Modifier.padding(vertical = 8.dp, horizontal = 8.dp).height(48.dp)) {
                var newHost by remember { mutableStateOf(settings.proxy.socks.host) }
                var newPort by remember { mutableStateOf(settings.proxy.socks.port) }

                val hostFocus = remember { FocusRequester() }
                val portFocus = remember { FocusRequester() }
                val buttonFocus = remember { FocusRequester() }
                BoxWithSaveButton(
                    showButton = newHost != settings.proxy.socks.host || newPort != settings.proxy.socks.port,
                    onClickSave = {
//                        manager.updateProxySocks(newHost, newPort)
                    },
                    buttonHeightOffset = { 8.dp },
                    Modifier.focusGroup(),
                    buttonModifier = Modifier
                        .focusRequester(buttonFocus)
                        .focusProperties {
                            previous = portFocus
                            left = portFocus
                            start = hostFocus
                            end = buttonFocus
                        },
                    enabled = isPortValid(newPort),
                ) { width ->
                    val hostWeight = 0.76f
                    SettingsOutlinedTextField(
                        value = newHost,
                        onValueChange = { newHost = it },
                        width = width * hostWeight,
                        onPressEnter = {
                            portFocus.requestFocus()
                            true
                        },
                        Modifier
                            .focusRequester(hostFocus)
                            .focusProperties {
                                next = portFocus
                                right = portFocus
                                start = hostFocus
                                end = buttonFocus
                            },
                        label = {
                            Text(LocalI18n.current.getString("preferences.proxy.socks.host"))
                        },
                    )
                    SettingsOutlinedTextField(
                        value = remember(newPort) { newPort.toString() },
                        onValueChange = { newPort = it.toIntOrNull() ?: 0 },
                        width = width * (1 - hostWeight),
                        onPressEnter = {
//                            manager.updateProxySocks(newHost, newPort)
                            true
                        },
                        Modifier
                            .padding(start = 8.dp)
                            .focusRequester(portFocus)
                            .focusProperties {
                                previous = hostFocus
                                next = buttonFocus
                                left = hostFocus
                                right = buttonFocus
                                start = hostFocus
                                end = buttonFocus
                            },
                        isError = !isPortValid(newPort),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        label = {
                            Text(LocalI18n.current.getString("preferences.proxy.socks.port"))
                        }
                    )
                }
            }
        }
    }
}

@Stable
fun isPortValid(newPort: Int) = newPort in 0..65535

@Stable
fun isHostValid(host: String) = host.isNotEmpty()
