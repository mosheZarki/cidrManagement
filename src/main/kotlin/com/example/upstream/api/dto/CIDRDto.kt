package com.example.upstream.api.dto

import com.example.upstream.model.CidrDocument
import com.example.upstream.util.CIDRUtils
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Pattern

//import javax.validation.constraints.Pattern

class CIDRDto(
        @Pattern(regexp = "^([0-9]{1,3}\\.){3}[0-9]{1,3}/[0-9]{1,2}$")
        val notation: String,
        val id: String? = null
)