package com.example.upstream.api.controller

import com.example.upstream.api.dto.CIDRDto
import com.example.upstream.model.model.Cidr
import com.example.upstream.service.CIDRService
import io.swagger.v3.oas.annotations.Operation
import org.bson.types.ObjectId
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import javax.validation.ConstraintViolation
import javax.validation.Validator

@RestController
@RequestMapping("/api/cidr")
class CIDRController(private val service: CIDRService,private val validator: Validator) {


    @Operation(summary = "Get IP Range Breakdown for given cidr ip, For interview and debug simplicity")
    @GetMapping("ips-breakdown/{id}")
    fun getIpsBreakDown(@PathVariable id: String): ResponseEntity<List<String>> {
        return ResponseEntity.ok(service.getIpsBreakdownById(id))
    }


    @Operation(summary = "Get all cidr's persisted to db, no paging ordering ability as not required for now")
    @GetMapping
    fun getAll(): ResponseEntity<List<CIDRDto>> {
        return ResponseEntity.ok(service.getAll().map { CIDRDto(it.notation,it.id) })
    }

    @Operation(summary = "Create new cidr range in db")
    @PostMapping
    fun insert(@RequestBody cidrDto: CIDRDto): ResponseEntity<CIDRDto> {

        val violations: Set<ConstraintViolation<CIDRDto>> = validator.validate(cidrDto)
        if (violations.isNotEmpty()) {
            throw RuntimeException(violations.joinToString(", ") { it.message })
        }

        val created = service.createCidr(Cidr(cidrDto.notation))
        return ResponseEntity.ok(CIDRDto(created.notation,created.id.toString()))
    }

    @Operation(summary = "Deletes cidr from db")

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        val objectId = ObjectId(id)
        service.deleteById(objectId)

        return ResponseEntity.noContent().build()
    }
}