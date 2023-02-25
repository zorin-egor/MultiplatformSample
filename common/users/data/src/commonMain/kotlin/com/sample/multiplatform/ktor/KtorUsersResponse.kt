package com.sample.multiplatform.ktor

import com.sample.multiplatform.models.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KtorUsersResponse(
    @SerialName("id") val id: Long,
    @SerialName("update_time") var updateTime: Long = 0,
    @SerialName("userId") var userId: Long = -1,
    @SerialName("nodeId") var nodeId: String? = null,
    @SerialName("login") var login: String? = null,
    @SerialName("url") var url: String? = null,
    @SerialName("avatar_url") var avatarUrl: String? = null
)

fun mapTo(item: KtorUsersResponse): User {
    return User(
        id = item.id,
        updateTime = item.updateTime,
        userId = item.userId,
        nodeId = item.nodeId,
        login = item.login,
        url = item.url,
        avatarUrl = item.avatarUrl,
    )
}