package edu.ucne.jugadorestictactoe.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.jugadorestictactoe.data.local.Database.JugadorDb
import edu.ucne.jugadorestictactoe.data.repository.JugadorRepositoryImpl
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository
import edu.ucne.jugadorestictactoe.domain.useCase.EliminarJugadorUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.GuardarJugadorUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.JugadorUseCases
import edu.ucne.jugadorestictactoe.domain.useCase.ObtenerJugadorUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.ObtenerJugadoresUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.ValidarJugadorUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun providesTareaDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            JugadorDb::class.java,
            "JugadorDb"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesJugadorDao(jugadorDb: JugadorDb) = jugadorDb.JugadorDao()

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
    @Module
    @InstallIn(SingletonComponent::class)
    abstract class RepositoryModule {

        @Binds
        @Singleton
        abstract fun bindJugadorRepository(
            impl: JugadorRepositoryImpl
        ): JugadorRepository
    }
}


