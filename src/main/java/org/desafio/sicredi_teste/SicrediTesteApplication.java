package org.desafio.sicredi_teste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SicrediTesteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SicrediTesteApplication.class, args);
    }

}
