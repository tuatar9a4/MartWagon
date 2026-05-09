package com.devd.domain.model.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey


interface DataStoreKey<T> {
    val key: Preferences.Key<T>
}

// 구체적인 키 정의 (예시)
object DataStoreKeys {
    val USER_NAME = object : DataStoreKey<String> {
        override val key = stringPreferencesKey("user_name")
    }
    val IS_LOGGED_IN = object : DataStoreKey<Boolean> {
        override val key = booleanPreferencesKey("is_logged_in")
    }
}

object DataStoreObjKey {
    const val STORE_LIST_KEY = "MartListKey" // List<MartItem>
    const val REGISTER_METADATA_KEY = "RegisterMetadataKey" // RegisterMetadata
    const val STORE_RECENT_SEARCH_WORD_KEY = "RecentSearchKey" // List<String>
}