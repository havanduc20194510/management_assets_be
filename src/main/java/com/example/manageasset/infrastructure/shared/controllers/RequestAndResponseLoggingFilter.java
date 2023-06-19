package com.example.manageasset.infrastructure.shared.controllers;

import com.example.manageasset.infrastructure.shared.jsons.MyObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Spring Web filter for logging request and response.
 *
 * @author Hidetake Iwata
 * @see org.springframework.web.filter.AbstractRequestLoggingFilter
 * @see ContentCachingRequestWrapper
 * @see ContentCachingResponseWrapper
 */
@Slf4j
@Component
public class RequestAndResponseLoggingFilter extends OncePerRequestFilter {
    private static final List<MediaType> VISIBLE_TYPES = Arrays.asList(
            MediaType.valueOf("text/*"),
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_XML,
            MediaType.valueOf("application/*+json"),
            MediaType.valueOf("application/*+xml"),
            MediaType.MULTIPART_FORM_DATA
    );

    private static void logRequestHeader(ContentCachingRequestWrapper request, ObjectNode logObject) {
        logObject.put("method", request.getMethod());
        logObject.put("uri", request.getRequestURI());
        if (request.getQueryString() != null) {
            logObject.put("queryString", request.getQueryString());
        }
        ObjectNode logHeader = MyObjectMapper.instance().createObjectNode();
        Collections.list(request.getHeaderNames()).forEach(headerName ->
                Collections.list(request.getHeaders(headerName)).forEach(headerValue ->
                        logHeader.put(headerName, headerValue)));
        logObject.put("requestHeader", logHeader);
    }

    private static void logRequestBody(ContentCachingRequestWrapper request, ObjectNode logObject) {
        val content = request.getContentAsByteArray();
        if (content.length > 0) {
            logContent(content, request.getContentType(), request.getCharacterEncoding(), logObject, "requestBody");
        }
    }

    private static void logResponse(ContentCachingResponseWrapper response, ObjectNode logObject) {
        val status = response.getStatus();
        logObject.put("responseStatus", status + " - " + HttpStatus.valueOf(status).getReasonPhrase());
        ObjectNode headerObject = MyObjectMapper.instance().createObjectNode();
        response.getHeaderNames().forEach(headerName ->
                response.getHeaders(headerName).forEach(headerValue ->
                        headerObject.put(headerName, headerValue)));
        val content = response.getContentAsByteArray();
        if (content.length > 0) {
            logContent(content, response.getContentType(), response.getCharacterEncoding(), logObject, "responseBody");
        }
    }

    private static void logContent(byte[] content,
                                   String contentType,
                                   String contentEncoding,
                                   ObjectNode logObject,
                                   String prefix) {
        val mediaType = MediaType.valueOf(contentType);
        val visible = VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
        if (visible) {
            try {
                val contentString = new String(content, contentEncoding);
                logObject.put(prefix, contentString);
            } catch (UnsupportedEncodingException e) {
                logObject.put(prefix + "Bytes", content.length);
            }
        } else {
            logObject.put(prefix + "Bytes", content.length);
        }
    }

    private static ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper) {
            return (ContentCachingRequestWrapper) request;
        } else {
            return new ContentCachingRequestWrapper(request);
        }
    }

    private static ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper) {
            return (ContentCachingResponseWrapper) response;
        } else {
            return new ContentCachingResponseWrapper(response);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isAsyncDispatch(request)) {
            filterChain.doFilter(request, response);
        } else {
            doFilterWrapped(wrapRequest(request), wrapResponse(response), filterChain);
        }
    }

    protected void doFilterWrapped(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, FilterChain filterChain) throws ServletException, IOException {
        final ObjectNode logObject = MyObjectMapper.instance().createObjectNode();
        final long startTime = System.currentTimeMillis();
        try {
            beforeRequest(request, response, logObject);
            filterChain.doFilter(request, response);
        } finally {
            final long time = System.currentTimeMillis() - startTime;
            logObject.put("time", time);
            afterRequest(request, response, logObject);
            response.copyBodyToResponse();
        }
        final String logString = MyObjectMapper.instance().writeValueAsString(logObject).replace("(?s)\\\\s*/\\\\*.*\\\\*/", " ");
        log.info(logString);
    }

    protected void beforeRequest(ContentCachingRequestWrapper request,
                                 ContentCachingResponseWrapper response,
                                 ObjectNode logObject) {
        logRequestHeader(request, logObject);
    }

    protected void afterRequest(ContentCachingRequestWrapper request,
                                ContentCachingResponseWrapper response,
                                ObjectNode logString) {
        logRequestBody(request, logString);
        logResponse(response, logString);
    }
}
