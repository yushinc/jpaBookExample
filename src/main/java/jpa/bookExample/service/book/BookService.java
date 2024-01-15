package jpa.bookExample.service.book;

import jpa.bookExample.service.book.domain.Book;
import jpa.bookExample.service.book.repository.BookRepository;
import jpa.bookExample.service.member.domain.Loan;
import jpa.bookExample.service.member.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    // 카테고리 별 도서 조회
    @Transactional(readOnly = true)
    public List<Book> booksByCategory(Long categoryId, int offset, int limit) {
        List<Book> booksByCategory = bookRepository.findByCategory(categoryId, offset, limit);
        return booksByCategory;
    }

    // 책 반납하기
    @Transactional
    public void returnBook(Long bookId, Long memberId) {
        Loan loan = loanRepository.findByBookIdAndMemberId(bookId, memberId);
        loan.setReturnDate(LocalDate.now());
        // 책 isLoan false로 변경 (반납했으므로 대출 가능 상태)
        loan.getBook().returnBookStatus();
    }
}
