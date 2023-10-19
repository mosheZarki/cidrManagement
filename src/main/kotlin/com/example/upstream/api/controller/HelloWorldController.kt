package com.example.upstream.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {

    @GetMapping("/hello")
    fun helloWorld(@RequestHeader("IpToCheck") ipToCheck: String): ResponseEntity<String> {
        return ResponseEntity.ok("Hello, World!")
    }
}