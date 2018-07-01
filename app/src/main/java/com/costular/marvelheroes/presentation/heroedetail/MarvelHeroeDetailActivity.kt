package com.costular.marvelheroes.presentation.heroedetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.costular.marvelheroes.R
import com.costular.marvelheroes.data.model.MarvelHeroEntity
import com.costular.marvelheroes.presentation.MainApp
import kotlinx.android.synthetic.main.activity_hero_detail.*
import javax.inject.Inject
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.graphics.drawable.toBitmap


/**
 * Created by costular on 18/03/2018.
 */
class MarvelHeroeDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var marvelHeroeDetailViewModel: MarvelHeroeDetailViewModel

    companion object {
        const val PARAM_HEROE_ID = "heroe_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_detail)
        setUp()
    }

    fun inject() {
        (application as MainApp).component.inject(this)
    }

    fun setUp() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
        supportPostponeEnterTransition() // Wait for image load and then draw the animation
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) // Evita que el teclado aparezca al iniciar la Activity
        setUpViewModel()
    }

    private fun setUpViewModel() {
        marvelHeroeDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(MarvelHeroeDetailViewModel::class.java)
        bindEvents()
        val heroName: String = intent.getStringExtra(PARAM_HEROE_ID)
        marvelHeroeDetailViewModel.getHero(heroName)
    }

    private fun bindEvents() {

        marvelHeroeDetailViewModel.heroeState.observe(this, Observer { heroesList ->
            heroesList?.let {
                fillHeroData(it)
            }
        })

        marvelHeroeDetailViewModel.heroeUpdated.observe(this, Observer {
            Toast.makeText(this, "Marvel Heroe updated!", Toast.LENGTH_SHORT).show()
        })
    }

    private fun fillHeroData(hero: MarvelHeroEntity) {
        Glide.with(this)
                .load(hero.photoUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }
                })
                .into(heroDetailImage)

        heroDetailName.text = hero.name
        heroDetailRealName.text = hero.realName
        heroDetailHeight.text = hero.height
        heroDetailPower.text = hero.power
        heroDetailAbilities.text = hero.abilities
        heroDetailReviewComments.setText(hero.review)

        if (hero.isFavorite) {
            heroFavoriteButton.setImageResource(R.drawable.ic_favorite_true)
        } else {
            heroFavoriteButton.setImageResource(R.drawable.ic_favorite_false)
        }

        // Valoración
        val rangeTo = heroDetailValorationContainer.childCount - 1
        for (i in 0..rangeTo) {

            val child = heroDetailValorationContainer.getChildAt(i)

            if (child is ImageView) {
                val tag = child.tag.toString()
                val tagInt = tag.toInt()

                // Listener de Valoracion
                child.setOnClickListener{
                    onValorationClicked(it, tagInt, hero)
                }

                // Pintado de valoración actual
                if (tagInt <= hero.valoration) {
                    child.setImageResource(R.drawable.ic_star)
                } else {
                    child.setImageResource(R.drawable.ic_star_border)
                }
            }
        }

        // Listener de botón Favorito
        heroFavoriteButton.setOnClickListener{button ->
            onFavoriteClicked(button, hero)
        }

        // Listener de botón Guardar Comentarios
        heroDetailReviewButton.setOnClickListener {
            onSaveCommentsClicked(it, hero)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onFavoriteClicked(view: View, hero: MarvelHeroEntity) {
        hero.isFavorite = !hero.isFavorite
        marvelHeroeDetailViewModel.updateHeroe(hero)

    }

    private fun onValorationClicked(view: View, valoration: Int, hero: MarvelHeroEntity) {
        hero.valoration = valoration
        marvelHeroeDetailViewModel.updateHeroe(hero)
    }

    private fun onSaveCommentsClicked(view: View, hero: MarvelHeroEntity) {
        hero.review = heroDetailReviewComments.text.toString()
        marvelHeroeDetailViewModel.updateHeroe(hero)
    }
}