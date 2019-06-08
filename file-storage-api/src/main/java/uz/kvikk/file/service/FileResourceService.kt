package uz.kvikk.file.service

import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile
import uz.kvikk.file.model.FileResourceResult
import uz.kvikk.file.model.entity.FileResource

import java.io.File
import java.util.Date

interface FileResourceService {

  fun saveFileResource(clientId: String, fileResourceType: String, multipartFile: MultipartFile): ResponseEntity<FileResourceResult>

  fun saveBase64Resource(clientId: String, fileResourceType: String, fileName: String, base64File: String): ResponseEntity<FileResourceResult>

  fun updateFileResource(fileName: String, multipartFile: MultipartFile): ResponseEntity<FileResourceResult>

  fun trackFileResource(fileName: String, parametres: Map<String, Any>): ResponseEntity<Any>

  fun loadFileResource(fileName: String): ResponseEntity<Resource>

  fun infoFileResource(fileName: String): ResponseEntity<FileResourceResult>

  /* fun fileBase64(fileString: String): FileResource

   fun getFile(id: String): Resource

   fun uploadFolder(now: Date): File*/
}
