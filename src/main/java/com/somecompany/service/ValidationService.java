package com.somecompany.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ValidationService {

	@Value("${errorMsg.paramNotDecimalNum}")
	private String errorParamNotDecimalNum;

	public void validatePush(String paramStr) throws IllegalArgumentException {
		try {
			Double.valueOf(paramStr);
		} catch (NumberFormatException exception) {
			log.error(errorParamNotDecimalNum);
			throw new IllegalArgumentException(errorParamNotDecimalNum);
		}
	}
}
