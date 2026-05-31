package com.elvin.purple.About

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elvin.purple.databinding.ItemContributorBinding

class ContributorAdapter(
    private val contributorList: List<Contributor>
) : RecyclerView.Adapter<ContributorAdapter.ContributorViewHolder>() {

    class ContributorViewHolder(val binding: ItemContributorBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorViewHolder {
        val binding = ItemContributorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContributorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContributorViewHolder, position: Int) {
        val contributor = contributorList[position]
        with(holder.binding) {
            tvName.text = contributor.name
            tvRole.text = contributor.role
            tvEmail.text = contributor.email
        }
    }

    override fun getItemCount(): Int = contributorList.size
}