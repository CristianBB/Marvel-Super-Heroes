package com.costular.marvelheroes.data.db

import android.arch.persistence.room.*
import com.costular.marvelheroes.data.model.MarvelHeroEntity
import io.reactivex.Maybe

@Dao
abstract class MarvelHeroDao {

    @Query("SELECT * FROM heroes")
    abstract fun getAllHeroes(): Maybe<List<MarvelHeroEntity>>

    @Query("SELECT * FROM heroes WHERE name = :name")
    abstract fun getHero(name: String): Maybe<MarvelHeroEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertAll(heroes: List<MarvelHeroEntity>)

    @Update
    abstract fun update(heroe: MarvelHeroEntity)

}