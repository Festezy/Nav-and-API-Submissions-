package com.example.aplikasi_dicoding_event_navigationdanapi.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasi_dicoding_event_navigationdanapi.core.domain.model.Events
import com.example.aplikasi_dicoding_event_navigationdanapi.core.ui.EventAdapter
import com.example.aplikasi_dicoding_event_navigationdanapi.detail.DetailsActivity
import com.example.aplikasi_dicoding_event_navigationdanapi.di.FavoriteModuleDependencies
import com.example.aplikasi_dicoding_event_navigationdanapi.favorite.databinding.FragmentFavoriteBinding
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

//@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: FavoriteViewModel by viewModels {
        factory
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteComponent.builder()
            .context(requireActivity())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity(),
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this@FavoriteFragment)
//        (requireActivity().application as MyApplication).appComponent.inject(this)
//        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)
        viewModel.getFavoriteEvents()
        viewModel.listFavoriteEvents.observe(viewLifecycleOwner) { favoriteEventList ->
            setFavoriteEventData(favoriteEventList)
        }
        showLoading(false)
    }

    private fun setFavoriteEventData(eventData: List<Events>) {
        val adapter = EventAdapter()
        adapter.submitList(eventData)
        binding.apply {
            rvEvent.setHasFixedSize(true)
            rvEvent.layoutManager = LinearLayoutManager(requireActivity())
            rvEvent.adapter = adapter

            checkIfEmpty(rvEvent, emptyView)
        }
        adapter.setOnItemClickCallback(object : EventAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Events) {
                Intent(requireActivity(), DetailsActivity::class.java).also { intent ->
                    intent.putExtra(DetailsActivity.EXTRA_DATA, data)
                    requireActivity().startActivity(intent)
                }
            }
        })
    }

    private fun checkIfEmpty(recyclerView: RecyclerView, emptyView: TextView) {
        val adapter = recyclerView.adapter
        if (adapter != null) {
            if (adapter.itemCount == 0) {
                recyclerView.visibility = View.GONE
                emptyView.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                emptyView.visibility = View.GONE
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) progressBar.visibility = View.VISIBLE
            else progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}