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

package me.him188.animationgarden.server.modules

import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import kotlinx.serialization.SerializationException

class ExceptionModule : KtorModule {
    override fun Application.install() {
        install(StatusPages) {
            exception<Throwable> { call, _ ->
                call.respondError("Internal server error")
            }

            exception<SerializationException> { call, cause ->
                call.respondError("Invalid request body: ${cause.message}")
            }
        }
    }
}
