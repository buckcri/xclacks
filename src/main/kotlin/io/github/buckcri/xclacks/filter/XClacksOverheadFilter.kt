package io.github.buckcri.xclacks.filter

import jakarta.servlet.*
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.http.HttpServletResponse
import java.io.IOException

/**
 * Servlet WebFilter adding the X-Clacks-Overhead http header to each response.
 */
@WebFilter
class XClacksOverheadFilter : Filter {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpServletResponse = response as? HttpServletResponse
        httpServletResponse?.setHeader("X-Clacks-Overhead", "GNU Terry Pratchett")
        chain.doFilter(request, response)
    }
}
