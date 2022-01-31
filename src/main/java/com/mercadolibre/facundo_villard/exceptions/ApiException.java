package com.mercadolibre.facundo_villard.exceptions;

import java.util.function.Supplier;

public class ApiException extends RuntimeException implements Supplier<ApiException> {


	private static final long serialVersionUID = 1L;

	private final String code;
	private final String description;
	private final Integer statusCode;

	public ApiException(String code, String description, Integer statusCode) {
		super(description);
		this.code = code;
		this.description = description;
		this.statusCode = statusCode;
	}

	public ApiException(String code, String description, Integer statusCode, Throwable cause) {
		super(description, cause);
		this.code = code;
		this.description = description;
		this.statusCode = statusCode;
	}

	public String getCode() {
		return this.code;
	}

	public String getDescription() {
		return this.description;
	}

	public Integer getStatusCode() {
		return this.statusCode;
	}

	@Override
	public ApiException get() {
		return this;
	}
}
