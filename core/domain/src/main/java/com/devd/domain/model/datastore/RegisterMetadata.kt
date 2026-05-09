package com.devd.domain.model.datastore

import kotlinx.serialization.Serializable

@Serializable
data class RegisterMetadata(
    val martList: List<String>,
    val category: List<String>,
)