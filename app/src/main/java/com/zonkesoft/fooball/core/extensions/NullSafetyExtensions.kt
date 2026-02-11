package com.zonkesoft.fooball.core.extensions

/**
 * Null safety extensions for better handling of nullable types
 */

/**
 * Returns this string if not null and not empty, otherwise returns the default value
 */
fun String?.orDefault(default: String = ""): String =
    if (this.isNullOrEmpty()) default else this

/**
 * Returns this Int if not null, otherwise returns the default value
 */
fun Int?.orDefault(default: Int = 0): Int = this ?: default

/**
 * Returns this Double if not null, otherwise returns the default value
 */
fun Double?.orDefault(default: Double = 0.0): Double = this ?: default

/**
 * Returns this Boolean if not null, otherwise returns the default value
 */
fun Boolean?.orDefault(default: Boolean = false): Boolean = this ?: default

/**
 * Returns this Long if not null, otherwise returns the default value
 */
fun Long?.orDefault(default: Long = 0L): Long = this ?: default

/**
 * Safe string formatter for nullable strings
 */
fun String?.toSafeString(): String = this.orEmpty()

/**
 * Safe int formatter for nullable integers
 */
fun Int?.toSafeInt(): Int = this ?: 0

/**
 * Safe double formatter for nullable doubles
 */
fun Double?.toSafeDouble(): Double = this ?: 0.0

/**
 * Checks if a string is null or empty and returns null if so
 */
fun String?.nullIfEmpty(): String? = if (this.isNullOrEmpty()) null else this

/**
 * Returns a default list if this list is null
 */
fun <T> List<T>?.orEmptyList(): List<T> = this ?: emptyList()

/**
 * Returns a default map if this map is null
 */
fun <K, V> Map<K, V>?.orEmptyMap(): Map<K, V> = this ?: emptyMap()

