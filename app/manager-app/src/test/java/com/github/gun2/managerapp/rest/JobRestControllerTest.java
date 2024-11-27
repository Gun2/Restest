package com.github.gun2.managerapp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gun2.managerapp.controller.rest.JobRestController;
import com.github.gun2.managerapp.exception.GlobalExceptionHandler;
import com.github.gun2.managerapp.form.request.JobRequest;
import com.github.gun2.managerapp.service.JobService;
import com.github.gun2.managerapp.spreader.JobChangeDataSpreader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@ExtendWith(MockitoExtension.class)
@Transactional
@TestPropertySource(locations = "classpath:/application-local.yml")
class JobRestControllerTest {

    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();



    @Nested
    class ValidationTest{
        @Mock
        JobService jobService;
        @Mock
        JobChangeDataSpreader changeDataSpreader;
        @BeforeEach
        void init(){
            mockMvc = MockMvcBuilders.standaloneSetup(new JobRestController(jobService, changeDataSpreader))
                    .setControllerAdvice(GlobalExceptionHandler.class)
                    .build();
        }

        @DisplayName("empty param으로 save요청시 validation error 모두 발생")
        @Test
        void emptyBodySaveValidation() throws Exception{
            /** given */
            JobRequest jobRequest = new JobRequest();
            String body = objectMapper.writeValueAsString(jobRequest);

            /** when */
            String expectByErrorField = "$.errors[?(@.field == '%s')]";
            MvcResult result = mockMvc.perform(
                    post("/api/v1/jobs")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body)
                    ).andExpect(status().isBadRequest())
                    .andExpect(jsonPath(expectByErrorField, "title").exists())
                    .andExpect(jsonPath(expectByErrorField, "method").exists())
                    .andExpect(jsonPath(expectByErrorField, "url").exists())
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();
            /** then */
            log.info(response.getContentAsString());
        }

        @DisplayName("empty param으로 update요청시 validation error 모두 발생")
        @Test
        void emptyBodyUpdateValidation() throws Exception{
            /** given */
            JobRequest jobRequest = new JobRequest();
            String body = objectMapper.writeValueAsString(jobRequest);

            /** when */
            String expectByErrorField = "$.errors[?(@.field == '%s')]";
            MvcResult result = mockMvc.perform(
                            put("/api/v1/jobs")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(body)
                    ).andExpect(status().isBadRequest())
                    .andExpect(jsonPath(expectByErrorField, "title").exists())
                    .andExpect(jsonPath(expectByErrorField, "method").exists())
                    .andExpect(jsonPath(expectByErrorField, "url").exists())
                    .andReturn();

            MockHttpServletResponse response = result.getResponse();
            /** then */
            log.info(response.getContentAsString());
        }
    }

}