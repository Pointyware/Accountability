/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.feature.about

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView

/**
 *
 * @author taushsampley
 */
open class SpinnerItemView(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {

    lateinit var textView : TextView

    override fun onFinishInflate() {
        super.onFinishInflate()

        textView = findViewById(R.id.spinner_text)
    }

    companion object {

        fun createView(context: Context, parent: ViewGroup?): SpinnerItemView {
            return LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false) as SpinnerItemView
        }
    }
}
