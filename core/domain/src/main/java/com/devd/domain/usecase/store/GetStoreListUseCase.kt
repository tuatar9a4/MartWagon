package com.devd.domain.usecase.store

import com.devd.domain.model.datastore.DataStoreObjKey
import com.devd.domain.repository.DataStoreRepository
import com.devd.domain.repository.getObject
import javax.inject.Inject

class GetStoreListUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(): List<String> {
        return dataStoreRepository.getObject(DataStoreObjKey.STORE_LIST_KEY, emptyList())
    }

}