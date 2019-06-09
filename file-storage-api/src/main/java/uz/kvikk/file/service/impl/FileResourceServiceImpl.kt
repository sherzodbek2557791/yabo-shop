package uz.kvikk.file.service.impl

import org.apache.commons.io.FilenameUtils
import org.hashids.Hashids
import org.jooq.*
import org.jooq.impl.DSL
import org.jooq.impl.SQLDataType
import org.slf4j.LoggerFactory
import org.springframework.core.io.FileUrlResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.Base64Utils
import org.springframework.util.StringUtils
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.multipart.MultipartFile
import uz.kvikk.file.config.property.StorageProperties
import uz.kvikk.file.exception.FileStorageException
import uz.kvikk.file.model.FileResourceResult
import uz.kvikk.file.model.entity.FileResource
import uz.kvikk.file.service.FileResourceService
import uz.kvikk.file.utils.converter.DateConverter
import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.math.BigInteger
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.text.SimpleDateFormat
import java.util.*
import javax.activation.MimetypesFileTypeMap
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest


@Service
class FileResourceServiceImpl(
        private val hashids: Hashids,
        private val storageProperties: StorageProperties,
        private val dsl: DSLContext) : FileResourceService {

  override fun saveBase64Resource(clientId: String, fileResourceType: String, fileName: String, base64File: String): ResponseEntity<FileResourceResult> {
    if (StringUtils.isEmpty(base64File))
      throw FileStorageException("Sorry! Invalid base64File=$base64File")

    if (StringUtils.isEmpty(fileName))
      throw FileStorageException("Sorry! Invalid fileName=$fileName")

    val uploadedName = FilenameUtils.getBaseName(fileName)
    val extension = FilenameUtils.getExtension(fileName)

    if (StringUtils.isEmpty(uploadedName))
      throw FileStorageException("Sorry! Invalid uploadedName=$uploadedName")

    if (StringUtils.isEmpty(extension))
      throw FileStorageException("Sorry! Invalid extension=$extension")

    //check clientId
    if (StringUtils.isEmpty(clientId))
      throw FileStorageException("Sorry! Invalid clientId=$clientId")

    //check fileResourceType
    if (StringUtils.isEmpty(fileResourceType))
      throw FileStorageException("Sorry! Invalid fileResourceType=$fileResourceType")

    // Check if the file's name contains invalid characters
    if (uploadedName!!.contains(".."))
      throw FileStorageException("Sorry! Filename contains invalid path sequence $uploadedName")

    val currentDate: String = SimpleDateFormat("yyyy/MM/dd").format(Date())
    val localPath: String = "$clientId/$currentDate/$fileResourceType/"

    val id = dsl.select(ENTITY_SEQ.nextval()).from("dual").fetchOne().into(Long::class.java)
    val storedName = hashids.encode(id)
    val fileResourcePath: Path = this.rootLocation!!.resolve(localPath)
    val bytes = Base64.getMimeDecoder().decode(base64File)
    val size = bytes.size
    try {
      if (Files.notExists(fileResourcePath))
        Files.createDirectories(fileResourcePath)

      ByteArrayInputStream(bytes).use { inputStream ->
        Files.copy(inputStream,
            fileResourcePath.resolve("$storedName.$extension"),
            StandardCopyOption.REPLACE_EXISTING)
      }
    } catch (e: IOException) {
      throw FileStorageException("Failed to store file $fileName", e)
    }

    dsl.insertInto<Record>(ENTITY,
            ID,
            ORDERED,
            STORED_NAME,
            UPLOADED_NAME,
            CLIENT_ID,
            FILE_RESOURCE_TYPE,
            FILE_RESOURCE_PATH,
            FILE_RESOURCE_SIZE,
            EXTENSION
    ).values(
        storedName,
        id,
        storedName,
        uploadedName,
        clientId,
        fileResourceType,
        fileResourcePath.toString(),
        size,
        extension
    ).execute()

    val rFile: FileResource? = dsl.selectFrom<Record>(VIEW)
        .where(ID.eq(storedName))
        .fetchOneInto(FileResource::class.java)

    val (request) = RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes

    return ResponseEntity.ok(FileResourceResult(
            rFile!!.id,
            rFile.ordered,
            rFile.version,
            rFile.createdDate,
            rFile.storedName,
            rFile.uploadedName,
            rFile.extension,
            rFile.fileResourceType,
            rFile.fileResourceSize,
            "${getBaseUrl(request)}/file-server/downloadFile/${rFile.storedFullName}",
            rFile.storedFullName,
            rFile.uploadedFullName
    ))
  }

  override fun infoFileResource(fileName: String): ResponseEntity<FileResourceResult> {
    if (StringUtils.isEmpty(fileName))
      throw FileStorageException("Sorry! Invalid fileName=$fileName")

    val storedName = FilenameUtils.getBaseName(fileName)
    val extension = FilenameUtils.getExtension(fileName)

    if (StringUtils.isEmpty(storedName))
      throw FileStorageException("Sorry! Invalid storedName=$storedName")

    if (StringUtils.isEmpty(extension))
      throw FileStorageException("Sorry! Invalid extension=$extension")

    val rFile: FileResource? = dsl.selectFrom<Record>(VIEW)
        .where(ID.eq(storedName))
        .fetchOneInto(FileResource::class.java)

    val (request) = RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes

    return ResponseEntity.ok(FileResourceResult(
            rFile!!.id,
            rFile.ordered,
            rFile.version,
            rFile.createdDate,
            rFile.storedName,
            rFile.uploadedName,
            rFile.extension,
            rFile.fileResourceType,
            rFile.fileResourceSize,
            "${getBaseUrl(request)}/file-server/downloadFile/${rFile.storedFullName}",
            rFile.storedFullName,
            rFile.uploadedFullName
    ))
  }

  override fun trackFileResource(fileName: String, parametres: Map<String, Any>): ResponseEntity<Any> {
    if (StringUtils.isEmpty(fileName))
      throw FileStorageException("Sorry! Invalid fileName=$fileName")

    val storedName = FilenameUtils.getBaseName(fileName)
    val extension = FilenameUtils.getExtension(fileName)

    if (StringUtils.isEmpty(storedName))
      throw FileStorageException("Sorry! Invalid storedName=$storedName")

    if (StringUtils.isEmpty(extension))
      throw FileStorageException("Sorry! Invalid extension=$extension")

    if (parametres["status"].toInt() == 2) {
      val rFile: FileResource = dsl.selectFrom<Record>(VIEW)
          .where(ID.eq(storedName))
          .and(EXTENSION.eq(extension))
          .fetchOneInto(FileResource::class.java)
          ?: throw FileStorageException("Sorry! Invalid file not found.")
      try {
        val bufferedInputStream = BufferedInputStream(URL(parametres["url"] as String).openStream())
        bufferedInputStream.use { inputStream ->
          Files.copy(inputStream,
              Paths.get(rFile.absoluteFile).normalize(),
              StandardCopyOption.REPLACE_EXISTING)
        }
      } catch (e: IOException) {
        throw FileStorageException("Failed to store file $fileName", e)
      }
      val version = rFile.version + 1

      dsl.update<Record>(ENTITY)
          .set(FILE_RESOURCE_VERSION, version)
          .where(ID.eq(rFile.id))
          .execute()
    }
    return ResponseEntity.ok(mapOf("error" to 0))
  }

  override fun updateFileResource(fileName: String, multipartFile: MultipartFile): ResponseEntity<FileResourceResult> {
    if (StringUtils.isEmpty(fileName))
      throw FileStorageException("Sorry! Invalid fileName=$fileName")

    val storedName = FilenameUtils.getBaseName(fileName)
    val extension = FilenameUtils.getExtension(fileName)

    if (StringUtils.isEmpty(storedName))
      throw FileStorageException("Sorry! Invalid storedName=$storedName")

    if (StringUtils.isEmpty(extension))
      throw FileStorageException("Sorry! Invalid extension=$extension")

    val rFile: FileResource = dsl.selectFrom<Record>(VIEW)
        .where(ID.eq(storedName))
        .and(EXTENSION.eq(extension))
        .fetchOneInto(FileResource::class.java) ?: throw FileStorageException("Sorry! Invalid file not found.")

    try {
      multipartFile.inputStream.use { inputStream ->
        Files.copy(inputStream,
            Paths.get(rFile.absoluteFile).normalize(),
            StandardCopyOption.REPLACE_EXISTING)
      }
    } catch (e: IOException) {
      throw FileStorageException("Failed to store file $fileName", e)
    }
    val version = rFile.version + 1

    dsl.update<Record>(ENTITY)
        .set(FILE_RESOURCE_VERSION, version)
        .where(ID.eq(rFile.id))
        .execute()
    val (request) = RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes

    return ResponseEntity.ok(FileResourceResult(
            rFile.id,
            rFile.ordered,
            version,
            rFile.createdDate,
            rFile.storedName,
            rFile.uploadedName,
            rFile.extension,
            rFile.fileResourceType,
            rFile.fileResourceSize,
            "${getBaseUrl(request)}/file-server/downloadFile/${rFile.storedFullName}",
            rFile.storedFullName,
            rFile.uploadedFullName
    ))
  }

  override fun saveFileResource(clientId: String, fileResourceType: String, multipartFile: MultipartFile): ResponseEntity<FileResourceResult> {
    //check clientId
    if (StringUtils.isEmpty(clientId))
      throw FileStorageException("Sorry! Invalid clientId=$clientId")

    //check fileResourceType
    if (StringUtils.isEmpty(fileResourceType))
      throw FileStorageException("Sorry! Invalid fileResourceType=$fileResourceType")

    // Normalize file name
    val uploadFileName = multipartFile.originalFilename?.let { StringUtils.cleanPath(it) }

    // Check if the file's name contains invalid characters
    if (uploadFileName!!.contains(".."))
      throw FileStorageException("Sorry! Filename contains invalid path sequence $uploadFileName")

    val currentDate: String = SimpleDateFormat("yyyy/MM/dd").format(Date())
    val localPath: String = "$clientId/$currentDate/$fileResourceType/"

    val id = dsl.select(ENTITY_SEQ.nextval()).from("dual").fetchOne().into(Long::class.java)
    val storedName = hashids.encode(id)
    val uploadedName = FilenameUtils.getBaseName(multipartFile.originalFilename)
    val extension = FilenameUtils.getExtension(multipartFile.originalFilename)
    val fileName = "$storedName.$extension"
    val fileResourcePath: Path = this.rootLocation!!.resolve(localPath)
    try {
      if (Files.notExists(fileResourcePath))
        Files.createDirectories(fileResourcePath)

      multipartFile.inputStream.use { inputStream ->
        Files.copy(inputStream,
            fileResourcePath.resolve(fileName),
            StandardCopyOption.REPLACE_EXISTING)
      }
    } catch (e: IOException) {
      throw FileStorageException("Failed to store file $fileName", e)
    }

    dsl.insertInto<Record>(ENTITY,
            ID,
            ORDERED,
            STORED_NAME,
            UPLOADED_NAME,
            CLIENT_ID,
            FILE_RESOURCE_TYPE,
            FILE_RESOURCE_PATH,
            FILE_RESOURCE_SIZE,
            EXTENSION
    ).values(
        storedName,
        id,
        storedName,
        uploadedName,
        clientId,
        fileResourceType,
        fileResourcePath.toString(),
        multipartFile.size,
        extension
    ).execute()

    val rFile: FileResource? = dsl.selectFrom<Record>(VIEW)
        .where(ID.eq(storedName))
        .fetchOneInto(FileResource::class.java)

    val (request) = RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes

    return ResponseEntity.ok(FileResourceResult(
            rFile!!.id,
            rFile.ordered,
            rFile.version,
            rFile.createdDate,
            rFile.storedName,
            rFile.uploadedName,
            rFile.extension,
            rFile.fileResourceType,
            rFile.fileResourceSize,
            "${getBaseUrl(request)}/file-server/downloadFile/${rFile.storedFullName}",
            rFile.storedFullName,
            rFile.uploadedFullName
    ))
  }

  override fun loadFileResource(fileName: String): ResponseEntity<Resource> {
    if (StringUtils.isEmpty(fileName))
      throw FileStorageException("Sorry! Invalid fileName=$fileName")

    val storedName = FilenameUtils.getBaseName(fileName)
    val extension = FilenameUtils.getExtension(fileName)

    if (StringUtils.isEmpty(storedName))
      throw FileStorageException("Sorry! Invalid storedName=$storedName")

    if (StringUtils.isEmpty(extension))
      throw FileStorageException("Sorry! Invalid extension=$extension")

    val rFile: FileResource = dsl.selectFrom<Record>(VIEW)
        .where(ID.eq(storedName))
        .and(EXTENSION.eq(extension))
        .fetchOneInto(FileResource::class.java) ?: throw FileStorageException("Sorry! Invalid file not found.")

    println(rFile.uploadedFullName)
    val file = File(rFile.absoluteFile)
    var mimeType = MediaType.parseMediaType(MimetypesFileTypeMap().getContentType(file))
    val isImage = mimeType.isPresentIn(listOf(MediaType.IMAGE_GIF, MediaType.IMAGE_JPEG, MediaType.IMAGE_PNG))
    var contentDispositionType = if (isImage) "inline" else "attachment"
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "$contentDispositionType; filename=${rFile.uploadedFullName}")
        .contentType(mimeType)
        .body(FileUrlResource(file.toURI().toURL()))
  }

  companion object {
    val ENTITY_SEQ: Sequence<BigInteger> = DSL.sequence(DSL.name("FILE_RESOURCE_SEQ"))
    val ENTITY: Table<Record> = DSL.table(DSL.name("FILE_RESOURCE"))
    val VIEW: Table<Record> = DSL.table(DSL.name("FILE_RESOURCE_VIEW"))
    val ID: Field<String> = DSL.field(DSL.name("ID"), SQLDataType.VARCHAR)
    val ORDERED: Field<Long> = DSL.field(DSL.name("ORDERED"), SQLDataType.BIGINT)
    val FILE_RESOURCE_VERSION: Field<Long> = DSL.field(DSL.name("FILE_RESOURCE_VERSION"), SQLDataType.BIGINT)
    val CREATED_DATE: Field<Date> = DSL.field(DSL.name("CREATED_DATE"), SQLDataType.DATE.asConvertedDataType(DateConverter()))
    val CLIENT_ID: Field<String> = DSL.field(DSL.name("CLIENT_ID"), SQLDataType.VARCHAR)
    val STORED_NAME: Field<String> = DSL.field(DSL.name("STORED_NAME"), SQLDataType.VARCHAR)
    val UPLOADED_NAME: Field<String> = DSL.field(DSL.name("UPLOADED_NAME"), SQLDataType.VARCHAR)
    val FILE_RESOURCE_TYPE: Field<String> = DSL.field(DSL.name("FILE_RESOURCE_TYPE"), SQLDataType.VARCHAR)
    val FILE_RESOURCE_PATH: Field<String> = DSL.field(DSL.name("FILE_RESOURCE_PATH"), SQLDataType.VARCHAR)
    val FILE_RESOURCE_SIZE: Field<Long> = DSL.field(DSL.name("FILE_RESOURCE_SIZE"), SQLDataType.BIGINT)
    val EXTENSION: Field<String> = DSL.field(DSL.name("EXTENSION"), SQLDataType.VARCHAR)
    val DELIMITER: Field<String> = DSL.field(DSL.name("DELIMITER"), SQLDataType.CHAR)
    val STORED_FULL_NAME: Field<String> = DSL.field(DSL.name("STORED_FULL_NAME"), SQLDataType.VARCHAR)
    val UPLOADED_FULL_NAME: Field<String> = DSL.field(DSL.name("UPLOADED_FULL_NAME"), SQLDataType.VARCHAR)
    val ABSOLUTE_FILE: Field<String> = DSL.field(DSL.name("ABSOLUTE_FILE"), SQLDataType.VARCHAR)
    private val log = LoggerFactory.getLogger(FileResourceServiceImpl::class.java)
  }

  var rootLocation: Path? = null

  @PostConstruct
  fun init() {
    val fileStorageLocation = Paths
        .get(storageProperties.uploadDir)
        .toAbsolutePath()
        .normalize()

    if (Files.notExists(fileStorageLocation))
      try {
        Files.createDirectories(fileStorageLocation)
      } catch (ex: Exception) {
        throw FileStorageException("Could not create the directory where the uploaded fifiles_tmp-localhostles will be stored.", ex)
      }
    this.rootLocation = Paths.get(fileStorageLocation.toUri())
  }

}

private fun Any?.toInt(): Int? =
    when {
      this is String -> this.toString().toIntOrNull()
      this is Int -> this
      else -> null
    }


private operator fun ServletRequestAttributes.component1() = request
private operator fun URL.component1() = protocol
private operator fun URL.component2() = port
private operator fun URL.component3() = host

fun getBaseUrl(request: HttpServletRequest): String {
  val requestURL = URL(request.requestURL.toString())
  val (protocol, port, host) = requestURL;
  return "$protocol://$host${if (port == -1) "" else ":$port"}"
}
