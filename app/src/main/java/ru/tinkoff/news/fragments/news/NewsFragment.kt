package ru.tinkoff.news.fragments.news


import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.fragment_news_id.*
import ru.tinkoff.news.App
import ru.tinkoff.news.R
import ru.tinkoff.news.api.models.NewsIdResponse
import ru.tinkoff.news.fragments.BaseFragment
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class NewsFragment : BaseFragment(), NewsContract.View, SwipeRefreshLayout.OnRefreshListener {

    var root : View? = null
    lateinit var news: NewsIdResponse

    @Inject
    lateinit var mPresenter: NewsContract.Presenter

    companion object {
        private const val NEWS_ID = "news_id"

        fun newInstance(aNewsId: String) = NewsFragment().apply {
            arguments = bundleOf(NEWS_ID to aNewsId)
        }
    }

    private val newsId: String by lazy { this.arguments!!.getString(NEWS_ID) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_news_id, container, false)
        App.appComponent.inject(this)
        mPresenter.attach(this.context!!, this)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout.setOnRefreshListener(this)
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    override fun onRefresh() {
        mPresenter.doSync(newsId)
    }

    override fun updateScreen(news: NewsIdResponse) {
        this.news = news

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            content_news.text = Html.fromHtml(news.payload.content, Html.FROM_HTML_MODE_LEGACY)
        } else {
            content_news.text = Html.fromHtml(news.payload.content)
        }
        content_news.setMovementMethod(LinkMovementMethod.getInstance())
    }

    override fun setRefreshing(refreshing: Boolean) {
        swipeRefreshLayout.isRefreshing = refreshing
    }

}
