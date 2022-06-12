package com.pokedex.pokeAPI.Utilities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.plugin.core.SimplePluginRegistry;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    @Value("${app.host}")
    private String host;

    @Value("${app.port}")
    private String port;

    @Value("${pom.version}")
    private String version;
    

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("PokeApi")
            .version(version) //get from application
            .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .host(host + ':' + port)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors
            .basePackage("com.pokedex.pokeAPI"))
            .paths(PathSelectors.any())
            .build();
    }

    @Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
    }
}