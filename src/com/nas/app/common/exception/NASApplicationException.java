package com.nas.app.common.exception;

public class NASApplicationException extends Exception {
	public NASApplicationException() {
		super();
	}
	public NASApplicationException(Exception e){
		super(e);
	}
	public NASApplicationException(String e){
		super(e);
	}
}
