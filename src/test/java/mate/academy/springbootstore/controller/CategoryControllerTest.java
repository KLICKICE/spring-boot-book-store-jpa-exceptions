package mate.academy.springbootstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mate.academy.springbootstore.dto.category.CategoryDto;
import mate.academy.springbootstore.dto.category.CreateCategoryRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts = "/testData/categories.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("Create a new category successfully")
    void createCategory_ValidRequestDto_success() throws Exception {
        // Given
        CreateCategoryRequestDto requestDto = createCategoryRequestDto();
        CategoryDto expected = createExpectedCategoryDto(requestDto);

        // When
        MvcResult result = mockMvc.perform(post("/categories")
                        .content(toJson(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        CategoryDto actual = fromJson(result, CategoryDto.class);

        // Then
        assertNotNull(actual.getId());
        assertEquals(expected, actual);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts = "/testData/categories.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("Delete category by id successfully")
    void deleteCategoryById_ValidId_success() throws Exception {
        mockMvc.perform(delete("/categories/{id}", 200))
                .andExpect(status().isNoContent());
    }

    private CreateCategoryRequestDto createCategoryRequestDto() {
        CreateCategoryRequestDto dto = new CreateCategoryRequestDto();
        dto.setName("Fiction");
        dto.setDescription("Books with fictional stories");
        return dto;
    }

    private CategoryDto createExpectedCategoryDto(CreateCategoryRequestDto requestDto) {
        CategoryDto dto = new CategoryDto();
        dto.setName(requestDto.getName());
        dto.setDescription(requestDto.getDescription());
        return dto;
    }

    private String toJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    private <T> T fromJson(MvcResult result, Class<T> clazz) throws Exception {
        return objectMapper.readValue(result.getResponse().getContentAsString(), clazz);
    }
}
