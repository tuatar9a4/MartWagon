package com.devd.domain.usecase.store

import com.devd.domain.model.datastore.DataStoreObjKey
import com.devd.domain.repository.DataStoreRepository
import com.devd.domain.repository.saveObject
import javax.inject.Inject

class SaveSearchWordListUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(saveItem: List<String>) {
        dataStoreRepository.saveObject(DataStoreObjKey.STORE_RECENT_SEARCH_WORD_KEY, saveItem)
    }

}