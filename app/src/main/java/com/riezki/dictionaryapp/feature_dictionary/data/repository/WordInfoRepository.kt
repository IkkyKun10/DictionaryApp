package com.riezki.dictionaryapp.feature_dictionary.data.repository

import com.riezki.dictionaryapp.core.util.Resource
import com.riezki.dictionaryapp.feature_dictionary.data.local.WordInfoDao
import com.riezki.dictionaryapp.feature_dictionary.data.remote.ApiService
import com.riezki.dictionaryapp.feature_dictionary.domain.model.WordInfo
import com.riezki.dictionaryapp.feature_dictionary.domain.repository.IWordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class WordInfoRepository(
    private val apiService: ApiService,
    private val dao: WordInfoDao,
) : IWordInfoRepository {

    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> {
        return flow {
            emit(Resource.Loading())

            val wordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
            emit(Resource.Loading(data = wordInfos))

            try {
                val remoteWordInfos = apiService.getWordInfo(word)
                dao.deleteWordInfos(remoteWordInfos.map { it.word })
                dao.insertWordInfos(remoteWordInfos.map { it.toWordInfoEntity() })
            } catch (e: HttpException) {
                emit(Resource.Error(
                    message = "Oops, something went wrong!",
                    data = wordInfos,
                ))
            } catch (e: IOException) {
                emit(Resource.Error(
                    message = "Couldn't reach server, check your internet connection.",
                    data = wordInfos,
                ))
            }

            val newWordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
            emit(Resource.Success(data = newWordInfos))
        }
    }

}