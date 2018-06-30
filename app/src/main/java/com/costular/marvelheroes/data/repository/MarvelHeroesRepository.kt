package com.costular.marvelheroes.data.repository

import com.costular.marvelheroes.data.repository.datasource.RemoteMarvelHeroesDataSource
import com.costular.marvelheroes.data.model.MarvelHeroEntity
import com.costular.marvelheroes.data.repository.datasource.LocalMarvelHeroesDataSource
import io.reactivex.Flowable

/**
 * Created by costular on 17/03/2018.
 */
class MarvelHeroesRepository (private val localMarvelHeroesDataSource: LocalMarvelHeroesDataSource,
                              private val remoteMarvelHeroesDataSource: RemoteMarvelHeroesDataSource) {

    fun getMarvelHeroesList(): Flowable<List<MarvelHeroEntity>> =
            getMarvelHeroesFromDb().concatWith(getMarvelHeroesFromRemote())

    private fun getMarvelHeroesFromDb(): Flowable<List<MarvelHeroEntity>> = localMarvelHeroesDataSource.getMarvelHeroesList()

    private fun getMarvelHeroesFromRemote(): Flowable<List<MarvelHeroEntity>> =
            remoteMarvelHeroesDataSource.getMarvelHeroesList()
                    .doOnNext{localMarvelHeroesDataSource.saveMarvelHeroes(it)}
}