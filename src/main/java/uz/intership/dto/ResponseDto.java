package uz.intership.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto<T> {
    /**
     * Response code:
     * <p> -2 - Validation error </p>
     * <p> -1 - Not found </p>
     * <p> 0 - OK </p>
     * <p> 1 - Unexpected error </p>
     * <p> 2 - Database error </p>
     */
    private Integer code;
    private String info;
    private boolean success;
    private T data;
}
