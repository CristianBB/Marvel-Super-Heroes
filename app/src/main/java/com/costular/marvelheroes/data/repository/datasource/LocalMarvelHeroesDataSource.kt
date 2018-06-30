package com.costular.marvelheroes.data.repository.datasource

import com.costular.marvelheroes.data.db.MarvelHeroDatabase
import com.costular.marvelheroes.data.model.MarvelHeroEntity
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class LocalMarvelHeroesDataSource(val marvelHeroDatabase: MarvelHeroDatabase): MarvelHeroesDataSource {

    override fun getMarvelHeroesList(): Flowable<List<MarvelHeroEntity>> =
            marvelHeroDatabase
                    .getHeroDao()
                    .getAllHeroes()
                    .toFlowable()

    fun saveMarvelHeroes(marvelHeroes: List<MarvelHeroEntity>) {
        Observable.fromCallable{
            marvelHeroDatabase.getHeroDao().insertAll(marvelHeroes)
        }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun updateMarvelHero(marvelHero: MarvelHeroEntity) {
        Observable.fromCallable{
            marvelHeroDatabase.getHeroDao().update(marvelHero)
        }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun getMarvelHero(name: String): Flowable<MarvelHeroEntity> =
            marvelHeroDatabase
                    .getHeroDao()
                    .getHero(name)
                    .toFlowable()
}