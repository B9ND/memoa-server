package org.example.memoaserver.global.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Test", description = "테스트용 API")
public class TestController {
    @Operation(
            summary = "서버 접속 테스트용 코드입니다.",
            description = "get 이며 token 은 필요없습니다."
    )
    @GetMapping("/test")
    public String test() {
        return "Memoa server test";
    }
}
