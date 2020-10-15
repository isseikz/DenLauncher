package com.example.denlauncher.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.denlauncher.R
import com.example.denlauncher.databinding.MainFragmentBinding
import com.example.denlauncher.model.InstalledApps
import com.example.denlauncher.model.Time

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: MainFragmentBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var installedAppAdapter: InstalledAppAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initialize()

        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.apply {
            installedApps.layoutManager = LinearLayoutManager(context)
            installedApps.adapter = InstalledAppAdapter(viewLifecycleOwner, viewModel!!).also {
                installedAppAdapter = it
            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.run {
            installedApps.observe(viewLifecycleOwner, {
                installedAppAdapter.submitList(it)
            })
        }
    }

    private fun initialize() {
        context?.let {
            Time.startReceiver(it)
            InstalledApps.updateList(it)
        }
    }
}
