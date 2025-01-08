package com.ldcc.javajwtskeleton.domain.member.repository;

import com.ldcc.javajwtskeleton.domain.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @EntityGraph(attributePaths = {"role"})
    Optional<Member> findByEmail(String email);

    Optional<Member> findByMemberId(String memberId);

    boolean existsByEmail(String email);

    boolean existsByMemberId(String memberId);

}
