package com.nasaImages.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.drawable.ScaleDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nasaImages.R
import com.nasaImages.model.Item
import com.nasaImages.model.MainViewModel
import com.nasaImages.repository.NasaImagesRepositoryImpl
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class XmlFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private var items: MutableList<Item> = mutableListOf()
    private var adapter: ResultsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_xml, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val searchTextView = view.findViewById<EditText>(R.id.search_bar)
        val searchButton = view.findViewById<Button>(R.id.search_button)
        val resultsRecyclerView = view.findViewById<RecyclerView>(R.id.results_recycler_view)

        mainViewModel = MainViewModel(NasaImagesRepositoryImpl())

        searchButton.setOnClickListener {
            mainViewModel.updateSearchQuery(searchTextView.text.toString())
            mainViewModel.performSearch(1)
            hideSoftwareKeyboard(requireActivity())
        }

        adapter = ResultsAdapter(items, requireContext())
        resultsRecyclerView.adapter = adapter
        resultsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        observeSearchDataUpdates()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeSearchDataUpdates() {
        lifecycleScope.launch {
            mainViewModel.searchResult.collectLatest {
                items.clear()
                items.addAll(it?.collection?.items ?: listOf())
                adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun hideSoftwareKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        activity.currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}