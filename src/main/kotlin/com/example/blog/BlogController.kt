package com.example.blog

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class BlogController {

    @GetMapping("/blog")
    fun blog(model: Model): String {
        model["title"] = "Blog"
        return "blog"
    }

}