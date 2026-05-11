package com.devd.domain.usecase.store

import com.devd.domain.model.datastore.DataStoreObjKey
import com.devd.domain.model.datastore.RegisterMetadata
import com.devd.domain.repository.DataStoreRepository
import com.devd.domain.repository.getObject
import javax.inject.Inject

class GetSavedMetaDataListUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(): RegisterMetadata? {
        return dataStoreRepository.getObject(DataStoreObjKey.REGISTER_METADATA_KEY, null)
    }

}