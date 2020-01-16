package com.badzohugues.playground

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Property
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView

class MainActivity : AppCompatActivity() {

    private lateinit var btnFavorite: FrameLayout
    private lateinit var imFavorite: AppCompatImageView
    private lateinit var imFavoriteBorder: AppCompatImageView
    private lateinit var imStartCircle: AppCompatImageView
    private lateinit var imEndCircle: AppCompatImageView
    private var isPushed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initListeners()
        resetAlpha()
    }

    private fun initView() {
        btnFavorite = findViewById(R.id.main_button_favorite)
        imFavorite = findViewById(R.id.main_image_favorite)
        imFavoriteBorder = findViewById(R.id.main_image_favorite_border)
        imStartCircle = findViewById(R.id.main_image_startcircle)
        imEndCircle = findViewById(R.id.main_image_endcircle)
    }

    private fun initListeners() {
        btnFavorite.setOnClickListener {
            if (!isPushed) launchAnimation()
            else resetAlpha()

            isPushed = !isPushed
        }
    }

    private fun launchAnimation() {
        val animatorSet = AnimatorSet()

        animatorSet.playTogether(
            createAnimatorObject(imStartCircle, View.SCALE_X),  // #1
            createAnimatorObject(imStartCircle, View.SCALE_Y)   // #1
        )

        animatorSet.play(createAnimatorObject(imFavorite, View.SCALE_X))    // #2
            .with(createAnimatorObject(imFavorite, View.SCALE_Y))   // #2
            .with(createAnimatorObject(imStartCircle, View.ALPHA, false))   // #2
            .with(createAnimatorObject(imFavorite, View.ALPHA)) // #2
            .after(createAnimatorObject(imStartCircle, View.ALPHA)) // #1

        animatorSet.play(createAnimatorObject(imEndCircle, View.SCALE_X))
            .with(createAnimatorObject(imEndCircle, View.SCALE_Y))
            .after(500)
            .with(createAnimatorObject(imEndCircle, View.ALPHA, false))
            .with(createAnimatorObject(imFavorite, View.ALPHA))
            .with(createAnimatorObject(imStartCircle, View.ALPHA, false))   // #2

        animatorSet.start()
    }

    private fun createAnimatorObject(view: View, type: Property<View, Float>, isAscendant: Boolean = true, duration: Long = 2000): ObjectAnimator {
        val objectAnimator = ObjectAnimator.ofFloat(
            view,
            type,
            if (isAscendant) 0f else 1f,
            if (isAscendant) 1f else 0f
        )
        objectAnimator.duration = duration

        return objectAnimator
    }

    private fun resetAlpha() {
        imFavorite.alpha = 0f
        imFavoriteBorder.alpha = 1f
        imStartCircle.alpha = 0f
        imEndCircle.alpha = 0f
    }
}
