package com.example.upstream.db

import com.example.upstream.model.CidrDocument
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param

interface CidrRepository : MongoRepository<CidrDocument, ObjectId> {
    fun findByStartIpLessThanEqualAndEndIpGreaterThanEqual(@Param("startIp")startIp: Long,@Param("endIp") endIp: Long): List<CidrDocument>
}