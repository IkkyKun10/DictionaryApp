package com.riezki.dictionaryapp.feature_dictionary.data.remote.dto

import com.riezki.dictionaryapp.feature_dictionary.data.local.entity.WordInfoEntity

data class WordInfoDto(
    val meanings: List<MeaningDto>,
    val origin: String? = null,
    val phonetic: String,
    val phonetics: List<PhoneticDto>,
    val word: String
) {
    fun toWordInfoEntity() : WordInfoEntity {
        return WordInfoEntity(
            word = word,
            phonetic = phonetic,
            origin = origin,
            meanings = meanings.map { it.toMeaning() },
        )
    }
}