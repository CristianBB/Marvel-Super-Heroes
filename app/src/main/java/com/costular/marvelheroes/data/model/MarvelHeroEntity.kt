package com.costular.marvelheroes.data.model

import android.annotation.SuppressLint
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.os.Parcelable
import com.costular.marvelheroes.data.db.Converters
import kotlinx.android.parcel.Parcelize

/**
 * Created by costular on 17/03/2018.
 */
@TypeConverters(Converters::class)
@Entity(tableName = "heroes")
@SuppressLint("ParcelCreator")
@Parcelize
data class MarvelHeroEntity(
        @PrimaryKey
        val name: String,
        val photoUrl: String,
        val realName: String,
        val height: String,
        val power: String,
        val abilities: String,
        val groups: Array<String>,
        var isFavorite: Boolean,
        var valoration: Int,
        var review: String
) : Parcelable

