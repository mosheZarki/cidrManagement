package com.example.upstream.api.interceptor

import com.example.upstream.db.CidrRepository
import com.example.upstream.exception.ForbiddenException
import com.example.upstream.exception.InvalidInputException
import com.example.upstream.util.CIDRUtils
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class IpInterceptor(private val repo: CidrRepository) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (isFilteredUri(request)) return true

        if (!isAllowed(getIpHeader(request))) {
            throw ForbiddenException("IP not allowed")
        }

        return true;
    }


    // For interview purpose only not getting ip real header but take from header request
    private fun getIpHeader(request: HttpServletRequest) = request.getHeader("IpToCheck")
            ?: throw InvalidInputException("Ip header is required for non crud operations")

    private fun isFilteredUri(request: HttpServletRequest): Boolean {
        // For crud apis, act like ot other server bypass for interview only
        val whitelist = listOf("/api/cidr", "/swagger", "/v3/api-docs")

        return whitelist.any { request.requestURI.contains(it) }
    }


    fun isAllowed(ip: String): Boolean {
        val ipAsLong = CIDRUtils.ipToLong(ip)
        return repo.findByStartIpLessThanEqualAndEndIpGreaterThanEqual(ipAsLong,ipAsLong).isEmpty()
    }
}