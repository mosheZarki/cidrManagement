package com.example.upstream.service

import com.example.upstream.exception.EntityNotFoundException
import com.example.upstream.model.CidrDocument
import com.example.upstream.model.model.Cidr
import com.example.upstream.util.CIDRUtils
import org.bson.types.ObjectId

interface ICidr {
    fun getAll(): List<Cidr>

    fun getById(id : String): List<String>

    fun createCidr(cidr: Cidr): Cidr

    fun deleteById(id: ObjectId)
}