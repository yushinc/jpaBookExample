package jpa.bookExample.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookDto {

    private Long bookId;
    private String ISBN;
    private String author;
    private Boolean isLoan; // 이미 대출된 책은 빌릴 수 없음
    private Boolean isReserve; // 이미 예약된 책은 가장 먼저 예약한 사람만 대출 가능
    private String category;
}
