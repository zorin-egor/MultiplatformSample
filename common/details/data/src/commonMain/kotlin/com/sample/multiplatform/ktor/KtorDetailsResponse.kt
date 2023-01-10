package com.sample.multiplatform.ktor

import com.sample.multiplatform.models.Details
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KtorDetailsResponse(
    @SerialName("id") val id: Long,
    @SerialName("updateTime") var updateTime: Long = 0,
    @SerialName("userId") var userId: Long = -1,
    @SerialName("nodeId") var nodeId: String? = null,
    @SerialName("login") var login: String? = null,
    @SerialName("url") var url: String? = null,
    @SerialName("avatarUrl") var avatarUrl: String? = null
)

fun mapTo(item: KtorDetailsResponse): Details {
    return Details(
        id = item.id,
        updateTime = item.updateTime,
        userId = item.userId,
        nodeId = item.nodeId,
        login = item.login,
        url = item.url,
        avatarUrl = item.avatarUrl,
    )
}