package com.ldcc.javajwtskeleton.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_name")
    private String name;

    @Column(name = "member_password")
    private String password;

    @Column(name = "member_email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role")
    private MemberRoleType role;

    @Builder
    public Member(String memberId, String name, String password, String email, MemberRoleType role) {
        this.memberId = memberId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
    }

}
