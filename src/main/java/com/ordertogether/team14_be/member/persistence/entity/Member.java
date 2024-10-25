package com.ordertogether.team14_be.member.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "email")
	private String email;

	@Column(name = "point")
	private int point;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "delivery_name")
	private String deliveryName;

	@Column(name = "platform")
	private String platform;

	protected Member() {}

	public Member(String email, int point, String phoneNumber, String deliveryName, String platform) {
		this.email = email;
		this.point = point;
		this.phoneNumber = phoneNumber;
		this.deliveryName = deliveryName;
		this.platform = platform;
	}

	public Member(String email, String phoneNumber, String deliveryName) {
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.deliveryName = deliveryName;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public int getPoint() {
		return point;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public String getPlatform() {
		return platform;
	}

	public void modifyMemberInfo(String deliveryName, String phoneNumber) {
		this.deliveryName = deliveryName;
		this.phoneNumber = phoneNumber;
	}

	public void modifyMemberInfo(String deliveryName, String phoneNumber) {
		this.deliveryName = deliveryName;
		this.phoneNumber = phoneNumber;
	}
}
