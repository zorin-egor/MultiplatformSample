package com.sample.multiplatform.db

import com.sample.multiplatform.UsersRepository
import com.sample.multiplatform.users.data.Database
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val dbUsersModule = DI.Module("dbUsersModule") {
    bind<Database>(tag = UsersRepository::class.simpleName) with singleton {
        val driverFactory: GitHubDriverFactory = instance()
        val driver = driverFactory.createDriver(Database.Schema)
        Database.Schema.create(driver)
        Database(driver)
    }
}