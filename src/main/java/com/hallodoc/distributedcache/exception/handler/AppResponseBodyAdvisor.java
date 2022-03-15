package com.hallodoc.distributedcache.exception.handler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hallodoc.distributedcache.response.ErrorResponse;

@ControllerAdvice
public class AppResponseBodyAdvisor extends ResponseEntityExceptionHandler
		implements ResponseBodyAdvice<Object> {
	private static final Logger LOG = LoggerFactory.getLogger(AppResponseBodyAdvisor.class);
	private static final String EXCPETION_MESSAGE = "Exception occurred :: {}";

	@Autowired
	private ObjectMapper jsonMapper;
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		try {
			String strResponse = jsonMapper.writeValueAsString(body);
			LOG.info("Response body: {}", strResponse);
		} catch (JsonProcessingException e) {
		}
		return body;
	}

	public ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message, Exception exception) {
		LOG.error(EXCPETION_MESSAGE,exception);
		return ResponseEntity.status(status).body(new ErrorResponse(status.value(), message));
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		super.handleHttpRequestMethodNotSupported(ex, headers, status, request);
		return buildErrorResponse(status, ex.getLocalizedMessage(), ex);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		super.handleHttpMediaTypeNotSupported(ex, headers, status, request);
		return buildErrorResponse(status, ex.getLocalizedMessage(), ex);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
		String errMsg = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
		HttpStatus status = INTERNAL_SERVER_ERROR;
		
		return buildErrorResponse(status, errMsg, exception);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		
		String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .findAny().orElse(null);

        return buildErrorResponse(HttpStatus.BAD_REQUEST, errors, ex);
	}

	
	
}