package com.sample.app.core.network.di

import com.sample.app.core.network.requests.repositories.KtorRepositoriesDataSource
import com.sample.app.core.network.requests.users.KtorUsersDataSource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

const val MODULE_NETWORK = "networkModule"

val networkModule = DI.Module(MODULE_NETWORK) {
    bind<HttpClient>() with singleton {
        HttpClient {
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }

            install(DefaultRequest)

            install(ContentNegotiation) {
                json(Json {
                    explicitNulls = false
                    isLenient = true
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }

            install(HttpTimeout) {
                connectTimeoutMillis = 15000
                requestTimeoutMillis = 30000
            }

            defaultRequest {
                url("https://api.github.com/")
                header("Content-Type", "application/json; charset=UTF-8")
            }
        }
    }

    bind<KtorUsersDataSource>() with singleton {
        KtorUsersDataSource(instance(), instance())
    }

    bind<KtorRepositoriesDataSource>() with singleton {
        KtorRepositoriesDataSource(instance())
    }
}