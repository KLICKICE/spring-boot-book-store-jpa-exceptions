package mate.academy.springbootstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mate.academy.springbootstore.dto.book.BookDto;
import mate.academy.springbootstore.dto.book.CreateBookRequestDto;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateBookRequestDto createBookRequestDto(String isbn) {
        CreateBookRequestDto dto = new CreateBookRequestDto();
        dto.setAuthor("Sunless");
        dto.setTitle("Lost From Light");
        dto.setIsbn(isbn);
        dto.setPrice(new BigDecimal("199.99"));
        dto.setDescription("A mysterious tale of light and shadow.");
        dto.setCoverImage("cover-image-url.jpg");
        dto.setCategoryIds(List.of(1L, 2L));
        return dto;
    }

    private BookDto createExpectedBookDto(CreateBookRequestDto requestDto) {
        BookDto dto = new BookDto();
        dto.setAuthor(requestDto.getAuthor());
        dto.setTitle(requestDto.getTitle());
        dto.setIsbn(requestDto.getIsbn());
        dto.setPrice(requestDto.getPrice());
        dto.setDescription(requestDto.getDescription());
        dto.setCoverImage(requestDto.getCoverImage());
        return dto;
    }

    private String toJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    private <T> T fromJson(MvcResult result, Class<T> clazz) throws Exception {
        return objectMapper.readValue(result.getResponse().getContentAsString(), clazz);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Create a new book successfully")
    void createBook_ValidRequestDto_success() throws Exception {
        // Given
        CreateBookRequestDto requestDto = createBookRequestDto("978-1-23-456789-0");
        BookDto expected = createExpectedBookDto(requestDto);

        // When
        MvcResult result = mockMvc.perform(post("/books")
                        .content(toJson(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actual = fromJson(result, BookDto.class);

        // Then
        assertNotNull(actual.getId());
        assertAll(
                () -> assertEquals(expected.getTitle(), actual.getTitle()),
                () -> assertEquals(expected.getAuthor(), actual.getAuthor()),
                () -> assertEquals(expected.getIsbn(), actual.getIsbn()),
                () -> assertEquals(expected.getPrice(), actual.getPrice()),
                () -> assertEquals(expected.getDescription(), actual.getDescription()),
                () -> assertEquals(expected.getCoverImage(), actual.getCoverImage()),
                () -> assertEquals(requestDto.getCategoryIds().size(), actual.getCategories().size())
        );
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts = "/testdata/books.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("Delete Book by id successfully")
    void deleteBookById_ValidId_success() throws Exception {
        // When & Then
        mockMvc.perform(delete("/books/{id}", 100))
                .andExpect(status().isNoContent());
    }
}
