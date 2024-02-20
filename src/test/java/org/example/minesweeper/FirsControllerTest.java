package org.example.minesweeper;

import org.example.minesweeper.controllers.FirsController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.BindException;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FirsController.class)
public class FirsControllerTest {

    @Autowired
    private MockMvc mvc;
//    @Test
//    public void whenWidthIsValid() throws Exception {
//        mvc.perform(MockMvcRequestBuilders
//                        .post("/api/new")
//                        .content(new NewGameRequest(10, 10, 10).toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//

//    @Test
//    public void whenWidthIsInvalid_thenThrowBindException() throws Exception {
////        String requestBody = new NewGameRequest(1, 10, 10).toString();
//        mvc.perform(MockMvcRequestBuilders
//                        .post("/api/new")
//                        .param("width", "1")
//                        .param("height", "10")
//                        .param("mines", "10")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is4xxClientError())
//                .andExpect(MockMvcResultMatchers.content().string("error: ширина поля должна быть не менее 2 и не более 30"));
//    }
//
    @Test
    public void whenWidthIsInvalid_thenThrowBindException2() throws Exception {
        NewGameRequest newGameRequest = new NewGameRequest(1, 10, 10);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(newGameRequest);
        System.out.println(requestBody);
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/new")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
.andExpect(MockMvcResultMatchers.content().string("error: ширина поля должна быть не менее 2 и не более 30"));
    }



//    @Test
//    public void whenHeightIsInvalid_thenThrowBindException() throws Exception {
//        mvc.perform(post("/api/new")
//                        .param("width", "10") // Корректное значение ширины
//                        .param("height", "1")) // Используем некорректное значение высоты
//                .andExpect(status().isOk()) // Проверяем, что статус ответа - OK
//                .andExpect(content().string("error: высота поля должна быть не менее 2 и не более 30"));
//    }
}



