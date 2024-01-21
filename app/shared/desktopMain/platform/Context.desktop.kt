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

package me.him188.ani.app.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import java.io.File

actual abstract class Context

class DesktopContext(
    val dataDir: File,
    val dataStoreDir: File,
) : Context() {
    val tokenStore: DataStore<Preferences> = PreferenceDataStoreFactory.create {
        dataStoreDir.resolve("tokens.preferences_pb")
    }

    val settingStore: DataStore<Preferences> = PreferenceDataStoreFactory.create {
        dataStoreDir.resolve("settings.preferences_pb")
    }
}

actual val LocalContext: ProvidableCompositionLocal<Context> = compositionLocalOf {
    error("No Context provided")
}


@Composable
actual fun isInLandscapeMode(): Boolean = false

actual fun Context.setFullScreen(fullscreen: Boolean) {
}