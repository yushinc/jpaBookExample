package jpa.bookExample.service.book;

import jpa.bookExample.service.book.domain.Reservation;
import jpa.bookExample.service.book.repository.ReservationRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepositry reservationRepositry;

    // 도서 별 예약 내역 조회
    @Transactional
    public List<Reservation> reserveByBook(Long bookId, int offset, int limit) {
        List<Reservation> reserveByBook = reservationRepositry.findByBook(bookId, offset, limit);
        return reserveByBook;
    }

}
