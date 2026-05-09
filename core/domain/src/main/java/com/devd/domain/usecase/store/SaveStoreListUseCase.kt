package com.devd.domain.usecase.store

import com.devd.domain.model.datastore.DataStoreObjKey
import com.devd.domain.model.datastore.RegisterMetadata
import com.devd.domain.repository.DataStoreRepository
import com.devd.domain.repository.saveObject
import javax.inject.Inject

class SaveStoreListUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(saveItem: RegisterMetadata) {
        dataStoreRepository.saveObject(DataStoreObjKey.REGISTER_METADATA_KEY, saveItem)
    }

}