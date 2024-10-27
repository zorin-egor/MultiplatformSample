package com.sample.app.core.data.database

import app.cash.sqldelight.db.SqlDriver
import com.sample.app.core.data.Database
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


interface AppDatabase {
    suspend fun db(): Database
}

class AppDatabaseImpl(
    private val dbDriverFactory: DbDriverFactory
): AppDatabase {

    private val mutex = Mutex()
    private lateinit var appDatabase: Database
    private lateinit var sqlDrive: SqlDriver

    override suspend fun db(): Database = mutex.withLock {
        if (!this::appDatabase.isInitialized) {
            val driver = dbDriverFactory.provideDbDriver(Database.Schema)
            Database(driver).also {
                sqlDrive = driver
                appDatabase = it
            }
        } else {
            appDatabase
        }
    }

}