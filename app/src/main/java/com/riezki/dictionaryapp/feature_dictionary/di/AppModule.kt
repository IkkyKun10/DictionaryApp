package com.riezki.dictionaryapp.feature_dictionary.di

import android.app.Application
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.riezki.dictionaryapp.feature_dictionary.data.local.Converters
import com.riezki.dictionaryapp.feature_dictionary.data.local.WordInfoDatabase
import com.riezki.dictionaryapp.feature_dictionary.data.remote.ApiService
import com.riezki.dictionaryapp.feature_dictionary.data.repository.WordInfoRepository
import com.riezki.dictionaryapp.feature_dictionary.data.util.GsonParser
import com.riezki.dictionaryapp.feature_dictionary.domain.repository.IWordInfoRepository
import com.riezki.dictionaryapp.feature_dictionary.domain.use_case.GetWordInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGetWordInfoUseCase(repository: IWordInfoRepository) : GetWordInfoUseCase {
        return GetWordInfoUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        db: WordInfoDatabase,
        api: ApiService
    ) : IWordInfoRepository {
        return WordInfoRepository(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application) : WordInfoDatabase {
        return Room.databaseBuilder(
            app, WordInfoDatabase::class.java, "word_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideDictionaryApiService(context: Application) : ApiService {

        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}