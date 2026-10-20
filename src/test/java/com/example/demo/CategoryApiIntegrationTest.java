package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void categoryApiHandlesValidationNotFoundAndDuplicateSlug() throws Exception {
        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"name":"","slug":"","description":"invalid test"}
                    """))
            .andExpect(status().isBadRequest());

        mockMvc.perform(get("/api/categories/999999999"))
            .andExpect(status().isNotFound());

        String unique = UUID.randomUUID().toString().replace("-", "");
        String slug = "integration-" + unique;
        Long createdId = null;

        try {
            MvcResult createResult = mockMvc.perform(post("/api/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {"name":"Integration %s","slug":"%s","description":"Temporary integration test category"}
                        """.formatted(unique, slug)))
                .andExpect(status().isCreated())
                .andReturn();

            String responseBody = createResult.getResponse().getContentAsString();
            assertNotNull(responseBody);
            assertFalse(responseBody.contains("\"createdAt\":null"),
                "createdAt should be populated");
            assertFalse(responseBody.contains("\"updatedAt\":null"),
                "updatedAt should be populated");

            java.util.regex.Matcher matcher =
                java.util.regex.Pattern.compile("\"id\"\\s*:\\s*(\\d+)")
                    .matcher(responseBody);
            if (matcher.find()) {
                createdId = Long.parseLong(matcher.group(1));
            }
            assertNotNull(createdId, "Created category ID should be returned");

            mockMvc.perform(post("/api/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                        {"name":"Duplicate %s","slug":"%s","description":"Duplicate slug test"}
                        """.formatted(unique, slug)))
                .andExpect(status().isConflict());
        } finally {
            if (createdId != null) {
                mockMvc.perform(delete("/api/categories/{id}", createdId));
            }
        }
    }
}
