package com.etnetera.hr;

import com.etnetera.hr.data.FanaticIrrationalAdmirationLevel;
import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.dto.JSFrameworkRequestDto;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.etnetera.hr.service.JavaScriptFrameworkService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.StreamSupport;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Class used for Spring Boot/MVC based tests.
 *
 * @author Etnetera
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JavaScriptFrameworkTests {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private JavaScriptFrameworkRepository repository;
    @Autowired
    private JavaScriptFrameworkService service;

    private void prepareData() throws Exception {
        JavaScriptFramework react = new JavaScriptFramework("ReactJS");
        JavaScriptFramework vue = new JavaScriptFramework("Vue.js");

        repository.save(react);
        repository.save(vue);
    }

    @Test
    public void frameworksTest() throws Exception {
        prepareData();

        mockMvc.perform(get("/frameworks")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is("ReactJS")))
				.andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].name", is("Vue.js")))
        ;
    }

    @Test
    public void addFrameworkInvalid() throws Exception {
        JavaScriptFramework framework = new JavaScriptFramework();
        mockMvc.perform(post("/frameworks").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is("NotEmpty")));

        framework.setName("verylongnameofthejavascriptframeworkjavaisthebest");
        mockMvc.perform(post("/frameworks").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is("Size")));

    }

    @Test
    public void addFramework_whenValdiName_thenStatusCreated() throws Exception {
        String frameWorkName = RandomStringUtils.randomAlphabetic(6);
        JSFrameworkRequestDto framework = new JSFrameworkRequestDto(frameWorkName);
        mockMvc.perform(post("/frameworks").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
                .andExpect(status().isCreated());

    }


    @Test
    public void addFramework_whenValdiName_thenNumberOfFrameworksIncreases() throws Exception {
        long numbOfFrameworks = repository.count();
        JavaScriptFramework framework = new JavaScriptFramework(RandomStringUtils.randomAlphabetic(7));
        mockMvc.perform(post("/frameworks").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
                .andExpect(status().isCreated());
        Assert.assertEquals(numbOfFrameworks + 1, repository.count());

    }

    @Test
    public void editFramework_whenInvalidID_thenBadRequest() throws Exception {
        String frameWorkName = RandomStringUtils.randomAlphabetic(6);
        JSFrameworkRequestDto framework = new JSFrameworkRequestDto(frameWorkName);
        mockMvc.perform(put(
                        "/frameworks/1001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(framework)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].field", is("id")))
                .andExpect(jsonPath("$.errors[0].message", is("NotFound")));
    }

    @Test
    public void editFramework_whenInvalidName_thenBadRequest() throws Exception {
        JavaScriptFramework oldFramework = new JavaScriptFramework(RandomStringUtils.randomAlphabetic(7));
        repository.save(oldFramework);
        String id = oldFramework.getId().toString();
        String frameWorkName = RandomStringUtils.randomAlphabetic(31);
        JSFrameworkRequestDto framework = new JSFrameworkRequestDto(frameWorkName);
        mockMvc.perform(put(
                        "/frameworks/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(framework)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is("Size")));
    }

    @Test
    public void editFramework_whenValidNameAndID_thenStatusOk() throws Exception {
        JavaScriptFramework oldFramework = new JavaScriptFramework(RandomStringUtils.randomAlphabetic(7));
        repository.save(oldFramework);
        String id = oldFramework.getId().toString();

        String frameWorkName = RandomStringUtils.randomAlphabetic(5);
        JSFrameworkRequestDto framework = new JSFrameworkRequestDto(frameWorkName);
        framework.setHypeLevel(FanaticIrrationalAdmirationLevel.ADORE);

        mockMvc.perform(put(
                        "/frameworks/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(framework)))
                .andExpect(status().isOk());
        oldFramework = repository.findById(oldFramework.getId()).orElseThrow();
        Assert.assertEquals("check name was updated", framework.getName(), oldFramework.getName());
        Assert.assertEquals("check admiration level was updated", FanaticIrrationalAdmirationLevel.ADORE, oldFramework.getHypeLevel());
    }


    @Test
    public void deleteFramework_whenValidId_thenStatusOk() throws Exception {
        prepareData();
        long numbOfFrameworks = StreamSupport.stream(repository.findAllByArchivedFalse().spliterator(), false).count();
        Long id = repository.findAll().iterator().next().getId();

        mockMvc.perform(delete("/frameworks/" + id.toString()))
                .andExpect(status().isOk());
        long newNumbOfFrameworks = StreamSupport.stream(repository.findAllByArchivedFalse().spliterator(), false).count();
        Assert.assertEquals(numbOfFrameworks - 1, newNumbOfFrameworks);

    }

    @Test
    public void deleteFramework_whenInvalidId_thenBadRequest() throws Exception {
        mockMvc.perform(delete("/frameworks/123"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].field", is("id")))
                .andExpect(jsonPath("$.errors[0].message", is("NotFound")));
    }

    @Test
    public void search_whenExistentName_thenStatusOk() throws Exception {
        prepareData();

        mockMvc.perform(get("/search?name=Vue.js"))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    public void search_whenExistentPartitialName_thenStatusOk() throws Exception {
        prepareData();

        mockMvc.perform(get("/search?name=eact"))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    public void search_whenNonExistent_thenSizeZero() throws Exception {

        mockMvc.perform(get("/search?name=React"))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)));

    }

}
