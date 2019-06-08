package uz.kvikk.file.exception

class FileStorageException : RuntimeException {
  constructor(message: String) : super(message) {}

  constructor(message: String, cause: Throwable) : super(message, cause) {}
}
