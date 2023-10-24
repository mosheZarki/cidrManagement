package com.example.upstream.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {

    @Operation(
        summary = "This is the interview relevant API, based on crud operations for blocking some ranges",
        description = "It gets an IP header to check and returns some content if the IP passes the check or is forbidden this logic is implemented on interceptor level"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successful response, the ip passed isAllowed function",
        content = [io.swagger.v3.oas.annotations.media.Content()]
    )
    @ApiResponse(
        responseCode = "403",
        description = "Forbidden response, the ip is not allowed.",
        content = [io.swagger.v3.oas.annotations.media.Content()]
    )
    @GetMapping("/call-me-for-ip-check")
    fun helloWorld(
        @Parameter(description = "The IP address to check on isAlowed function", `in` = ParameterIn.HEADER)
        @RequestHeader("IpToCheck") ipToCheck: String): ResponseEntity<String> {
        return ResponseEntity.ok("Hello, World!")
    }


}