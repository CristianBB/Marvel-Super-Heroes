package com.costular.marvelheroes.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.costular.marvelheroes.data.model.MarvelHeroEntity



@Database(entities = [MarvelHeroEntity::class], version = 3)
abstract class MarvelHeroDatabase: RoomDatabase() {

    abstract fun getHeroDao(): MarvelHeroDao

}