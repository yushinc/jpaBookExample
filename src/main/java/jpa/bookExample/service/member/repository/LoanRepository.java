package jpa.bookExample.service.member.repository;

import jpa.bookExample.service.member.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    public List<Loan> findByBookIdAndMemberId(Long bookId, Long MemberId);

    public List<Loan> findByMemberId(Long memberId);
}
