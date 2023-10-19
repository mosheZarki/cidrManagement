package com.example.upstream.api.controller

import com.example.upstream.api.dto.CIDRDto
import com.example.upstream.model.model.Cidr
import com.example.upstream.service.CIDRService
import org.bson.types.ObjectId
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import javax.validation.ConstraintViolation
import javax.validation.Validator

@RestController
@RequestMapping("/api/cidr")
class CIDRController(private val service: CIDRService,private val validator: Validator) {

    @GetMapping("ips-range/{id}")
    fun getIpsBreakDown(@PathVariable id: String): ResponseEntity<List<String>> {
        return ResponseEntity.ok(service.getById(id))
    }


    @GetMapping
    fun getAll(): ResponseEntity<List<CIDRDto>> {
        return ResponseEntity.ok(service.getAll().map { CIDRDto(it.notation,it.id) })
    }

    @PostMapping
    fun insert(@RequestBody cidrDto: CIDRDto): ResponseEntity<CIDRDto> {

        val violations: Set<ConstraintViolation<CIDRDto>> = validator.validate(cidrDto)
        if (violations.isNotEmpty()) {
            throw RuntimeException(violations.joinToString(", ") { it.message })
        }

        val created = service.createCidr(Cidr(cidrDto.notation))
        return ResponseEntity.ok(CIDRDto(created.notation,created.id.toString()))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        val objectId = ObjectId(id)
        service.deleteById(objectId)

        return ResponseEntity.noContent().build()
    }
}