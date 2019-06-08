package uz.kvikk.file.model

import java.util.*

data class FileResourceResult(

    val id: String,

    val ordered: Long,

    val version: Long,

    val createdDate: Date,

    val storedName: String,

    val uploadedName: String,

    val extension: String,

    val fileResourceType: String,

    val fileResourceSize: Long,

    val fileResourceUrl: String,

    val storedFullName: String,

    val uploadedFullName: String

) {}
