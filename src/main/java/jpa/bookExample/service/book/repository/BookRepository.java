package jpa.bookExample.service.book.repository;

import jakarta.persistence.EntityManager;
import jpa.bookExample.service.book.domain.Book;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
@AllArgsConstructor
public class BookRepository {

    private final EntityManager em;

    // 카테고리 별 도서 조회
    public List<Book> findByCategory(Long categoryId, int offset, int limit) {
        return em.createQuery(
                "select b from Book b" +
                        " join fetch b.category c" +
                        " where b.category.id = :categoryId", Book.class)
                .setParameter("categoryId", categoryId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    // 책 개별 조회
    public Book findById(Long bookId) {
        return em.createQuery(
                "select b from Book b" +
                        " where b.id = :bookId", Book.class)
                .setParameter("bookId", bookId)
                .getSingleResult();
    }
}
