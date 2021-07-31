package ua.netcracker.netcrackerquizb.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.netcracker.netcrackerquizb.exception.UserDoesNotExistException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "spring", password = "secret")
    void authTest() throws Exception {
        this.mockMvc.perform(get("/test"))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser(username = "spring", password = "secret")
    void loginTest() throws Exception {
        this.mockMvc.perform(post("/login")
                .param("email", "mark2@gmail.com")
                .param("password", "testPassword1-")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    /*
    @Test
    @WithMockUser(username = "spring", password = "secret")
    void loginTestThrows() throws Exception {
        this.mockMvc.perform(post("/login")
                .param("email", "asdasdasd")
                .param("password", "asddasadasd")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

    }

     */

}