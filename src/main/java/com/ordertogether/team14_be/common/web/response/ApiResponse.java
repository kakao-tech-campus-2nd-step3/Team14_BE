package com.ordertogether.team14_be.common.web.response;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

	private final Integer status;
	private final String message;
	private final T data;

	public static <T> ApiResponse<T> with(HttpStatus httpStatus, String message, @Nullable T data) {
		return new ApiResponse<>(httpStatus.value(), message, data);
	}
}
