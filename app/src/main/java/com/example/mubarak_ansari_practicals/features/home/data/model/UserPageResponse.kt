package com.example.mubarak_ansari_practicals.features.home.data.model

data class UserPageResponse(
    val backoff: Int? = null,
    val has_more: Boolean? = null,
    val items: List<Item>? = null,
    val quota_max: Int? = null,
    val quota_remaining: Int? = null,
)