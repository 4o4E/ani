/*
 * Animation Garden App
 * Copyright (C) 2022  Him188
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

package me.him188.animationgarden.android.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.him188.animationgarden.app.AppTheme
import me.him188.animationgarden.app.app.LocalAppSettingsManager
import me.him188.animationgarden.app.i18n.LocalI18n
import me.him188.animationgarden.app.ui.settings.ProxySettingsGroup
import me.him188.animationgarden.app.ui.settings.SyncSettingsGroup

class SettingsActivity : BaseComponentActivity() {
    companion object {
        fun getIntent(context: Context): Intent = Intent(context, SettingsActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContent {
            MaterialTheme {
                ImmerseStatusBar(AppTheme.colorScheme.primary)

                CommonAppScaffold(
                    topBar = {
                        CommonTopAppBar(
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        Icons.Default.ArrowBack, LocalI18n.current.getString("menu.back")
                                    )
                                }
                            },
                            title = {
                                Text(text = LocalI18n.current.getString("window.settings.title"))
                            },
                        )
                    }
                ) {
                    Box(modifier = Modifier.padding(vertical = 16.dp)) {
                        SettingsPage()
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsPage() {
    val manager = LocalAppSettingsManager.current
    val settings by manager.value
    Column(Modifier.padding(horizontal = 8.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ProxySettingsGroup(
            settings,
            manager,
            disabledButtonText = { Text(LocalI18n.current.getString("preferences.proxy.mode.system")) },
            disabledContent = {
                Text(LocalI18n.current.getString("preferences.proxy.mode.system.content"))
            }
        )
        SyncSettingsGroup(settings, manager)
    }
}
