package com.project.smartpantry.util

fun normalizeIngredientName(name: String): String {
    val normalized = name.trim().lowercase().replace(Regex("\\s+"), "")
    return when (normalized) {
        "eggs" -> "egg"
        "tomatoes" -> "tomato"
        "potatoes" -> "potato"
        "onions" -> "onion"
        else -> normalized
    }
}