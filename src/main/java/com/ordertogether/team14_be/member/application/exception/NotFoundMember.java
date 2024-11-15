package com.ordertogether.team14_be.member.application.exception;

import java.util.NoSuchElementException;

public class NotFoundMember extends NoSuchElementException {
	public NotFoundMember() {
		super("회원정보가 존재하지 않습니다.");
	}
}
