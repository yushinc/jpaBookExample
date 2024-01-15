package jpa.bookExample.presentation.controller;

import jpa.bookExample.presentation.dto.BookDto;
import jpa.bookExample.presentation.dto.LoanHistDto;
import jpa.bookExample.presentation.dto.ReservationDto;
import jpa.bookExample.service.book.BookService;
import jpa.bookExample.service.book.domain.Book;
import jpa.bookExample.service.book.domain.Reservation;
import jpa.bookExample.service.book.ReservationService;
import jpa.bookExample.service.member.LoanService;
import jpa.bookExample.service.member.domain.Loan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class apiController {

    private final BookService bookService;
    private final ReservationService reservationService;
    private final LoanService loanService;

    // 카테고리 별 도서 조회
    // fetchjoin + dto 반환
    @GetMapping("/api/categories/{categoryId}")
    public Result bookListByCategory(@PathVariable Long categoryId,
                                     @RequestParam(value="offset", defaultValue = "0") int offset,
                                     @RequestParam(value="limit", defaultValue = "1") int limit) {

        List<Book> booksByCategory = bookService.booksByCategory(categoryId, offset, limit);

        // 엔티티 -> dto 변환
        List<BookDto> bookDtoList =
                booksByCategory.stream()
                        .map(b -> new BookDto(b.getId(), b.getISBN(), b.getAuthor(),
                                b.getIsLoan(), b.getIsReserve(), b.getCategory().getName()))
                        .collect(Collectors.toList());

        return new Result(bookDtoList.size(), bookDtoList);
    }



    // 도서 예약목록 조회
    // fetchjoin + dto 반환
    @GetMapping("/api/reservations/{bookId}")
    public Result reserveListByBook(@PathVariable Long bookId,
                                    @RequestParam(value="offset", defaultValue = "0") int offset,
                                    @RequestParam(value="limit", defaultValue = "1") int limit) {

        List<Reservation> reservesByBook = reservationService.reserveByBook(bookId, offset, limit);

        List<ReservationDto> reserveDtoList = reservesByBook.stream()
                .map(r -> new ReservationDto(r.getId(), r.getBook().getId(), r.getMember().getId(),
                        r.getReserveDate().toString()))
                .collect(Collectors.toList());

        return new Result(reserveDtoList.size(), reserveDtoList);
    }

    // 특정 회원 대출이력 조회
    // fetchjoin + dto 반환
    @GetMapping("/api/members/{memberId}/loans/history")
    public Result loanListByMember(@PathVariable Long memberId,
                                   @RequestParam(value="offset", defaultValue = "0") int offset,
                                   @RequestParam(value="limit", defaultValue = "1") int limit) {

        List<Loan> loansByMember = loanService.loansByMember(memberId, offset, limit);

        List<LoanHistDto> loanHistDtoList = loansByMember.stream()
                .map(l -> {
                    String returnDateStr = (l.getReturnDate() != null) ? l.getReturnDate().toString() : null;
                    return new LoanHistDto(l.getId(), l.getLoanDate().toString(), returnDateStr,
                            l.getMember().getId(), l.getBook().getId());
                })
                .collect(Collectors.toList());

        return new Result(loanHistDtoList.size(), loanHistDtoList);
    }

    // 책 반납하기
    @GetMapping("/api/return")
    public String returnBook(@RequestParam(value="bookId") Long bookId,
                           @RequestParam(value="memberId") Long memberId) {
        bookService.returnBook(bookId, memberId);
        return "반납되었습니다.";
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }
}
