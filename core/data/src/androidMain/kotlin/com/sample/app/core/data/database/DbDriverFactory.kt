package com.sample.app.core.data.database

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.sample.app.core.model.AppConfigModel

actual class DbDriverFactory actual constructor(private val appConfig: AppConfigModel){
    actual suspend fun provideDbDriver(
        schema: SqlSchema<QueryResult.AsyncValue<Unit>>,
    ): SqlDriver = AndroidSqliteDriver(schema.synchronous(), appConfig.androidContext, "main_database.db")
}