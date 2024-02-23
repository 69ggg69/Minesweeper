package org.example.minesweeper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.util.Assert;

import java.io.IOException;

import static org.apache.coyote.http11.Constants.a;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class MyJsonTest {
//    @Autowired
//            private JacksonTester<NewGameRequest> json;
//
//    @Test
//        void testSerialize() throws Exception {
//            NewGameRequest details = new NewGameRequest(20,10, 199);
//            json.write(details);
//            assertThat(json.write(details)).extractingJsonPathNumberValue("@.width").isEqualTo(20);
//            assertThat(json.write(details)).extractingJsonPathNumberValue("@.height").isEqualTo(10);
//            assertThat(json.write(details)).extractingJsonPathNumberValue("@.mines").isEqualTo(199);
//    }
}
