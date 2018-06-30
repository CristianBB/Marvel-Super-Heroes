package com.costular.marvelheroes.data.repository.datasource

import com.costular.marvelheroes.data.model.mapper.MarvelHeroMapper
import com.costular.marvelheroes.data.net.MarvelHeroesService
import com.costular.marvelheroes.data.model.MarvelHeroEntity
import io.reactivex.Flowable

/**
 * Created by costular on 17/03/2018.
 */
class RemoteMarvelHeroesDataSource(private val marvelHeroesService: MarvelHeroesService,
                                   private val marvelHeroMapper: MarvelHeroMapper):
        MarvelHeroesDataSource {

    override fun getMarvelHeroesList(): Flowable<List<MarvelHeroEntity>> =
            marvelHeroesService.getMarvelHeroesList()
                    .map { it.superheroes }
                    .map {marvelHeroMapper.transformList(it)}

}