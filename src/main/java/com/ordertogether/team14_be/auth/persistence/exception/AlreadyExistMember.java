package com.ordertogether.team14_be.auth.persistence.exception;

public class AlreadyExistMember extends RuntimeException {
	public AlreadyExistMember() {
		super("이미 회원이 존재합니다.");
	}
}
