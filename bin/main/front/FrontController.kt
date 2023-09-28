package com.wafflestudio.seminar.spring2023.front

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class FrontController {
    @RequestMapping(value = ["/", "/web/**"])
    fun viewMapping(): String? {
        return "forward:/index.html"
    }
}