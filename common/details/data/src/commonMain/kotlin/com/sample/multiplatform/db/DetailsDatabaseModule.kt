package com.sample.multiplatform.db

import com.sample.multiplatform.DetailsRepository
import com.sample.multiplatform.details.data.Database
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val dbDetailsModule = DI.Module("dbDetailsModule") {
    bind<Database>(tag = DetailsRepository::class.simpleName) with singleton {
        val driverFactory: GitHubDriverFactory = instance()
        val driver = driverFactory.createDriver(Database.Schema)
        Database.Schema.create(driver)
        Database(driver)
    }
}