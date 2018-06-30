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

    fun getMarvelHero(name: String): Flowable<MarvelHeroEntity> = localMarvelHeroesDataSource.getMarvelHero(name)

    fun getMarvelHeroesList(): Flowable<List<MarvelHeroEntity>> =
            getMarvelHeroesFromDb().concatWith(getMarvelHeroesFromRemote())

    fun updateMarvelHero(hero: MarvelHeroEntity) = localMarvelHeroesDataSource.updateMarvelHero(hero)

    private fun getMarvelHeroesFromDb(): Flowable<List<MarvelHeroEntity>> = localMarvelHeroesDataSource.getMarvelHeroesList()

    private fun getMarvelHeroesFromRemote(): Flowable<List<MarvelHeroEntity>> =
            remoteMarvelHeroesDataSource.getMarvelHeroesList()
                    .doOnNext{localMarvelHeroesDataSource.saveMarvelHeroes(it)}

}