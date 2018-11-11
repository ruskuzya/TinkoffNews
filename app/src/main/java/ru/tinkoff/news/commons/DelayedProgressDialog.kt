package ru.tinkoff.news.commons

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Handler
import android.support.annotation.NonNull
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.widget.ProgressBar
import ru.tinkoff.news.R

class DelayedProgressDialog : DialogFragment() {

    private var mProgressBar: ProgressBar? = null
    private var startedShowing: Boolean = false
    private var mStartMillisecond: Long = 0
    private var mStopMillisecond: Long = 0

    companion object {
        private const val DELAY_MILLISECOND = 450L
        private const val SHOW_MIN_MILLISECOND = 300L
        private const val PROGRESS_CONTENT_SIZE_DP : Int = 80
    }

    @NonNull
    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = this.activity?.layoutInflater

        builder.setView(inflater!!.inflate(R.layout.dialog_progress, null))
        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        mProgressBar = dialog.findViewById(R.id.progress)

        if (dialog.window != null) {
            val px = PROGRESS_CONTENT_SIZE_DP * this.resources.displayMetrics.density.toInt()
            dialog.window.setLayout(px, px)
            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun show(fm: FragmentManager?, tag: String) {
        mStartMillisecond = System.currentTimeMillis()
        startedShowing = false
        mStopMillisecond = java.lang.Long.MAX_VALUE

        val handler = Handler()
        handler.postDelayed({
            if (mStopMillisecond > System.currentTimeMillis())
                showDialogAfterDelay(fm, tag)
        }, DELAY_MILLISECOND)
    }

    private fun showDialogAfterDelay(fm: FragmentManager?, tag: String) {
        startedShowing = true
        val ft = fm?.beginTransaction()
        ft?.add(this, tag)
        ft?.commitAllowingStateLoss()
    }

    fun cancel() {
        mStopMillisecond = System.currentTimeMillis()

        if (startedShowing) {
            if (mProgressBar != null) {
                cancelWhenShowing()
            } else {
                cancelWhenNotShowing()
            }
        }
    }

    private fun cancelWhenShowing() {
        if (mStopMillisecond < mStartMillisecond + DELAY_MILLISECOND + SHOW_MIN_MILLISECOND) {
            val handler = Handler()
            handler.postDelayed({ dismissAllowingStateLoss() }, SHOW_MIN_MILLISECOND)
        } else {
            dismissAllowingStateLoss()
        }
    }

    private fun cancelWhenNotShowing() {
        val handler = Handler()
        handler.postDelayed({ dismissAllowingStateLoss() }, DELAY_MILLISECOND)
    }


}