package jpa.bookExample.service.member.repository;

import jakarta.persistence.EntityManager;
import jpa.bookExample.service.book.domain.Book;
import jpa.bookExample.service.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}
