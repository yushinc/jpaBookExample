package jpa.bookExample.service.book.domain;

import jakarta.persistence.*;
import jpa.bookExample.service.member.domain.Loan;
import jpa.bookExample.service.member.domain.Member;
import lombok.*;

import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@Getter @Setter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reservation_id")
    private Long id;

    private LocalDate reserveDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
    private Book book;


    // 예약하기 메소드
    public static Reservation createReserve(Member member, Book book) {
        Reservation reservation = new Reservation();
        reservation.setMember(member);
        reservation.setBook(book);
        reservation.setReserveDate(LocalDate.now());

        return reservation;
    }
}
