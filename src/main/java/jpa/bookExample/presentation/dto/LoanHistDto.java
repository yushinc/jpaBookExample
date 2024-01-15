package jpa.bookExample.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoanHistDto {

    private Long loanId;
    private String loanDate;
    private String returnDate;
    private Long memberId;
    private Long bookId;
}
