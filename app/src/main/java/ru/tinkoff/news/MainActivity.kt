package ru.tinkoff.news

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.AppBarLayout
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import ru.tinkoff.news.commons.Constants
import ru.tinkoff.news.events.GoToFragment
import ru.tinkoff.news.events.SyncToggle
import ru.tinkoff.news.fragments.main.MainFragment


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var header: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        App.appComponent.inject(this)

        supportFragmentManager.addOnBackStackChangedListener(getBackListener())

        val mToolBar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolBar)
        val mAppBarLayout = findViewById<AppBarLayout>(R.id.appBar)
        mAppBarLayout.bringToFront()
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        setFragment(MainFragment(), false, Constants.F_MAIN)

    }

    override fun onResume() {
        super.onResume()
        header = nav_view.getHeaderView(0)
        val btnHeader = header.findViewById<Button>(R.id.btn_header)
        btnHeader.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
            setFragment(MainFragment(),false, Constants.F_MAIN) }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setFragment(fragment: Fragment, addToBackStack: Boolean, tag: String) {
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()

        if (addToBackStack) {
            ft.addToBackStack(tag)
            ft.add(R.id.container_frame, fragment, tag)
        } else {
            ft.replace(R.id.container_frame, fragment, tag)
        }
        ft.commitAllowingStateLoss()
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGoToFragment(event: GoToFragment) {
        setFragment(GoToFragment.m_fragment_class!!, GoToFragment.m_toBack, GoToFragment.m_fragment_type)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSyncToggle(event: SyncToggle) {
        syncDrawerState()
    }


    private fun syncDrawerState() {
        Handler().post {
            if ((supportActionBar!!.displayOptions and ActionBar.DISPLAY_HOME_AS_UP) != ActionBar.DISPLAY_HOME_AS_UP) {
                drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                toolbar.setNavigationIcon(R.drawable.ic_hamburger)
                toggle = ActionBarDrawerToggle(
                        this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
                drawer_layout.addDrawerListener(toggle)
                toggle.syncState()

            } else if ((supportActionBar!!.displayOptions and ActionBar.DISPLAY_HOME_AS_UP) == ActionBar.DISPLAY_HOME_AS_UP) {
                drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                toolbar.setNavigationIcon(R.drawable.ic_triangle)
                toolbar.setNavigationOnClickListener{
                    supportFragmentManager.popBackStackImmediate()
                }
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }

    }

    private fun getCurrentFragment(): Fragment {
        val fragmentManager = supportFragmentManager
        var fragmentTag = fragmentManager.getBackStackEntryAt(0).name
        if (fragmentManager.backStackEntryCount > 1)
            fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1).name
        return fragmentManager.findFragmentByTag(fragmentTag)
    }

    private fun getBackListener(): FragmentManager.OnBackStackChangedListener {

        return FragmentManager.OnBackStackChangedListener {
            val manager = supportFragmentManager

            if (manager != null && manager.backStackEntryCount>0) {
                val currFrag = getCurrentFragment()

                currFrag.onResume()
            }
        }
    }

}
