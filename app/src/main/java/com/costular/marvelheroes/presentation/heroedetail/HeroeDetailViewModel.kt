package com.costular.marvelheroes.presentation.heroedetail

import android.arch.lifecycle.MutableLiveData
import com.costular.marvelheroes.data.model.MarvelHeroEntity
import com.costular.marvelheroes.presentation.util.mvvm.BaseViewModel

class HeroeDetailViewModel: BaseViewModel() {

    val heroeState: MutableLiveData<MarvelHeroEntity> = MutableLiveData()

}