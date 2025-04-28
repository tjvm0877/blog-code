package com.its.transactional;

public class NotEnoughMoneyException extends Exception {
	public NotEnoughMoneyException(String message) {
		super(message);
	}
}
