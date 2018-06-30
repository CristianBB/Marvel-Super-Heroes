package com.costular.marvelheroes.data.repository

import com.costular.marvelheroes.data.model.mapper.MarvelHeroMapper
import com.costular.marvelheroes.data.repository.datasource.RemoteMarvelHeroesDataSource
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import io.reactivex.Flowable
import io.reactivex.Observable

/**
 * Created by costular on 17/03/2018.
 */
class MarvelHeroesRepository (private val remoteMarvelHeroesDataSource: RemoteMarvelHeroesDataSource) {

    fun getMarvelHeroesList(): Flowable<List<MarvelHeroEntity>> = remoteMarvelHeroesDataSource.getMarvelHeroesList()

}