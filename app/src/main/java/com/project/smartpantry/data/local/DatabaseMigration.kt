package com.project.smartpantry.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1,2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
                ALTER TABLE ingredients
                ADD COLUMN expirationDateEpochDay INTEGER
            """.trimIndent()
        )
    }
}