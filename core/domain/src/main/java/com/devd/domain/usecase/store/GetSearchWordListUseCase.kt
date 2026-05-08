package com.devd.domain.usecase.store

import com.devd.domain.model.datastore.DataStoreObjKey
import com.devd.domain.repository.DataStoreRepository
import com.devd.domain.repository.getObjectFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchWordListUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<List<String>> {
        return dataStoreRepository.getObjectFlow(
            DataStoreObjKey.STORE_RECENT_SEARCH_WORD_KEY,
            emptyList()
        )
    }

}