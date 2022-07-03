package com.link.worldwidenews.ui

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.link.worldwidenews.R
import com.link.worldwidenews.databinding.ActivityMainBinding
import com.link.worldwidenews.databinding.LayoutNavMenuBinding
import com.link.worldwidenews.ui.home.HomeFragment
import com.link.worldwidenews.utils.extensions.getCurrentFragment
import com.link.worldwidenews.utils.extensions.isVisible
import com.link.worldwidenews.utils.extensions.searchManager
import com.link.worldwidenews.utils.extensions.showToastMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var _navMenuBinding: LayoutNavMenuBinding? = null
    private val navMenuBinding get() = _navMenuBinding!!
    private var navController: NavController? = null
    private var appBarConfiguration: AppBarConfiguration? = null

    private val mainViewModel by viewModels<MainViewModel>()

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        _navMenuBinding = LayoutNavMenuBinding.inflate(layoutInflater, binding.navView, true)
        val view = binding.root
        setContentView(view)

        setupView()
    }

    private fun setupView() {
        setupNavigationComponent()
        setupMenuButtonClick()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)

        searchView = menu.findItem(R.id.menu_search)?.actionView as SearchView

        searchView.setSearchableInfo(
            searchManager
                .getSearchableInfo(componentName)
        )
        searchView.setMaxWidth(Int.MAX_VALUE)

        // listening to search query text change
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // filter recycler view when query submitted
                mainViewModel.searchInNewsList(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // filter recycler view when text is changed
                mainViewModel.searchInNewsList(newText.toString())
                return false
            }
        })
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (navController == null || appBarConfiguration == null)
            super.onSupportNavigateUp()
        else NavigationUI.navigateUp(navController!!, appBarConfiguration!!)
                || super.onSupportNavigateUp()

    }

    private fun setupNavigationComponent() {
        setSupportActionBar(binding.toolbar)
        appBarConfiguration = AppBarConfiguration.Builder(R.id.homeFragment)
            .setOpenableLayout(binding.drawerLayout)
            .build()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        navController = navHostFragment?.navController
        navController?.let {
            appBarConfiguration?.let { appBarConfiguration ->
                NavigationUI.setupActionBarWithNavController(
                    this,
                    it, appBarConfiguration
                )
            }

            NavigationUI.setupWithNavController(
                binding.navView,
                it
            )

        }
        navController?.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.newsDetailsFragment) {
                if (::searchView.isInitialized) {
                    searchView.visibility = View.GONE
                    searchView.onActionViewCollapsed()
                }
            } else {
                if (::searchView.isInitialized) {
                    searchView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupMenuButtonClick() {
        navMenuBinding.eMagazineButton.setOnClickListener {
            showToastMessage(R.string.e_magazine)
            binding.drawerLayout.closeDrawers()
            updateSelectedSideMenuItem(it.id)

        }
        navMenuBinding.wishListButton.setOnClickListener {
            showToastMessage(R.string.wish_list)
            binding.drawerLayout.closeDrawers()
            updateSelectedSideMenuItem(it.id)

        }

        navMenuBinding.galleryButton.setOnClickListener {
            showToastMessage(R.string.gallery)
            binding.drawerLayout.closeDrawers()
            updateSelectedSideMenuItem(it.id)

        }

        navMenuBinding.liveChatButton.setOnClickListener {
            showToastMessage(R.string.live_chat)
            binding.drawerLayout.closeDrawers()
            updateSelectedSideMenuItem(it.id)

        }
        navMenuBinding.exploreButton.setOnClickListener {
            showToastMessage(R.string.explore)
            binding.drawerLayout.closeDrawers()
            updateSelectedSideMenuItem(it.id)

        }

    }

    private fun updateSelectedSideMenuItem(id: Int) {
        navMenuBinding.exploreSelectImageView.visibility =
            if (id == R.id.explore_button) View.VISIBLE else View.INVISIBLE
        navMenuBinding.liveChatSelectImageView.visibility =
            if (id == R.id.live_chat_button) View.VISIBLE else View.INVISIBLE
        navMenuBinding.gallerySelectImageView.visibility =
            if (id == R.id.gallery_button) View.VISIBLE else View.INVISIBLE
        navMenuBinding.wishListSelectImageView.visibility =
            if (id == R.id.wish_list_button) View.VISIBLE else View.INVISIBLE
        navMenuBinding.eMagazineSelectImageView.visibility =
            if (id == R.id.e_magazine_button) View.VISIBLE else View.INVISIBLE
    }
}