package jpa.bookExample.service.book.repository;

import jpa.bookExample.service.book.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // 예약 내역 찾기
    Reservation findByBookIdAndMemberId(Long bookId, Long memberId);


}
