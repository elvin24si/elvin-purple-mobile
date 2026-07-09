package com.elvin.purple.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elvin.purple.About.LegalDocument

@Dao
interface LegalDocumentDao {
    @Query("SELECT * FROM legal_documents")
    suspend fun getAllDocuments(): List<LegalDocument>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocuments(documents: List<LegalDocument>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocument(document: LegalDocument)
}
