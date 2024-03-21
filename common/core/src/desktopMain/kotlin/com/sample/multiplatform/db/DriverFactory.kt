package com.sample.multiplatform.db

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.sample.multiplatform.PlatformConfiguration
import java.io.File

actual class DriverFactory actual constructor(private val platformConfiguration: PlatformConfiguration) {

    actual fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>, name: String): SqlDriver {
        val appPath = platformConfiguration.appDataPath
        if (!File(appPath).exists()) {
            File(appPath).mkdirs()
        }

        val filePath = "$appPath/$name"
        return JdbcSqliteDriver("jdbc:sqlite:$filePath").apply {
            if (!File(filePath).exists()) {
                schema.create(this)
            }
        }
    }

}