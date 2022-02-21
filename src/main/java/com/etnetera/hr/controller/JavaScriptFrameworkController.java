package com.etnetera.hr.controller;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.dto.JSFrameworkRequestDto;
import com.etnetera.hr.dto.JSFrameworkResponseDto;
import com.etnetera.hr.rest.Errors;
import com.etnetera.hr.service.JavaScriptFrameworkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping("/frameworks")
    public Iterable<JavaScriptFramework> frameworks() {
        return frameworkService.findAll();
    }

    @PostMapping("/frameworks")
    public ResponseEntity<Errors> addNewFramework(@RequestBody JSFrameworkRequestDto frameworkDto) {
        Errors errors = frameworkService.addNewFramework(frameworkDto);

        if (!errors.getErrors().isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }

    @Operation(
            summary = "Edit existent framework", description = "Update framework",
            tags = {"frameworks"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Framework was updated"),
            @ApiResponse(responseCode = "400", description = "Bad request")

    })
    @PutMapping("/frameworks/{id}")
    public ResponseEntity<Errors> editFramework(@PathVariable Long id, @RequestBody JSFrameworkRequestDto framework) {
        Errors errors = frameworkService.editFramework(id, framework);

        if (!errors.getErrors().isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        } else {
            return ResponseEntity.ok().body(errors);
        }
    }

    @DeleteMapping("/frameworks/{id}")
    public ResponseEntity<Errors> deleteFramework(@PathVariable Long id) {
        Errors errors = frameworkService.delete(id);

        if (!errors.getErrors().isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        } else {
            return ResponseEntity.ok().body(errors);
        }
    }

    @GetMapping("/search")
    public Iterable<JSFrameworkResponseDto> search(@RequestParam("name") String name) {
        return frameworkService.searchForFramework(name);
    }

}
