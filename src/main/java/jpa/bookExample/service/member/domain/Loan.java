package jpa.bookExample.service.member.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jpa.bookExample.service.book.domain.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@Getter @Setter
public class Loan {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="loan_id")
    private Long id;

    private LocalDate loanDate;

    private LocalDate returnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
    private Book book;



    // 대출하기 메소드
    public static Loan createLoan(Member member, Book book) {
        Loan loan = new Loan();
        loan.setLoanDate(LocalDate.now());
        loan.setMember(member);
        loan.setBook(book);

        book.setIsLoan(true);

        return loan;
    }

    // 반납하기 메소드
    public void returnLoan(Long bookId, Long memberId) {

        this.setReturnDate(LocalDate.now());
    }

}
