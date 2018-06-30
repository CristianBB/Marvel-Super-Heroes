package com.costular.marvelheroes.presentation.heroeslist

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.costular.marvelheroes.data.repository.MarvelHeroesRepository
import com.costular.marvelheroes.data.model.MarvelHeroEntity
import com.costular.marvelheroes.presentation.util.mvvm.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HeroesListViewModel @Inject constructor(val marvelHeroesRepository: MarvelHeroesRepository): BaseViewModel() {

    val heroesListState: MutableLiveData<List<MarvelHeroEntity>> = MutableLiveData()
    val isLoadingState: MutableLiveData<Boolean> = MutableLiveData()

    fun loadMarvelHeroesList() {
        marvelHeroesRepository.getMarvelHeroesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {isLoadingState.postValue(true)}
                .doOnTerminate {isLoadingState.postValue(false)}
                .subscribeBy(
                        onNext = {
                            heroesListState.value = it
                        },
                        onError = {
                            Log.d("HeroesListViewModel", it.toString())
                        }
                )
                .addTo(compositeDisposable)
    }

}