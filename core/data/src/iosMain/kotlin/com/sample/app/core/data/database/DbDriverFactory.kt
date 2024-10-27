package com.sample.app.core.data.database

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.sample.app.core.model.AppConfigModel

actual class DbDriverFactory actual constructor(appConfig: AppConfigModel){
    actual suspend fun provideDbDriver(
        schema: SqlSchema<QueryResult.AsyncValue<Unit>>,
    ): SqlDriver = NativeSqliteDriver(schema.synchronous(), "main_database.db")
}


