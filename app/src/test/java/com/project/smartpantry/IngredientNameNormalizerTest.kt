package com.project.smartpantry

import com.project.smartpantry.util.normalizeIngredientName
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class IngredientNameNormalizerTest {
    @Test
    fun eggs_normalizesToEgg() {
        assertEquals("egg", normalizeIngredientName("Eggs"))
    }

    @Test
    fun trimsAdnLowercases() {
        assertEquals("redonion", normalizeIngredientName("  RED  ONION "))
    }

    @Test
    fun chickenBreastIsNotChicken() {
        assertNotEquals(normalizeIngredientName("Chicken"), normalizeIngredientName("Chicken Breast"))
    }
}