package org.example.memoaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MemoaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemoaServerApplication.class, args);
    }

}
