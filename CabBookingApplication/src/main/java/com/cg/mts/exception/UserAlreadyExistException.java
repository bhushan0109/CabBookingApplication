package com.cg.mts.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserAlreadyExistException extends Exception {
	private final static Logger logger = LogManager.getLogger(UserAlreadyExistException.class);
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistException(String message) {
        super(message);
        logger.info(message);
    }
}