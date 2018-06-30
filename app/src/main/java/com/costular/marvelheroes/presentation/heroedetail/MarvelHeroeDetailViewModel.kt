package com.costular.marvelheroes.presentation.heroedetail

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.costular.marvelheroes.data.model.MarvelHeroEntity
import com.costular.marvelheroes.data.repository.MarvelHeroesRepository
import com.costular.marvelheroes.presentation.util.mvvm.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MarvelHeroeDetailViewModel @Inject constructor(val marvelHeroesRepository: MarvelHeroesRepository): BaseViewModel() {

    val heroeState: MutableLiveData<MarvelHeroEntity> = MutableLiveData()
    val heroeUpdated: MutableLiveData<Boolean> = MutableLiveData()

    fun getHero(heroName: String) {
        marvelHeroesRepository.getMarvelHero(heroName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            heroeState.value = it
                        },
                        onError = {
                            Log.d("DetailViewModel", it.toString())
                        }
                )
                .addTo(compositeDisposable)
    }

    fun updateHeroe(hero: MarvelHeroEntity) {
        // Actualiza en repositorio local
        marvelHeroesRepository.updateMarvelHero(hero)

        // Informa del cambio de estado
        heroeState.value = hero
        heroeUpdated.value = true

    }


}