package edu.ucne.jugadorestictactoe.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.jugadorestictactoe.data.Database.JugadorDb
import edu.ucne.jugadorestictactoe.data.local.repository.JugadorRepositoryImpl
import edu.ucne.jugadorestictactoe.data.local.repository.LogroRepositoryImpl
import edu.ucne.jugadorestictactoe.data.local.repository.PartidaRepositoryImpl
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository
import edu.ucne.jugadorestictactoe.domain.repository.LogroRepository
import edu.ucne.jugadorestictactoe.domain.repository.PartidaRepository
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.EliminarJugadorUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.GuardarJugadorUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.JugadorUseCases
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.ObtenerJugadorUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.ObtenerJugadoresUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.ValidarJugadorUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase.EliminarPartidaUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase.GuardarPartidaUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase.ObtenerPartidaUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase.ObtenerPartidasUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase.PartidaUseCases
import edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase.ValidarPartidaUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun providesJugadorDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            JugadorDb::class.java,
            "JugadorDb"
        ).fallbackToDestructiveMigration()
            .build()


    @Provides
    fun providesJugadorDao(jugadorDb: JugadorDb) = jugadorDb.JugadorDao()
    @Provides
    fun providesPartidaDao(jugadorDb: JugadorDb)= jugadorDb.PartidaDao()

    @Provides
    fun provideJugadorUseCases(repository: JugadorRepository): JugadorUseCases {
        val validarJugador = ValidarJugadorUseCase(repository)
        return JugadorUseCases(
            validarJugador = validarJugador,
            guardarJugador = GuardarJugadorUseCase(repository, validarJugador),
            eliminarJugador = EliminarJugadorUseCase(repository),
            obtenerJugador = ObtenerJugadorUseCase(repository),
            obtenerJugadores = ObtenerJugadoresUseCase(repository)
        )}
    @Provides
    fun providePartidaUseCases(repository: PartidaRepository): PartidaUseCases{
        val validarPartida = ValidarPartidaUseCase()
        return PartidaUseCases (
            validarPartida = validarPartida,
            guardarPartida = GuardarPartidaUseCase(repository,validarPartida),
            eliminarPartida = EliminarPartidaUseCase(repository),
            obtenerPartida = ObtenerPartidaUseCase(repository),
            obtenerPartidas = ObtenerPartidasUseCase(repository),

        )}

    @Module
    @InstallIn(SingletonComponent::class)

    abstract class RepositoryModule {

        @Binds
        @Singleton
        abstract fun bindJugadorRepository(
            impl: JugadorRepositoryImpl
        ): JugadorRepository

        @Binds
        @Singleton
        abstract fun bindPartidaRepository(
            impl: PartidaRepositoryImpl
        ): PartidaRepository

        @Binds
        @Singleton
        abstract fun bindLogroRepository(
            impl: LogroRepositoryImpl
        ): LogroRepository
    }
}


