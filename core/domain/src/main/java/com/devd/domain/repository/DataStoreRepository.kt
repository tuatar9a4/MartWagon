package com.devd.domain.repository

import com.devd.domain.model.datastore.DataStoreKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json


interface DataStoreRepository {
    suspend fun <T> saveValue(dataStoreKey: DataStoreKey<T>, value: T)
    fun <T> getValueFlow(dataStoreKey: DataStoreKey<T>, defaultValue: T): Flow<T>
    suspend fun <T> getValue(dataStoreKey: DataStoreKey<T>, defaultValue: T): T
    suspend fun <T> removeValue(dataStoreKey: DataStoreKey<T>)
    suspend fun saveJsonString(keyName: String, jsonString: String)
    fun getJsonStringFlow(keyName: String): Flow<String?>
}

val dataStoreJson = Json { ignoreUnknownKeys = true }

suspend inline fun <reified T> DataStoreRepository.saveObject(keyName: String, value: T) {
    saveJsonString(keyName, dataStoreJson.encodeToString(value))
}

inline fun <reified T> DataStoreRepository.getObjectFlow(keyName: String, defaultValue: T): Flow<T> {
    return getJsonStringFlow(keyName).map { json ->
        if (json == null) defaultValue
        else {
            try { dataStoreJson.decodeFromString<T>(json) }
            catch (e: Exception) { defaultValue }
        }
    }
}

suspend inline fun <reified T> DataStoreRepository.getObject(keyName: String, defaultValue: T): T {
    return getObjectFlow(keyName, defaultValue).first()
}