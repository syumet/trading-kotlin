package ca.jrvs.trading.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AppController {

    @GetMapping("/health")
    @ResponseBody
    fun health(): String {
        return "I'm very healthy!"
    }

}