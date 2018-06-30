package com.costular.marvelheroes.di.modules

import com.costular.marvelheroes.data.model.mapper.MarvelHeroMapper
import com.costular.marvelheroes.data.net.MarvelHeroesService
import com.costular.marvelheroes.data.repository.MarvelHeroesRepository
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
    fun provideRemoteMarvelHeroesDataSource(marvelHeroesService: MarvelHeroesService,
                                            marvelHeroesMapper: MarvelHeroMapper): RemoteMarvelHeroesDataSource =
            RemoteMarvelHeroesDataSource(marvelHeroesService, marvelHeroesMapper)

    @Provides
    @Singleton
    fun provideMarvelHeroesRepository(
            marvelRemoteMarvelHeroesDataSource: RemoteMarvelHeroesDataSource): MarvelHeroesRepository =
            MarvelHeroesRepository(marvelRemoteMarvelHeroesDataSource)

}