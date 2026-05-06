package com.devd.datastroe

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.devd.domain.model.datastore.DataStoreKey
import com.devd.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences> // Context 대신 DataStore를 직접 주입
) : DataStoreRepository {

    override suspend fun <T> saveValue(dataStoreKey: DataStoreKey<T>, value: T) {
        dataStore.edit { it[dataStoreKey.key] = value }
    }

    override fun <T> getValueFlow(dataStoreKey: DataStoreKey<T>, defaultValue: T): Flow<T> {
        return dataStore.data.map { it[dataStoreKey.key] ?: defaultValue }
    }

    override suspend fun <T> getValue(dataStoreKey: DataStoreKey<T>, defaultValue: T): T {
        return getValueFlow(dataStoreKey, defaultValue).first()
    }

    override suspend fun <T> removeValue(dataStoreKey: DataStoreKey<T>) {
        dataStore.edit { it.remove(dataStoreKey.key) }
    }

    // JSON 처리를 위한 구현
    override suspend fun saveJsonString(keyName: String, jsonString: String) {
        val key = stringPreferencesKey(keyName)
        dataStore.edit { it[key] = jsonString }
    }

    override fun getJsonStringFlow(keyName: String): Flow<String?> {
        val key = stringPreferencesKey(keyName)
        return dataStore.data.map { it[key] }
    }

}