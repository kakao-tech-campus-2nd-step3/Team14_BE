package com.ordertogether.team14_be.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.ordertogether.team14_be.helper.MemberDatabaseHelper;
import com.ordertogether.team14_be.member.application.dto.MemberInfoResponse;
import com.ordertogether.team14_be.member.application.service.MemberService;
import com.ordertogether.team14_be.member.persistence.MemberRepository;
import com.ordertogether.team14_be.member.persistence.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class MemberTest {

	@Autowired private MemberRepository memberRepository;
	@Autowired private MemberService memberService;

	@Autowired private MemberDatabaseHelper memberDatabaseHelper;

	@Test
	@DisplayName("회원 저장 테스트")
	void saveMember() {
		Member expected = new Member("email1", "phone1", "deliveryName1");
		memberService.registerMember(expected);
		Member actual = memberRepository.findById(expected.getId()).get();

		assertAll(
				() -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
				() -> assertThat(actual.getPhoneNumber()).isEqualTo(expected.getPhoneNumber()),
				() -> assertThat(actual.getDeliveryName()).isEqualTo(expected.getDeliveryName()));
	}

	@Test
	@DisplayName("회원 수정 테스트")
	void modifyMember() {
		// given
		Member expected = new Member("email1", "phone1", "deliveryName1");
		memberRepository.save(expected);

		// when
		String newDeliveryName = "deliveryName2";
		String newPhoneNumber = "phone2";
		MemberInfoResponse memberInfoResponse =
				memberService.modifyMember(expected.getId(), newDeliveryName, newPhoneNumber);

		// then
		assertAll(
				() -> assertThat(memberInfoResponse.deliveryName()).isEqualTo(newDeliveryName),
				() -> assertThat(memberInfoResponse.phoneNumber()).isEqualTo(newPhoneNumber));
	}

	@Test
	@DisplayName("회원 조회 테스트")
	void getMember() {
		// given
		Member expected = new Member("email1", "phone1", "deliveryName1");
		memberRepository.save(expected);

		// when
		Member actual = memberRepository.findById(expected.getId()).get();

		// then
		assertAll(
				() -> assertThat(actual.getId()).isNotNull(),
				() -> assertThat(actual.getId()).isEqualTo(expected.getId()),
				() -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
				() -> assertThat(actual.getPhoneNumber()).isEqualTo(expected.getPhoneNumber()),
				() -> assertThat(actual.getDeliveryName()).isEqualTo(expected.getDeliveryName()));
	}

	@Test
	@DisplayName("회원 삭제 테스트")
	void deleteMember() {
		// given
		Member expected = new Member("email1", "phone1", "deliveryName1");
		memberRepository.save(expected);

		// when
		memberService.deleteMember(expected.getId());

		// then
		assertThat(memberRepository.findById(expected.getId())).isEmpty();
	}
}
