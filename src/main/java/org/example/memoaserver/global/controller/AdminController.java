package org.example.memoaserver.global.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "admin", description = "어드민 권한 테스트용")
@RestController
public class AdminController {

    @Operation(
            summary = "어드민 권한이 있다면 접속이 가능합니다.",
            description = "어드민 권한을 테스트 할때 ROLE_ADMIN 필요"
    )
    @GetMapping("/admin")
    public String admin() {
        return "admin_page";
    }
}