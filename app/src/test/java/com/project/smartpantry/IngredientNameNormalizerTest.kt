package com.project.smartpantry

import com.project.smartpantry.util.normalizeIngredientName
import junit.framework.TestCase.assertEquals
import org.junit.Test

class IngredientNameNormalizerTest {
    @Test
    fun eggs_normalizesToEgg() {
        assertEquals("egg", normalizeIngredientName("Eggs"))
    }

    @Test
    fun trimsAdnLowercases() {
        assertEquals("red onion", normalizeIngredientName("  RED  ONION "))
    }

    @Test
    fun chickenBreastIsNotChicken() {
        assertEquals(normalizeIngredientName("Chicken"), normalizeIngredientName("Chicken Breast"))
    }
}