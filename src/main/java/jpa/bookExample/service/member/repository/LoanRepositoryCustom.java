package jpa.bookExample.service.member.repository;

import jakarta.persistence.EntityManager;
import jpa.bookExample.service.member.domain.Loan;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class LoanRepositoryCustom {

    private final EntityManager em;

    // 멤버 별 대출 내역 조회
    public List<Loan> findByMember(Long memberId, int offset, int limit) {
        return em.createQuery(
                        "select l from Loan l" +
                                " join fetch l.member m" +
                                " where l.member.id = : memberId", Loan.class)
                .setParameter("memberId", memberId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
