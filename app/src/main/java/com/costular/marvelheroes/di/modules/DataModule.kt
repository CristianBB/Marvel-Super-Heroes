package com.costular.marvelheroes.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.costular.marvelheroes.data.db.MarvelHeroDatabase
import com.costular.marvelheroes.data.model.mapper.MarvelHeroMapper
import com.costular.marvelheroes.data.net.MarvelHeroesService
import com.costular.marvelheroes.data.repository.MarvelHeroesRepository
import com.costular.marvelheroes.data.repository.datasource.LocalMarvelHeroesDataSource
import com.costular.marvelheroes.data.repository.datasource.RemoteMarvelHeroesDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by costular on 17/03/2018.
 */
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideMarvelHeroMapper(): MarvelHeroMapper = MarvelHeroMapper()

    @Provides
    @Singleton
    fun provideDatabase(context: Context): MarvelHeroDatabase =
            Room.databaseBuilder(context, MarvelHeroDatabase::class.java, "hero.db")
                    .fallbackToDestructiveMigration()
                    .build()

    @Provides
    @Singleton
    fun provideLocalMarvelHeroesDataSource(marvelHeroDatabase: MarvelHeroDatabase): LocalMarvelHeroesDataSource =
            LocalMarvelHeroesDataSource(marvelHeroDatabase)

    @Provides
    @Singleton
    fun provideRemoteMarvelHeroesDataSource(marvelHeroesService: MarvelHeroesService,
                                            marvelHeroesMapper: MarvelHeroMapper): RemoteMarvelHeroesDataSource =
            RemoteMarvelHeroesDataSource(marvelHeroesService, marvelHeroesMapper)

    @Provides
    @Singleton
    fun provideMarvelHeroesRepository(localMarvelHeroesDataSource: LocalMarvelHeroesDataSource,
                                      marvelRemoteMarvelHeroesDataSource: RemoteMarvelHeroesDataSource): MarvelHeroesRepository =
            MarvelHeroesRepository(localMarvelHeroesDataSource, marvelRemoteMarvelHeroesDataSource)

}