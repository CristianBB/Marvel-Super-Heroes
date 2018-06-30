package com.costular.marvelheroes.data.repository.datasource

import com.costular.marvelheroes.data.model.MarvelHeroEntity
import io.reactivex.Flowable

/**
 * Created by costular on 17/03/2018.
 */
interface MarvelHeroesDataSource {

    fun getMarvelHeroesList(): Flowable<List<MarvelHeroEntity>>

}