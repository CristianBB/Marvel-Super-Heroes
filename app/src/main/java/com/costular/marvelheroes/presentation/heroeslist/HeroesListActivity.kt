package com.costular.marvelheroes.presentation.heroeslist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.costular.marvelheroes.R
import com.costular.marvelheroes.data.model.MarvelHeroEntity
import com.costular.marvelheroes.presentation.MainApp
import com.costular.marvelheroes.presentation.util.Navigator
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class HeroesListActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var heroesListViewModel: HeroesListViewModel

    private val adapter = HeroesListAdapter { hero, image -> onHeroClicked(hero, image)}

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUp()
    }

    fun inject() {
        (application as MainApp).component.inject(this)
    }

    private fun setUp() {
        setUpRecycler()
        setUpViewModel()
    }

    private fun setUpRecycler() {
        heroesListRecycler.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        heroesListRecycler.itemAnimator = DefaultItemAnimator()
        heroesListRecycler.adapter = adapter
    }

    private fun setUpViewModel() {
        heroesListViewModel = ViewModelProviders.of(this, viewModelFactory).get(HeroesListViewModel::class.java)
        bindEvents()
        heroesListViewModel.loadMarvelHeroesList()
    }

    private fun bindEvents() {
        heroesListViewModel.isLoadingState.observe(this, Observer { isLoading ->
            isLoading?.let {
                showLoading(it)
            }
        })

        heroesListViewModel.heroesListState.observe(this, Observer { heroesList ->
            heroesList?.let {
                onHeroesListLoaded(it)
            }
        })
    }

    private fun onHeroesListLoaded(heroesList: List<MarvelHeroEntity>) {
        adapter.submitList(heroesList)
    }

    private fun showLoading(isLoading: Boolean) {
        heroesListLoading.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    private fun onHeroClicked(hero: MarvelHeroEntity, image: View) {
        navigator.goToHeroDetail(this, hero, image)
    }


}
