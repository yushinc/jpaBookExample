package jpa.bookExample.service.book.repository;


import jakarta.persistence.EntityManager;
import jpa.bookExample.service.book.domain.Reservation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ReservationRepositry {

    private final EntityManager em;

    // 도서 별 예약 내역 조회
    // reservation 입장에서 book과 member는 ManyToOne 관계이므로 fetch join으로 최적화 (컬렉션 조회가 아님)
    public List<Reservation> findByBook(Long bookId, int offset, int limit) {
        return em.createQuery(
                "select r from Reservation r" +
                        " join fetch r.book b" +
                        " join fetch r.member m" +
                        " where r.book.id = :bookId", Reservation.class)
                .setParameter("bookId", bookId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
