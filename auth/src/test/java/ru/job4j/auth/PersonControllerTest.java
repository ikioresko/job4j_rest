package ru.job4j.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.auth.model.Person;
import ru.job4j.auth.service.PersonService;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AuthApplication.class)
@AutoConfigureMockMvc
class PersonControllerTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonService persons;

    @Test
    void findAll() throws Exception {
        this.mockMvc
                .perform(get("/person/"))
                .andDo(print())
                .andExpect(status().isOk());
        verify(persons).findAll();
    }

    @Test
    public void findById() throws Exception {
        Person person = new Person();
        person.setLogin("User");
        when(persons.findById(1)).thenReturn(Optional.of(person));
        String result = this.mockMvc
                .perform(get("/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(persons).findById(1);
        assertThat(mapper.readValue(result, Person.class), is(person));
    }

    @Test
    public void create() throws Exception {
        Person person = new Person();
        person.setLogin("User");
        person.setPassword("password");
        when(persons.save(person)).thenReturn(person);
        String result = this.mockMvc
                .perform(post("/person/")
                        .content(mapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(persons).save(person);
        assertThat(new ObjectMapper().readValue(result, Person.class), is(person));
    }

    @Test
    public void update() throws Exception {
        Person person = new Person();
        person.setLogin("User");
        person.setPassword("password");
        this.mockMvc
                .perform(put("/person/")
                        .content(mapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(persons).save(person);
    }

    @Test
    void deleteById() throws Exception {
        this.mockMvc
                .perform(delete("/person/1"))
                .andDo(print())
                .andExpect(status().isOk());
        verify(persons).deleteById(1);
    }
}