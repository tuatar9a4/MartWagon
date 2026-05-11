package com.devd.domain.model.datastore

import kotlinx.serialization.Serializable

@Serializable
data class SavedMartData(
    val martName: String,
    val colorPallet: Int
)