package cz.st52530.recipes.extensions

@Throws(java.lang.IllegalStateException::class)
fun String.ensureNotBlank(): String {
    if (isBlank()) {
        throw IllegalStateException("Cannot be empty!")
    }

    return this
}