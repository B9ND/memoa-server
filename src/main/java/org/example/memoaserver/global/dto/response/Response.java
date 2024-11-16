package org.example.memoaserver.global.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class Response {
    private int status;

    private String message;

    public static Response ok(String message) {
        return new Response(HttpStatus.OK.value(), message);
    }

    public static Response created(String message) {
        return new Response(HttpStatus.CREATED.value(), message);
    }
}
