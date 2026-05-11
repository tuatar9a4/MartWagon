package com.devd.domain.model.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey


interface DataStoreKey<T> {
    val key: Preferences.Key<T>
}

object DataStoreKeys {
    val CHART_PRODUCT_NAME = object : DataStoreKey<String> {
        override val key = stringPreferencesKey("chartProductName")
    }
}

object DataStoreObjKey {
    const val STORE_LIST_KEY = "MartListKey" // List<MartItem>
    const val REGISTER_METADATA_KEY = "RegisterMetadataKey" // RegisterMetadata
    const val STORE_RECENT_SEARCH_WORD_KEY = "RecentSearchKey" // List<String>
}