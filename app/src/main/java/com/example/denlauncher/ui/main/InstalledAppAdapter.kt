package com.example.denlauncher.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.denlauncher.databinding.ItemInstalledAppsBinding
import com.example.denlauncher.model.InstalledApp


private object DiffCallback : DiffUtil.ItemCallback<InstalledApp>() {
    override fun areItemsTheSame(oldItem: InstalledApp, newItem: InstalledApp): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: InstalledApp, newItem: InstalledApp): Boolean {
        return oldItem.name == newItem.name
    }
}

class InstalledAppAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewModel: MainViewModel
) : ListAdapter<InstalledApp, InstalledAppAdapter.AppNameViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AppNameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AppNameViewHolder(ItemInstalledAppsBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: AppNameViewHolder, position: Int) {
        holder.bind(getItem(position), viewLifecycleOwner, viewModel)
    }

    override fun getItemCount(): Int {
        val count = super.getItemCount()
        return if (count > 5) {
            5
        } else {
            count
        }
    }

    class AppNameViewHolder(
        private val binding: ItemInstalledAppsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: InstalledApp, viewLifecycleOwner: LifecycleOwner, viewModel: MainViewModel) {
            binding.run {
                lifecycleOwner = viewLifecycleOwner
                installedApp = item
                this.viewModel = viewModel
                executePendingBindings()
            }
        }
    }
}