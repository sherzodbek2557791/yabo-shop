package uz.kvikk.file.model.entity

import java.util.*
import javax.persistence.Column
import javax.persistence.Table

@Table(name = "FILE_RESOURCE")
data class FileResource (

  @Column(name = "ID")
  val id: String,

  @Column(name = "ORDERED")
  val ordered: Long,

  @Column(name = "FILE_RESOURCE_VERSION")
  val version: Long,

  @Column(name = "CREATED_DATE")
  val createdDate: Date,

  @Column(name = "STORED_NAME")
  val storedName: String,

  @Column(name = "UPLOADED_NAME")
  val uploadedName: String,

  @Column(name = "CLIENT_ID")
  val clientId: String,

  @Column(name = "FILE_RESOURCE_TYPE")
  val fileResourceType: String,

  @Column(name = "FILE_RESOURCE_PATH")
  val fileResourcePath: String,

  @Column(name = "FILE_RESOURCE_SIZE")
  val fileResourceSize: Long,

  @Column(name = "EXTENSION")
  val extension: String,

  @Column(name = "DELIMITER")
  val delimiter: Char,

  @Column(name = "STORED_FULL_NAME")
  val storedFullName: String,

  @Column(name = "UPLOADED_FULL_NAME")
  val uploadedFullName: String,

  @Column(name = "ABSOLUTE_FILE")
  val absoluteFile: String

){}
