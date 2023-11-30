package com.wafflestudio.seminar.spring2023._web.log

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class LogInterceptor(
    private val logRequest: LogRequest,
    private val logSlowResponse: AlertSlowResponse,
) : HandlerInterceptor {
    private val requestStartAt: ThreadLocal<Long> = ThreadLocal()

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logRequest(Request(method = request.method, path = request.requestURI))

        requestStartAt.set(System.currentTimeMillis())

        return super.preHandle(request, response, handler)
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?,
    ) {
        val responseDuration = System.currentTimeMillis() - requestStartAt.get()

        if (responseDuration > 3000) {
            logSlowResponse(
                slowResponse = SlowResponse(
                    method = request.method,
                    path = request.requestURI,
                    duration = responseDuration
                )
            )
        }

        super.afterCompletion(request, response, handler, ex)
    }
}
