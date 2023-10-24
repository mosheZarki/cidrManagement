package com.example.upstream.service


import com.example.upstream.model.model.Cidr
import org.bson.types.ObjectId

interface ICidr {
    fun getAll(): List<Cidr>

    fun getIpsBreakdownById(id : String): List<String>

    fun createCidr(cidr: Cidr): Cidr

    fun deleteById(id: ObjectId)
}