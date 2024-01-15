package jpa.bookExample.service.book.domain;

import jakarta.persistence.*;
import jpa.bookExample.service.member.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
@Getter @Setter
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id")
    private Long id;

    private String ISBN;
    private String author;
    private Boolean isLoan; // 이미 대출된 책은 빌릴 수 없음
    private Boolean isReserve; // 이미 예약된 책은 가장 먼저 예약한 사람만 대출 가능

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    // 책 반납 메소드
    public void returnBookStatus() {
        this.isLoan = false;
    }
}
