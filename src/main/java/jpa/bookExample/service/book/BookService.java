package jpa.bookExample.service.book;

import jakarta.persistence.EntityManager;
import jpa.bookExample.service.book.domain.Book;
import jpa.bookExample.service.book.domain.Reservation;
import jpa.bookExample.service.book.repository.BookRepository;
import jpa.bookExample.service.book.repository.ReservationRepository;
import jpa.bookExample.service.book.repository.ReservationRepositryCustom;
import jpa.bookExample.service.member.domain.Loan;
import jpa.bookExample.service.member.domain.Member;
import jpa.bookExample.service.member.repository.LoanRepository;
import jpa.bookExample.service.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;
    private final ReservationRepositryCustom reserveRepositoryCustom;
    private final ReservationRepository reserveRepository;
    private final EntityManager em;


    // 카테고리 별 도서 조회
    @Transactional(readOnly = true)
    public List<Book> booksByCategory(Long categoryId, int offset, int limit) {
        List<Book> booksByCategory = bookRepository.findByCategory(categoryId, offset, limit);
        return booksByCategory;
    }

    // 책 반납하기
    @Transactional
    public void returnBook(Long bookId, Long memberId) {
        List<Loan> loanList = loanRepository.findByBookIdAndMemberId(bookId, memberId);

        for (Loan loan : loanList) {
            // 반납 일자 수정
            loan.setReturnDate(LocalDate.now());

            // 책 isLoan false로 변경 (반납했으므로 대출 가능 상태)
            loan.getBook().returnBookStatus();
        }
    }

    @Transactional
    // 책 대출하기
    public String loanBook(Long memberId, Long bookId) {

        Book book = bookRepository.findById(bookId);
        Member member = memberRepository.findById(memberId).get();

        // 해당 회원이 대출한 책의 수가 2개 미만인지 확인
        List<Loan> loanList = loanRepository.findByMemberId(memberId);

        int count = 0;
        for (Loan loan : loanList) {
            // 해당 회원의 대출 내역에 존재하는 책이 반납되지 않은 경우 = 대출 중인 책의 권 수
            if (loan.getBook().getIsLoan()) {
                count++;
            }
        }

        System.out.println("count = " +count);
        // 대출한 책이 2권 이상이라면
        if (count >= 2) {
            return "2권 이상 대출은 불가능합니다.";
        }
        // 책이 이미 대출 중이라면 해당 책 자동 예약
        else if (book.getIsLoan()) {
            //Reservation reserveHist = reserveRepository.findByBookIdAndMemberId(bookId, memberId);

            Reservation reservation = Reservation.createReserve(member, book);
            em.persist(reservation);
            return "해당 책은 이미 대출 중이므로 자동 예약되었습니다.";

        }
        // 책이 예약 중이라면 + 책이 예약/대출 둘 다 없는 상태라면
        else {
            // 책이 예약 중이라면
            if (book.getIsReserve()) {

                // 조회한 책의 첫번째 예약 내역 조회
                Reservation reserveHist = reserveRepositoryCustom.findByBookId(bookId);

                // 해당 예약 내역의 회원이 조회한 회원이라면 (= 조회한 회원이 첫번째 예약자)
                if (member.equals(reserveHist.getMember())) {
                    // 대출 가능
                    Loan loan = Loan.createLoan(member, book);
                    em.persist(loan);

                    // 예약 기록 삭제
                    em.remove(reserveHist);

                    return "대출되었습니다.";
                }
                // 첫번째 예약자가 아니라면
                else {
                    return "첫번째 예약자가 아니므로 대출이 불가능합니다.";
                }

            }
            // 책이 예약/대출 둘 다 없는 상태라면
            else {
                Loan loan = Loan.createLoan(member, book);
                em.persist(loan);

                return "대출되었습니다.";
            }
        }
    }
}
