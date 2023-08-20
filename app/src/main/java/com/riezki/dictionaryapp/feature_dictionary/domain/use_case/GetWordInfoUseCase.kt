package com.riezki.dictionaryapp.feature_dictionary.domain.use_case

import com.riezki.dictionaryapp.core.util.Resource
import com.riezki.dictionaryapp.feature_dictionary.domain.model.WordInfo
import com.riezki.dictionaryapp.feature_dictionary.domain.repository.IWordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWordInfoUseCase(
    private val repository: IWordInfoRepository
) {

    operator fun invoke(word: String) : Flow<Resource<List<WordInfo>>> {
        if (word.isBlank()) {
            return flow {  }
        }

        return repository.getWordInfo(word)
    }
}