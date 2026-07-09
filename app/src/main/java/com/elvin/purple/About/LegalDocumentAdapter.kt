package com.elvin.purple.About // Sesuaikan dengan package project kamu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elvin.purple.databinding.ItemLegalDocumentBinding


class LegalDocumentAdapter(
    private var documentList: List<LegalDocument>,
    private val onRequestAccessClick: (LegalDocument) -> Unit
) : RecyclerView.Adapter<LegalDocumentAdapter.DocumentViewHolder>() {

    class DocumentViewHolder(val binding: ItemLegalDocumentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val binding = ItemLegalDocumentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DocumentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        val document = documentList[position]
        holder.binding.apply {
            tvTitle.text = document.title
            tvDate.text = document.date
            tvStatus.text = document.status
            tvDescription.text = document.description

            btnRequestAccess.setOnClickListener {
                onRequestAccessClick(document)
            }
        }
    }

    override fun getItemCount(): Int = documentList.size

    fun updateData(newList: List<LegalDocument>) {
        this.documentList = newList
        notifyDataSetChanged()
    }
}