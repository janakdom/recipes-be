package cz.st52530.recipes.extensions

fun String.ensureNotBlank(): String {
    if (isBlank()) {
        throw IllegalStateException("Cannot be empty!")
    }

    return this
}