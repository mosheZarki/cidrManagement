package com.example.upstream

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@EnableMongoRepositories
@SpringBootApplication
class UpstreamApplication

fun main(args: Array<String>) {
    runApplication<UpstreamApplication>(*args)
}

