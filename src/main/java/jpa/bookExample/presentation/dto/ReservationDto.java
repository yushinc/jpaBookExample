package jpa.bookExample.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class ReservationDto {

    private Long reserveId;
    private Long bookId;
    private Long memberId;
    private String reserveDate;
}
