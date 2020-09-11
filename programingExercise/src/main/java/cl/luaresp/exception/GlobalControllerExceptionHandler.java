package cl.luaresp.exception;

import java.time.LocalDateTime;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import cl.luaresp.model.ApiException;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

	@ExceptionHandler(value = { ConstraintViolationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiException> constraintViolationException(ConstraintViolationException ex, WebRequest request) {

		ApiException api = new ApiException();
		api.setTimestamp(LocalDateTime.now());
		api.setStatus(HttpStatus.BAD_REQUEST.value());
		api.setError(HttpStatus.BAD_REQUEST.name());
		api.setMessage(ex.getMessage());
		api.setPath(((ServletWebRequest)request).getRequest().getRequestURI().toString());

		return new ResponseEntity<>(api, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { BadRequestException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiException> noHandlerRequestException(BadRequestException ex, WebRequest request) {
		ApiException api = new ApiException();
		api.setTimestamp(LocalDateTime.now());
		api.setStatus(HttpStatus.BAD_REQUEST.value());
		api.setError(HttpStatus.BAD_REQUEST.name());
		api.setMessage(ex.getMessage());
		api.setPath(((ServletWebRequest)request).getRequest().getRequestURI().toString());

		return new ResponseEntity<>(api, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { NotFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ApiException> noHandlerFoundException(NotFoundException ex, WebRequest request) {
		ApiException api = new ApiException();
		api.setTimestamp(LocalDateTime.now());
		api.setStatus(HttpStatus.NOT_FOUND.value());
		api.setError(HttpStatus.NOT_FOUND.name());
		api.setMessage(ex.getMessage());
		api.setPath(((ServletWebRequest)request).getRequest().getRequestURI().toString());

		return new ResponseEntity<>(api, HttpStatus.NOT_FOUND);
	}


}
