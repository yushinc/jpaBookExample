package jpa.bookExample.service.member;

import jpa.bookExample.service.member.domain.Loan;
import jpa.bookExample.service.member.repository.LoanRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepositoryCustom loanRepositoryCustom;

    // 멤버 별 대출 내역 조회
    @Transactional
    public List<Loan> loansByMember(Long memberId, int offset, int limit) {
        List<Loan> loansByMember = loanRepositoryCustom.findByMember(memberId, offset, limit);
        return loansByMember;
    }
}
