package com.riezki.dictionaryapp.feature_dictionary.domain.model

data class WordInfo(
    val meanings: List<Meaning>,
    val origin: String? = null,
    val phonetic: String,
    val word: String,
)
