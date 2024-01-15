package jpa.bookExample.service.member.repository;

import jpa.bookExample.service.member.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    public Loan findByBookIdAndMemberId(Long bookId, Long MemberId);

}
