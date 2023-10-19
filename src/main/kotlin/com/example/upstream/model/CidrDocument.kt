package com.example.upstream.model


import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document


@Document
@CompoundIndex(def = "{'startIp': 1, 'endIp': 1}")
data class CidrDocument(
        @Id
        val id: ObjectId = ObjectId.get(),
        val startIp: Long,
        val endIp: Long,
        val cidr: String
)
