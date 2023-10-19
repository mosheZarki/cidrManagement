package com.example.upstream.service

import com.example.upstream.db.CidrRepository
import com.example.upstream.exception.EntityNotFoundException
import com.example.upstream.model.CidrDocument
import com.example.upstream.model.model.Cidr

import com.example.upstream.util.CIDRUtils
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class CIDRService(@Qualifier("cidrRepository") private val repo: CidrRepository) : ICidr{

    override fun getAll(): List<Cidr> = repo.findAll().map { Cidr(notation = it.cidr, id = it.id.toString()) }

    override fun getById(id : String): List<String> {
        val cidrOpt = repo.findById(ObjectId(id))
        if (!cidrOpt.isPresent)
        {
            throw EntityNotFoundException("No data found for id: $id")
        }

        val cidrDoc = cidrOpt.get()
        val range = CIDRUtils.getCidrIPs(cidrDoc.cidr)

        return range;
    }

    override fun createCidr(cidr: Cidr): Cidr {
        val (start, end) = CIDRUtils.cidrToRange(cidr.notation)
        val doc = repo.insert(CidrDocument(startIp = start, endIp = end, cidr = cidr.notation))
        return Cidr(cidr.notation,doc.id.toString())
    }

    override fun deleteById(id: ObjectId) {
        repo.deleteById(id)
    }
}
