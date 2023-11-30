package com.mbj.composepaging.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RickMorties(
    @SerialName("results")val results: List<RickMorty>? = emptyList()
)

@Serializable
data class RickMorty(
    @SerialName("created") val created: String? = "생성일이 존재하지 않습니다.",
    @SerialName("episode") val episode: List<String?>? = listOf("에피소드가 존재하지 않습니다."),
    @SerialName("gender") val gender: String? = "성별이 존재하지 않습니다.",
    @SerialName("id") val id: Int? = null,
    @SerialName("image") val image: String? = "이미지가 존재하지 않습니다.",
    @SerialName("name") val name: String? = "이름이 존재하지 않습니다.",
    @SerialName("species") val species: String? = null,
    @SerialName("status") val status: String? = "해당 종이 존재하지 않습니다.",
    @SerialName("type") val type: String? = "타입이 존재하지 않습니다.",
    @SerialName("url") val url: String? = "url이 존재하지 않습니다."

)
