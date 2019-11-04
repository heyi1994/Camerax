package org.melrose.camerax.ui

import android.util.Log
import android.view.TextureView
import android.view.View
import org.jetbrains.anko.*
import org.melrose.camerax.MainActivity

/**
 * @author NOBODY
 * @since 1.0.0
 */
class MainActivityUi :AnkoComponent<MainActivity> {
    var texture: TextureView?=null
    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        textureView { texture = this }
    }
}