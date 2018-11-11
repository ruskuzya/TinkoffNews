package ru.tinkoff.news.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.tinkoff.news.R


class Dialog : DialogFragment(), View.OnClickListener {

    private var rootView: View? = null
    private var message: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        message = arguments?.getString(ARG_MESSAGE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_dialog, null)
        (rootView!!.findViewById(R.id.message) as TextView).text = message
        rootView!!.findViewById<View>(R.id.btn_ok).setOnClickListener(this)

        return rootView
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_ok -> onDestroyView()
            else -> {
            }
        }
    }

    companion object {
        private const val ARG_MESSAGE = "message"

        fun newInstance(message: String): Dialog {
            val f = Dialog()
            f.setStyle(DialogFragment.STYLE_NO_TITLE, 0)
            val args = Bundle()
            args.putString(ARG_MESSAGE, message)
            f.arguments = args

            return f
        }
    }

}