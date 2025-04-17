package com.example.backend.member.repository;

import com.example.backend.member.model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MemberRepository extends MongoRepository<Member,String> {
   List<Member> findByRole(String role);
}
