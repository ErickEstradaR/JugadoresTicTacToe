package edu.ucne.jugadorestictactoe.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.jugadorestictactoe.data.remote.dto.Tecnico.TecnicosApiService
import edu.ucne.jugadorestictactoe.data.remote.dto.partidaApi.JugadorApiService
import edu.ucne.jugadorestictactoe.data.remote.dto.partidaApi.MovimientoApiService
import edu.ucne.jugadorestictactoe.data.remote.dto.partidaApi.PartidaApiService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import kotlin.jvm.java

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    const val BASE_URL = "https://gestionhuacalesapi.azurewebsites.net/"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideTecnicosApiService(moshi: Moshi): TecnicosApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TecnicosApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideJugadorApiService(moshi: Moshi): JugadorApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(JugadorApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovimientoApiService(moshi: Moshi): MovimientoApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(MovimientoApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePartidaApiService(moshi: Moshi): PartidaApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PartidaApiService::class.java)
    }
}

