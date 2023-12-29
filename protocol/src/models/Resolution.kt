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

package me.him188.animationgarden.shared.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(Resolution.Serializer::class)
data class Resolution(
    val id: String,
    val size: Int, // for sorting
    private val otherNames: List<String>,
    private val displayName: String = id,
) {
    constructor(
        id: String,
        size: Int, // for sorting
        vararg otherNames: String,
        displayName: String = id,
    ) : this(id, size, otherNames.toList(), displayName)

    override fun toString(): String {
        return displayName
    }

    companion object {
        private val R240P = Resolution("240P", 240, "x240")
        private val R360P = Resolution("360P", 360, "x360")
        private val R480P = Resolution("480P", 480, "x480")
        private val R560P = Resolution("560P", 560, "x560")
        private val R720P = Resolution("720P", 720, "x720")
        private val R1080P = Resolution("1080P", 1080, "x1080")
        private val R1440P = Resolution("1440P", 1440, "x1440", displayName = "2K")
        private val R2160P = Resolution("2160P", 2160, "x2160", displayName = "4K")
        val ParseError = Resolution("ERROR", 0, "")

        private val entries = arrayOf(
            R240P, R360P, R480P, R560P, R720P, R1080P, R1440P, R2160P,
        )

        fun tryParse(text: String): Resolution? {
            for (entry in entries) {
                if (text.contains(entry.id, ignoreCase = true)
                    || entry.otherNames.any { text.contains(it, ignoreCase = true) }
                ) {
                    return entry
                }
            }
            return null
        }
    }

    internal object Serializer : KSerializer<Resolution> {
        override val descriptor: SerialDescriptor = String.serializer().descriptor

        override fun deserialize(decoder: Decoder): Resolution {
            return tryParse(String.serializer().deserialize(decoder)) ?: ParseError
        }

        override fun serialize(encoder: Encoder, value: Resolution) {
            return String.serializer().serialize(encoder, value.id)
        }

    }
}