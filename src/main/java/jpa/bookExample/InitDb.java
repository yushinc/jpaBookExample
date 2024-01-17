package jpa.bookExample;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpa.bookExample.service.book.BookService;
import jpa.bookExample.service.book.domain.Book;
import jpa.bookExample.service.book.domain.Category;
import jpa.bookExample.service.book.domain.Reservation;
import jpa.bookExample.service.member.domain.Address;
import jpa.bookExample.service.member.domain.Loan;
import jpa.bookExample.service.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    // 스프링빈 모두 조회 후 실행
    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit() {
            Category category1 = new Category("novel");
            Category category2 = new Category("art");
            Category category3 = new Category("science");

            em.persist(category1);
            em.persist(category2);
            em.persist(category3);

            Member member1 = createMember("userA", "서울", "1", "1111");
            em.persist(member1);

            Book book1 = createBook("1234", "최유신", category1, false, false);
            em.persist(book1);

            Book book2 = createBook("5678", "박유신", category2, false, false);
            em.persist(book2);

            // 대출 메소드 한 번 실행에 책 1개 대출되므로 Book... book 사용하지 않음
            Loan loan1 = Loan.createLoan(member1, book1);
            Loan loan2 = Loan.createLoan(member1, book2);

            em.persist(loan1);
            em.persist(loan2);

            // member1이 대출한 book1을 member2가 예약하기 위해 dbInit() 메소드 하나에 모든 생성 코드를 작성하였다.
            Member member2 = createMember("userB", "인천", "2", "2222");
            em.persist(member2);

            Book book3 = createBook("8765", "김유신", category3,  false, false);
            em.persist(book3);

            Book book4 = createBook("4321", "이유신", category3, false, false);
            em.persist(book4);

            Loan loan = Loan.createLoan(member2, book3);
            em.persist(loan);

            // 같은 책을 두 명 이상 예약하기 위해 멤버3 생성
            Member member3 = createMember("userC", "경기", "3", "3333");
            em.persist(member3);

            Reservation reservation = Reservation.createReserve(member3, book1);

            em.persist(reservation);
        }


        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        private Book createBook(String ISBN, String author, Category category, Boolean isLoan, Boolean isReserve) {
            Book book = new Book();
            book.setISBN(ISBN);
            book.setAuthor(author);
            book.setCategory(category);
            book.setIsLoan(isLoan);
            book.setIsReserve(isReserve);
            return book;
        }
    }
}
