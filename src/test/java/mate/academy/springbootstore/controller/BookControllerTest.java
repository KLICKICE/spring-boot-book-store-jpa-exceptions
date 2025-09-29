package mate.academy.springbootstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mate.academy.springbootstore.dto.book.BookDto;
import mate.academy.springbootstore.dto.book.CreateBookRequestDto;
import mate.academy.springbootstore.dto.category.CategoryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Create a new book")
    void createBook_ValidRequestDto_success() throws Exception {
        // Given
        CategoryDto cat1 = new CategoryDto();
        cat1.setId(1L);
        cat1.setName("Fiction");

        CategoryDto cat2 = new CategoryDto();
        cat2.setId(2L);
        cat2.setName("Fantasy");

        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setAuthor("Sunless");
        requestDto.setTitle("Lost From Light");
        requestDto.setIsbn("978-3-16-148410-0");
        requestDto.setPrice(new BigDecimal("199.99"));
        requestDto.setDescription("A mysterious tale of light and shadow.");
        requestDto.setCoverImage("cover-image-url.jpg");
        requestDto.setCategoryIds(List.of(1L, 2L));

        BookDto expected = new BookDto();
        expected.setAuthor(requestDto.getAuthor());
        expected.setTitle(requestDto.getTitle());
        expected.setIsbn(requestDto.getIsbn());
        expected.setPrice(requestDto.getPrice());
        expected.setDescription(requestDto.getDescription());
        expected.setCoverImage(requestDto.getCoverImage());
        expected.setCategories(List.of(cat1, cat2));

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        System.out.println(jsonRequest);

        // When
        MvcResult result = mockMvc.perform(post("/books")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andReturn();

        // Then
        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), BookDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(expected.getTitle(), actual.getTitle());
        Assertions.assertEquals(expected.getIsbn(), actual.getIsbn());
        Assertions.assertEquals(expected.getPrice(), actual.getPrice());
        Assertions.assertEquals(expected.getDescription(), actual.getDescription());
        Assertions.assertEquals(expected.getCoverImage(), actual.getCoverImage());

        List<Long> expectedCategoryIds = List.of(1L, 2L);
        List<Long> actualCategoryIds = actual.getCategories().stream()
                .map(CategoryDto::getId)
                .sorted()
                .toList();

        Assertions.assertEquals(expectedCategoryIds, actualCategoryIds);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Delete Book by id successfully")
    void deleteBookById_ValidId_success() throws Exception {
        // Given
        CategoryDto cat1 = new CategoryDto();
        cat1.setId(1L);
        cat1.setName("Fiction");

        CategoryDto cat2 = new CategoryDto();
        cat2.setId(2L);
        cat2.setName("Fantasy");

        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setAuthor("Sunless");
        requestDto.setTitle("Lost From Light");
        requestDto.setIsbn("TestISBN");
        requestDto.setPrice(new BigDecimal("199.99"));
        requestDto.setDescription("A mysterious tale of light and shadow.");
        requestDto.setCoverImage("cover-image-url.jpg");
        requestDto.setCategoryIds(List.of(1L, 2L));

        BookDto expected = new BookDto();
        expected.setAuthor(requestDto.getAuthor());
        expected.setTitle(requestDto.getTitle());
        expected.setIsbn(requestDto.getIsbn());
        expected.setPrice(requestDto.getPrice());
        expected.setDescription(requestDto.getDescription());
        expected.setCoverImage(requestDto.getCoverImage());
        expected.setCategories(List.of(cat1, cat2));

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        System.out.println(jsonRequest);

        MvcResult result = mockMvc.perform(post("/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        BookDto createdBook = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookDto.class
        );

        // When
        mockMvc.perform(delete("/books/{id}", createdBook.getId()))
                .andExpect(status().isNoContent());
    }

}