/*
 * Kotlin Bitflags
 *
 * Copyright (c) 2021 Janne Mareike Koschinski
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at https://mozilla.org/MPL/2.0/.
 */

package de.justjanne.bitflags

/**
 * Interface for a helper object for a type of flag
 */

// Possible see also https://github.com/ITesserakt/diskordin/tree/master/diskordin-base/src/main/kotlin/org/tesserakt/diskordin/util/enums
interface Flags<T, U> where U : Flag<T>, U : Enum<U> {
  /**
   * Predefined set with all possible flag values
   */
  val all: Set<U>
}
