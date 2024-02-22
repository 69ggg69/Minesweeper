package org.example.minesweeper;

import org.example.minesweeper.controllers.FirsController;
import org.example.minesweeper.dto.GameInfoData;
import org.example.minesweeper.services.GameInfoService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FirsController.class)
public class FirsControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GameInfoService gameInfoService;


    @Test
    public void whenWidthIsInvalid_thenThrowBindException2() throws Exception {
        NewGameRequest request = new NewGameRequest(10, 10, 10);
        GameInfoData expectedResponse = new GameInfoData();
        doNothing().when(gameInfoService).saveGameInfo(any());
        System.out.println(objectMapper.writeValueAsString(request));
        mvc.perform(MockMvcRequestBuilders.post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedResponse)));
    }


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





