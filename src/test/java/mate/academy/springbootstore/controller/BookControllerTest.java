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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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

    private String toJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    private <T> T fromJson(MvcResult result, Class<T> clazz) throws Exception {
        return objectMapper.readValue(result.getResponse().getContentAsString(), clazz);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Create a new book")
    void createBook_ValidRequestDto_success() throws Exception {

        CreateBookRequestDto requestDto = createBookRequestDto("978-3-16-148410-0");

        MvcResult result = mockMvc.perform(post("/books")
                        .content(toJson(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actual = fromJson(result, BookDto.class);

        assertNotNull(actual.getId());
        assertEquals(requestDto.getTitle(), actual.getTitle());
        assertEquals(requestDto.getAuthor(), actual.getAuthor());
        assertEquals(requestDto.getIsbn(), actual.getIsbn());
        assertEquals(requestDto.getPrice(), actual.getPrice());
        assertEquals(requestDto.getDescription(), actual.getDescription());
        assertEquals(requestDto.getCoverImage(), actual.getCoverImage());
        assertEquals(requestDto.getCategoryIds().size(), actual.getCategories().size());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete Book by id successfully")
    void deleteBookById_ValidId_success() throws Exception {

        CreateBookRequestDto requestDto = createBookRequestDto("978-3-16-148410-0");

        MvcResult createResult = mockMvc.perform(post("/books")
                        .content(toJson(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto createdBook = fromJson(createResult, BookDto.class);

        mockMvc.perform(delete("/books/{id}", createdBook.getId()))
                .andExpect(status().isNoContent());
    }
}
