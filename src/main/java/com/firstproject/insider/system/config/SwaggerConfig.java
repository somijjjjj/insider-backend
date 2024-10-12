package com.firstproject.insider.system.config;

import static java.util.stream.Collectors.groupingBy;


import com.firstproject.insider.common.annotation.ApiErrorCodeExample;
import com.firstproject.insider.common.dto.ErrorResponseDto;
import com.firstproject.insider.common.response.ErrorResponse;
import com.firstproject.insider.system.exception.BaseErrorCode;
import com.firstproject.insider.system.exception.GlobalExceptionCode;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import jakarta.servlet.ServletContext;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
public class SwaggerConfig {

        @Bean
        public OpenAPI openAPI(ServletContext servletContext) {
//                String contextPath = servletContext.getContextPath();
//                Server server = new Server().url(contextPath);
//                return new OpenAPI().servers(List.of(server)).components((authSetting)).info(swaggerInfo());
                return new OpenAPI().info(swaggerInfo());
        }

//        private Components authSetting() {
//                return new Components()
//                        .addSecuritySchemes(
//                                "access-token",
//                                new SecurityScheme()
//                                        .type(Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT")
//                                        .in(In.HEADER)
//                                        .name("Authorization"));
//        }

        private Info swaggerInfo() {
                License license = new License();
                license.setUrl("https://github.com/somijjjjj/insider-backend");
                license.setName("insider-backend");

                return new Info()
                        .version("v0.0.1")
                        .title("INSIDER API Docs")
                        .description("Insider API 문서 입니다.")
                        .license(license);
        }

        /**
         * 커스텀 어노테이션 정보 가져오기
         */
        @Bean
        public OperationCustomizer customize() {
                return (Operation operation, HandlerMethod handlerMethod) -> {
                        ApiErrorCodeExample apiErrorCodeExample =
                                handlerMethod.getMethodAnnotation(ApiErrorCodeExample.class);
                        // ApiErrorCodeExample 어노테이션 단 메소드 적용
                        if (apiErrorCodeExample != null) {
                            // 특정 오류 코드 처리
                            generateErrorCodeResponseExample(operation, apiErrorCodeExample.value());
                        }

                        return operation;
                };
        }

        /**
         * BaseErrorCode 타입의 이넘값들을 문서화 시킵니다.
         * ExplainError 어노테이션으로 부가설명을 붙일수있습니다.
         * 필드들을 가져와서 예시 에러 객체를 동적으로 생성해서 예시값으로 붙입니다.
         */
        private void generateErrorCodeResponseExample(Operation operation,
                                                      Class<? extends BaseErrorCode> type) {
                ApiResponses responses = operation.getResponses();
                // 해당 이넘에 선언된 에러코드들의 목록을 가져옵니다.
                BaseErrorCode[] errorCodes = type.getEnumConstants();

                // GlobalExceptionCode의 에러코드 목록을 가져옵니다.
                BaseErrorCode[] globalErrorCodes = GlobalExceptionCode.class.getEnumConstants();

                // 두 배열을 합쳐서 새로운 배열로 생성
                errorCodes = Stream.concat(Arrays.stream(errorCodes), Arrays.stream(globalErrorCodes)).toArray(BaseErrorCode[]::new);

                // 400, 401, 404 등 에러코드의 상태코드들로 리스트로 모읍니다.
                // 400 같은 상태코드에 여러 에러코드들이 있을 수 있습니다.
                Map<Integer, List<ExampleHolder>> statusWithExampleHolders =
                        Arrays.stream(errorCodes)
                                .map(
                                        baseErrorCode -> {
                                                try {
                                                        ErrorResponseDto errorResponseDto = baseErrorCode.getErrorReason();
                                                        return ExampleHolder.builder()
                                                                .holder(
                                                                        getSwaggerExample(
                                                                                baseErrorCode.getExplainError(),
                                                                                errorResponseDto))
                                                                .status(errorResponseDto.getCode().value())
                                                                .name(errorResponseDto.getStatus())
                                                                .build();
                                                } catch (NoSuchFieldException e) {
                                                        throw new RuntimeException(e);
                                                }
                                        })
                                .collect(groupingBy(ExampleHolder::getStatus));
                // response 객체들을 responses 에 넣습니다.
                addExamplesToResponses(responses, statusWithExampleHolders);
        }


        private Example getSwaggerExample(String value, ErrorResponseDto errorResponseDto) {
            //ErrorResponse 는 클라이언트한 실제 응답하는 공통 에러 응답 객체입니다.
            ErrorResponse errorResponse = new ErrorResponse(errorResponseDto);
            Example example = new Example();
            example.description(value);
            example.setValue(errorResponse);
            return example;
        }

        private void addExamplesToResponses(
            ApiResponses responses, Map<Integer, List<ExampleHolder>> statusWithExampleHolders) {
            statusWithExampleHolders.forEach(
                    (status, v) -> {
                            Content content = new Content();
                            MediaType mediaType = new MediaType();
                            // 상태 코드마다 ApiResponse을 생성합니다.
                            ApiResponse apiResponse = new ApiResponse();
                            //  List<ExampleHolder> 를 순회하며, mediaType 객체에 예시값을 추가합니다.
                            v.forEach(
                                    exampleHolder -> {
                                            mediaType.addExamples(
                                                    exampleHolder.getName(), exampleHolder.getHolder());
                                    });
                            // ApiResponse 의 content 에 mediaType을 추가합니다.
                            content.addMediaType("application/json", mediaType);
                            apiResponse.setContent(content);
                            // 상태코드를 key 값으로 responses 에 추가합니다.
                            responses.addApiResponse(status.toString(), apiResponse);
                    });
        }


}
