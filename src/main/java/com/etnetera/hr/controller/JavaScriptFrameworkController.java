package com.etnetera.hr.controller;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.dto.JSFrameworkRequestDto;
import com.etnetera.hr.dto.JSFrameworkResponseDto;
import com.etnetera.hr.service.JavaScriptFrameworkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Simple REST controller for accessing application logic.
 *
 * @author Etnetera
 */
@RestController
@Tag(name = "frameworks", description = "JS Framework API")
public class JavaScriptFrameworkController extends EtnRestController {

    private final JavaScriptFrameworkService frameworkService;

    public JavaScriptFrameworkController(JavaScriptFrameworkService frameworkService) {
        this.frameworkService = frameworkService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/frameworks")
    public Iterable<JavaScriptFramework> frameworks() {
        return frameworkService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/frameworks")
    public JSFrameworkResponseDto addNewFramework(@Valid @RequestBody JSFrameworkRequestDto frameworkDto) {
        return frameworkService.addNew(frameworkDto);
    }

    @Operation(
            summary = "Edit existent framework", description = "Update framework",
            tags = {"frameworks"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Framework was updated"),
            @ApiResponse(responseCode = "400", description = "Bad request")

    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/frameworks/{id}")
    public JSFrameworkResponseDto editFramework(@PathVariable Long id, @Valid @RequestBody JSFrameworkRequestDto framework) {

        return frameworkService.editFramework(id, framework);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/frameworks/{id}")
    public void deleteFramework(@PathVariable Long id) {
        frameworkService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public Iterable<JSFrameworkResponseDto> search(@RequestParam("name") String name) {
        return frameworkService.searchForFramework(name);
    }

}
