package uz.kvikk.file.controller

import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import uz.kvikk.file.model.FileResourceResult
import uz.kvikk.file.service.FileResourceService

@RestController
@RequestMapping("/file-server/")
class FileController
constructor(private val dBFileResourceService: FileResourceService) {

    @GetMapping("info/{fileName:.+}")
    fun infoFile(@PathVariable fileName: String): ResponseEntity<FileResourceResult> = dBFileResourceService.infoFileResource(fileName)

    @PostMapping("uploadFile")
    fun uploadFile(
            @RequestParam("clientId") clientId: String,
            @RequestParam("fileResourceType") fileResourceType: String,
            @RequestParam("file") file: MultipartFile): ResponseEntity<FileResourceResult> = dBFileResourceService.saveFileResource(clientId, fileResourceType, file)

    @PostMapping("uploadBase64")
    fun uploadBase64(
            @RequestParam("clientId") clientId: String,
            @RequestParam("fileResourceType") fileResourceType: String,
            @RequestParam("fileName") fileName: String,
            @RequestParam("file") file: String): ResponseEntity<FileResourceResult> = dBFileResourceService.saveBase64Resource(clientId, fileResourceType, fileName, file)

    @PostMapping("updateFile")
    fun updateFile(
            @RequestParam("fileName") fileName: String,
            @RequestParam("file") file: MultipartFile): ResponseEntity<FileResourceResult> = dBFileResourceService.updateFileResource(fileName, file)

    @PostMapping("trackFile/{fileName:.+}")
    fun trackFile(
            @PathVariable fileName: String,
            @RequestBody parametres: Map<String, Any>): ResponseEntity<Any> = dBFileResourceService.trackFileResource(fileName, parametres)

    @GetMapping("downloadFile/{fileName:.+}")
    fun downloadFile(@PathVariable fileName: String): ResponseEntity<Resource> = dBFileResourceService.loadFileResource(fileName)

}