package edu.ucne.jugadorestictactoe.di

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.room.Room
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.jugadorestictactoe.data.Database.JugadorDb
import edu.ucne.jugadorestictactoe.data.local.jugadores.Dao.JugadorDao
import edu.ucne.jugadorestictactoe.data.local.repository.JugadorApiRepositoryImpl
import edu.ucne.jugadorestictactoe.data.local.repository.LogroRepositoryImpl
import edu.ucne.jugadorestictactoe.data.local.repository.MovimientoApiRepositoryImpl
import edu.ucne.jugadorestictactoe.data.local.repository.PartidaApiRepositoryImpl
import edu.ucne.jugadorestictactoe.data.local.repository.PartidaRepositoryImpl
import edu.ucne.jugadorestictactoe.data.local.repository.TecnicoRepositoryImpl
import edu.ucne.jugadorestictactoe.data.remote.dto.JugadorRemoteDataSource
import edu.ucne.jugadorestictactoe.data.remote.dto.partidaApi.JugadorApiService
import edu.ucne.jugadorestictactoe.domain.repository.JugadorApiRepository
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository
import edu.ucne.jugadorestictactoe.domain.repository.LogroRepository
import edu.ucne.jugadorestictactoe.domain.repository.MovimientoRepository
import edu.ucne.jugadorestictactoe.domain.repository.PartidaApiRepository
import edu.ucne.jugadorestictactoe.domain.repository.PartidaRepository
import edu.ucne.jugadorestictactoe.domain.repository.TecnicoRepository
import edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.JugadorApiUseCases.GuardarJugadorApiUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.JugadorApiUseCases.JugadoresUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.JugadorApiUseCases.ObtenerJugadorApiUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.JugadorApiUseCases.ObtenerJugadoresApiUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.MovimientoUseCases.MovimientoUseCases
import edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.MovimientoUseCases.ObtenerMovimientosDePartidaUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.MovimientoUseCases.RegistrarMovimientoUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.PartidaUseCases.CrearPartidaUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.PartidaUseCases.ObtenerPartidaApiUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.PartidaUseCases.PartidaApiUseCases
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.CreateJugadorLocalUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.EliminarJugadorUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.GuardarJugadorUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.JugadorUseCases
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.ObtenerJugadorUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.ObtenerJugadoresUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.LogrosUseCase.EliminarLogroUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.LogrosUseCase.GuardarLogroUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.LogrosUseCase.LogrosUseCases
import edu.ucne.jugadorestictactoe.domain.useCase.LogrosUseCase.ObtenerLogroUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.LogrosUseCase.ObtenerLogrosUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase.EliminarPartidaUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase.GuardarPartidaUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase.ObtenerPartidaUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase.ObtenerPartidasUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase.PartidaUseCases
import edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase.ValidarPartidaUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.TecnicoUseCase.EliminarTecnicoUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.TecnicoUseCase.GuardarTecnicoUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.TecnicoUseCase.ObtenerTecnicoUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.TecnicoUseCase.ObtenerTecnicosUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.TecnicoUseCase.TecnicoUseCases
import javax.inject.Inject
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
    @Singleton
    fun provideJugadorRepository(
        localDataSource: JugadorDao,
        remoteDataSource: JugadorRemoteDataSource
    ): JugadorRepository {
        return JugadorApiRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Provides
    fun providesJugadorDao(jugadorDb: JugadorDb) = jugadorDb.JugadorDao()
    @Provides
    fun providesPartidaDao(jugadorDb: JugadorDb)= jugadorDb.PartidaDao()
    @Provides
    fun providesLogroDao(jugadorDb: JugadorDb)= jugadorDb.LogroDao()

    @Provides
    fun provideJugadorUseCases(repository: JugadorRepository): JugadorUseCases {
        return JugadorUseCases(
            guardarJugador = GuardarJugadorUseCase(repository),
            eliminarJugador = EliminarJugadorUseCase(repository),
            obtenerJugador = ObtenerJugadorUseCase(repository),
            obtenerJugadores = ObtenerJugadoresUseCase(repository),
            createJugadorLocalUseCase = CreateJugadorLocalUseCase(repository)
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

    @Provides
    fun provideLogroUseCases(repository: LogroRepository): LogrosUseCases{
        return LogrosUseCases(
            guardarLogro = GuardarLogroUseCase(repository),
            obtenerLogros = ObtenerLogrosUseCase(repository),
            eliminarLogro = EliminarLogroUseCase(repository),
            obtenerLogro = ObtenerLogroUseCase(repository)
        )
    }

    @Provides
    fun provideTecnicoUseCases(repository: TecnicoRepository): TecnicoUseCases {
        return TecnicoUseCases(
            obtenerTecnicos = ObtenerTecnicosUseCase(repository),
            obtenerTecnico = ObtenerTecnicoUseCase(repository),
            guardarTecnico = GuardarTecnicoUseCase(repository),
            eliminarTecnico = EliminarTecnicoUseCase(repository)
        )
    }

    @Provides
    fun provideJugadorApiUseCases(repository: JugadorApiRepository): JugadoresUseCase {
        return JugadoresUseCase(
            obtenerJugadores = ObtenerJugadoresApiUseCase(repository),
            obtenerJugador = ObtenerJugadorApiUseCase(repository),
            guardarJugador = GuardarJugadorApiUseCase(repository)
        )
    }

    @Provides
    fun providePartidaApiUseCases(repository: PartidaApiRepository): PartidaApiUseCases {
        return PartidaApiUseCases(
            obtenerPartidaApi = ObtenerPartidaApiUseCase(repository),
            crearPartidaApi = CrearPartidaUseCase(repository),
        )
    }
    @Provides
    fun provideMovimientoUseCases(repository: MovimientoRepository): MovimientoUseCases {
        return MovimientoUseCases(
            crearMovimiento = RegistrarMovimientoUseCase(repository),
            obtenerMovimientosDePartida = ObtenerMovimientosDePartidaUseCase(repository)
        )
    }

    @Module
    @InstallIn(SingletonComponent::class)

    abstract class RepositoryModule {

        @Binds
        @Singleton
        abstract fun bindPartidaRepository(
            impl: PartidaRepositoryImpl
        ): PartidaRepository

        @Binds
        @Singleton
        abstract fun bindTecnicoRepository(
            impl: TecnicoRepositoryImpl
        ): TecnicoRepository


        @Binds
        @Singleton
        abstract fun bindPartidaApiRepository(
            impl: PartidaApiRepositoryImpl
        ): PartidaApiRepository

        @Binds
        @Singleton
        abstract fun bindLogroRepository(
            impl: LogroRepositoryImpl
        ): LogroRepository



        @Binds
        @Singleton
        abstract fun bindMovimientoRepository(
            impl: MovimientoApiRepositoryImpl
        ): MovimientoRepository
    }
}


