package com.example.upstream.util

import com.example.upstream.api.dto.CIDRDto
import com.example.upstream.model.CidrDocument
import com.example.upstream.model.model.Cidr

class CIDRUtils private constructor() {

    companion object {
        fun getCidrIPs(cidr: String): List<String> {
            val parts = cidr.split("/")
            val ipParts = parts[0].split(".").map { it.toInt() }
            val prefixLength = parts[1].toInt()

            val baseIP: Long = ipParts[0].toLong().shl(24) +
                    ipParts[1].shl(16) +
                    ipParts[2].shl(8) +
                    ipParts[3]

            val numberOfIPs = 1L.shl(32 - prefixLength)

            return (0 until numberOfIPs).map {
                longToIp(baseIP + it)
            }
        }

         fun cidrToRange(cidr: String): Pair<Long, Long> {
            val parts = cidr.split("/")
            val ip = parts[0]
            val prefix = parts[1].toInt()

            val ipLong = ipToLong(ip)
            val netmaskInv = (1L shl (32 - prefix)) - 1

            return Pair(ipLong, ipLong or netmaskInv)
        }

         fun ipToLong(ipString: String): Long {
            val octets = ipString.split(".")
            return (octets[0].toLong() shl 24) + (octets[1].toLong() shl 16) +
                    (octets[2].toLong() shl 8) + octets[3].toLong()
        }

         fun longToIp(ipLong: Long): String {
            return String.format(
                    "%d.%d.%d.%d",
                    ipLong shr 24 and 0xFF,
                    ipLong shr 16 and 0xFF,
                    ipLong shr 8 and 0xFF,
                    ipLong and 0xFF
            )
        }
    }
}
