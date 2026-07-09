package com.elvin.purple.About

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "legal_documents")
data class LegalDocument(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val date: String,
    val status: String,
    val description: String
)