package com.ordertogether.team14_be.memebr.persistence;

import java.util.Optional;
import com.ordertogether.team14_be.memebr.persistence.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByEmail(String email);

}
