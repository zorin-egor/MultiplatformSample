package com.sample.app.core.data.database

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import com.sample.app.core.model.AppConfigModel

expect class DbDriverFactory(appConfig: AppConfigModel) {
    suspend fun provideDbDriver(schema: SqlSchema<QueryResult.AsyncValue<Unit>>): SqlDriver
}