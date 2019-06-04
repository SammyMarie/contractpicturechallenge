package uk.co.nesistec.contractpicturechallenge.config;

import io.undertow.server.handlers.RequestDumpingHandler;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class PictureApiConfiguration {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(applicationApiInfo())
                                                      .groupName("picture-apis")
                                                      .select()
                                                      .apis(RequestHandlerSelectors.basePackage("uk.co.nesistec.contractpicturechallenge.api"))
                                                      .paths(PathSelectors.any())
                                                      .build();
    }

    private ApiInfo applicationApiInfo() {
        return new ApiInfoBuilder().title("Picture-Challenge")
                                   .description("Picture API for developers")
                                   .version("1.0")
                                   .build();
    }

    @Bean
    public UndertowServletWebServerFactory undertowServletWebServerFactory() {
        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
        factory.addDeploymentInfoCustomizers(deploymentInfo ->
                                                     deploymentInfo.addInitialHandlerChainWrapper(RequestDumpingHandler::new));

        return factory;
    }
}
