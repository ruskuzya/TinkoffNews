package ru.tinkoff.news.fragments.main


import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_main.*
import ru.tinkoff.news.App
import ru.tinkoff.news.R
import ru.tinkoff.news.db.models.NewsObj
import ru.tinkoff.news.fragments.BaseFragment
import javax.inject.Inject
import org.greenrobot.eventbus.EventBus
import ru.tinkoff.news.commons.Constants
import ru.tinkoff.news.events.GoToFragment
import ru.tinkoff.news.fragments.news.NewsFragment


/**
 * A simple [Fragment] subclass.
 *
 */
class MainFragment : BaseFragment(), MainContract.View, SwipeRefreshLayout.OnRefreshListener, MainRecyclerViewAdapter.OnItemClickListener {

    var root : View? = null
    private lateinit var mNews: RealmResults<NewsObj>
    private lateinit var mRealm: Realm
    private lateinit var adapter: MainRecyclerViewAdapter

    @Inject
    lateinit var mPresenter: MainContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_main, container, false)
        App.appComponent.inject(this)
        mPresenter.attach(this.context!!, this)

        mRealm = Realm.getDefaultInstance()

        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout.setOnRefreshListener(this)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        adapter = MainRecyclerViewAdapter(mRealm.where(NewsObj::class.java).findAll(), this)
        mRecyclerView.setLayoutManager(LinearLayoutManager(this.context!!))
        mRecyclerView.setAdapter(adapter)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.addItemDecoration(DividerItemDecoration(this.context!!, DividerItemDecoration.VERTICAL))
    }

    override fun onResume() {
        super.onResume()
        val activity = activity as AppCompatActivity?
        if (activity != null) {
            activity.supportActionBar!!.show()
            activity.supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent, activity.theme)))
            activity.supportActionBar!!.setDisplayShowTitleEnabled(false)
        }

        onRefresh()
    }

    override fun onRefresh() {
        mPresenter.doSync()
    }

    override fun setRefreshing(refreshing: Boolean) {
        swipeRefreshLayout.isRefreshing = refreshing
    }

    override fun onItemClick(item: NewsObj) {
        val toFragment = GoToFragment(NewsFragment.newInstance(item.id),
                Constants.F_NEWS, true)
        EventBus.getDefault().post(toFragment)
    }

}
