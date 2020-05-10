package cz.st52530.recipes.util

import com.cloudinary.Cloudinary
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Component
class ImageHandlingUtil(
        private val cloudinary: Cloudinary
) {

    /**
     * @return URL of uploaded file
     */
    fun uploadImage(image: MultipartFile, recipeId: Int? = null): String {
        // Prepare file to upload.
        val tempFile = File("/tmp/image")
        image.transferTo(tempFile)

        val params = mutableMapOf(
                "folder" to "recipes"
        )
        if (recipeId != null) {
            params["public_id"] = recipeId.toString()
        }
        val uploadResult = cloudinary.uploader().upload(tempFile, params)
        return uploadResult.getValue("secure_url") as String
    }

    fun deleteImage(recipeId: Int) {
        cloudinary.uploader().destroy("recipes/$recipeId", emptyMap<String, String>())
    }
}