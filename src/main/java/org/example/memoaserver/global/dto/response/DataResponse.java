package org.example.memoaserver.global.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public final class DataResponse<E> extends Response {

    private final E data;

    public DataResponse(HttpStatus status, String message, E data) {
        super(status.value(), message);
        this.data = data;
    }

    public static <E> DataResponse<E> ok(String message, E data) {
        return new DataResponse<>(HttpStatus.OK, message, data);
    }

    public static <E> DataResponse<E> created(String message, E data) {
        return new DataResponse<>(HttpStatus.CREATED, message, data);
    }
}
