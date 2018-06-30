package com.costular.marvelheroes.presentation.heroeslist

import android.arch.lifecycle.MutableLiveData
import com.costular.marvelheroes.domain.model.MarvelHeroEntity
import com.costular.marvelheroes.domain.usecase.GetMarvelHeroesList
import com.costular.marvelheroes.presentation.util.mvvm.BaseViewModel
import javax.inject.Inject

class HeroesListViewModel @Inject constructor(): BaseViewModel() {

    val heroListState: MutableLiveData<List<MarvelHeroEntity>> = MutableLiveData()
    val isLoadingState: MutableLiveData<Boolean> = MutableLiveData()

    fun loadMarvelHeroes() {


        view.showLoading(true)
        getMarvelHeroesList.execute({ heroes ->
            view.showHeroesList(heroes)
            view.showLoading(false)
        }, {
            view.showError(it.toString())
            view.showLoading(false)
        })
    }

}