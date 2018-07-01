package com.costular.marvelheroes.repository

import com.costular.marvelheroes.data.model.MarvelHero
import com.costular.marvelheroes.data.model.MarvelHeroEntity
import com.costular.marvelheroes.data.model.mapper.MarvelHeroMapper
import com.costular.marvelheroes.data.repository.MarvelHeroesRepository
import com.costular.marvelheroes.data.repository.datasource.LocalMarvelHeroesDataSource
import com.costular.marvelheroes.data.repository.datasource.RemoteMarvelHeroesDataSource
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Flowable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

/**
 * Created by costular on 17/03/2018.
 */
class MarvelHeroesRepositoryTest {

    private val mockRemoteDataSource: RemoteMarvelHeroesDataSource = mock()
    private val mockLocalDataSource: LocalMarvelHeroesDataSource = mock()

    private lateinit var mapper: MarvelHeroMapper
    private lateinit var marvelHeroesRepository: MarvelHeroesRepository

    @Before
    fun setUp() {
        mapper = MarvelHeroMapper()
        marvelHeroesRepository = MarvelHeroesRepository(mockLocalDataSource, mockRemoteDataSource)
    }

    @Test
    fun `repository should retrieve marvel heroes list`() {
        val heroes = listOf(MarvelHeroEntity("Iron Man","","","","","", arrayOf(""),false,0,""), MarvelHeroEntity("Spider-Man", "","","","","", arrayOf(""),false,0,""))
        val observable = Flowable.just(heroes)
        whenever(mockLocalDataSource.getMarvelHeroesList()).thenReturn(observable)
        whenever(mockRemoteDataSource.getMarvelHeroesList()).thenReturn(observable)

        val result = marvelHeroesRepository.getMarvelHeroesList()

        verify(mockLocalDataSource, atLeastOnce()).getMarvelHeroesList()
        verify(mockRemoteDataSource).getMarvelHeroesList()

    }

}