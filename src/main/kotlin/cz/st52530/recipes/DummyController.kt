package cz.st52530.recipes

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DummyController {

    @GetMapping("/")
    fun getRoot(): String {
        return "It works!"
    }
}