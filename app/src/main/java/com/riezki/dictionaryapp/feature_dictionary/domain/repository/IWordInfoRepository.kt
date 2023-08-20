package com.riezki.dictionaryapp.feature_dictionary.domain.repository

import com.riezki.dictionaryapp.core.util.Resource
import com.riezki.dictionaryapp.feature_dictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface IWordInfoRepository {

    fun getWordInfo(word: String) : Flow<Resource<List<WordInfo>>>

}