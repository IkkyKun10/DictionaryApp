package com.riezki.dictionaryapp.feature_dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.riezki.dictionaryapp.feature_dictionary.domain.model.Meaning
import com.riezki.dictionaryapp.feature_dictionary.domain.model.WordInfo

@Entity(tableName = "wordinfoentity")
data class WordInfoEntity(
    val word: String,
    val phonetic: String,
    val origin: String? = null,
    val meanings: List<Meaning>,
    @PrimaryKey
    val id: Int? = null,
) {
    fun toWordInfo() : WordInfo {
        return WordInfo(
            meanings = meanings,
            origin = origin,
            phonetic = phonetic,
            word = word,
        )
    }
}