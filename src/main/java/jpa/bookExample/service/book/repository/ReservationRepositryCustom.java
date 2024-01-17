package jpa.bookExample.service.book.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jpa.bookExample.service.book.domain.Reservation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ReservationRepositryCustom {

    private final EntityManager em;

    // 도서 별 예약 내역 조회 (페이징.ver)
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

    // 특정 책의 예약내역 찾기
    public Reservation findByBookId(Long bookId) {
        List<Reservation> reservations = em.createQuery("select r from Reservation r" +
                        " join fetch r.book b" +
                        " where r.book.id = :bookId", Reservation.class)
                .setParameter("bookId", bookId)
                .getResultList();

        if (!reservations.isEmpty()) {
            // 결과가 존재하는 경우 첫 번째 예약 내역 반환
            return reservations.get(0);
        } else {
            // 결과가 없는 경우
            return null; // 또는 예외를 던지거나 다른 처리 수행
        }

    }

}
