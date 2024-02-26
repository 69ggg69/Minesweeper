package org.example.minesweeper;

import org.example.minesweeper.controllers.FirsController;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(FirsController.class)
public class FirsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DefaultGameInfoService gameInfoService;

    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }



//    @Test
//    public void whenWidthIsInvalid_thenThrowBindException2() throws Exception {
//        GameInfoData gameInfoData = new GameInfoData();
//        gameInfoData.setWidth(10);
//        gameInfoData.setHeight(10);
//        gameInfoData.setMines_count(5);
//        gameInfoData.setCompleted(false);
//        // Устанавливаем ожидаемое поведение сервиса
//        Mockito.doNothing().when(gameInfoService).saveGameInfo(Mockito.any(GameInfoData.class));
//        System.out.println(objectMapper.writeValueAsString(gameInfoData));
//        // Отправляем POST-запрос с данными объекта GameInfoData и проверяем, что возвращается статус 201 CREATED
//        mvc.perform(MockMvcRequestBuilders.post("/api/new")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(gameInfoData)))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
//        mvc.perform(MockMvcRequestBuilders.post("/api")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

//        NewGameRequest newGameRequest = new NewGameRequest(1, 10, 10);
//        GameInfoResponse gameInfoResponse = new GameInfoResponse();
//        gameInfoResponse.setWidth(newGameRequest);
//        gameInfoResponse.setHeight(newGameRequest);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String requestBody = objectMapper.writeValueAsString(gameInfoResponse);
//        System.out.println(requestBody);
//        System.out.println(requestBody);
//        mvc.perform(MockMvcRequestBuilders
//                        .post("/api")
//                        .content(requestBody)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is4xxClientError())
//.andExpect(MockMvcResultMatchers.content().string("error: ширина поля должна быть не менее 2 и не более 30"));
//    }
//    @Test
//    public void whenHeightIsInvalid_thenThrowBindException2() throws Exception {
//        NewGameRequest newGameRequest = new NewGameRequest(10, 1, 10);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String requestBody = objectMapper.writeValueAsString(newGameRequest);
//        System.out.println(requestBody);
//        mvc.perform(MockMvcRequestBuilders
//                        .post("/api")
//                        .content(requestBody)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is4xxClientError())
//                .andExpect(MockMvcResultMatchers.content().string("error: высота поля должна быть не менее 2 и не более 30"));
//    }
//    @Test
//    public void whenMinesIsInvalid_thenThrowBindException2() throws Exception {
//        NewGameRequest newGameRequest = new NewGameRequest(10, 10, 100);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String requestBody = objectMapper.writeValueAsString(newGameRequest);
//        System.out.println(requestBody);
//        mvc.perform(MockMvcRequestBuilders
//                        .post("/api")
//                        .content(requestBody)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is4xxClientError())
//                .andExpect(MockMvcResultMatchers.content().string("error: количество мин должно быть не менее 1 и не более 99"));
//    }





