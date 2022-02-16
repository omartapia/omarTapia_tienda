package com.project.tapia.tienda.configuration;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@org.springframework.context.annotation.Configuration
@EnableWebFlux
public class Configuration {

    public static final String DATE_PATTERN = "dd-MM-yyyy";
    public static final String DATE_TIME_PATTERN = "dd-MM-yyyy hh:mm";

    @Value("${tienda.base.url}")
    private String baseUrl;

    @Bean
    public WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(@Qualifier("dataSource") final DataSource dataSource) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("/data.sql"));
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }

    @Bean
    @Primary
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
            builder.serializerByType(LocalDate.class, new LocalDateSerializer(localDateFormatter));
            builder.deserializerByType(LocalDate.class, new LocalDateDeserializer(localDateFormatter));

            DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(localDateTimeFormatter));
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(localDateTimeFormatter));
        };
    }

}
