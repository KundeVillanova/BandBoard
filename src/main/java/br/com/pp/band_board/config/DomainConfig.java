package br.com.pp.band_board.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("br.com.pp.band_board")
@EnableJpaRepositories("br.com.pp.band_board")
@EnableTransactionManagement
public class DomainConfig {
}
