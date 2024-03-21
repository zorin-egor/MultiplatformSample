package com.sample.multiplatform.db

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import com.sample.multiplatform.PlatformConfiguration

expect class DriverFactory(platformConfiguration: PlatformConfiguration) {
    fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>, name: String): SqlDriver
}

class GitHubDriverFactory(private val factory: DriverFactory) {
    fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>): SqlDriver {
        return factory.createDriver(schema, "github_client.db")
    }
}