package com.etnetera.hr.controller;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.rest.Errors;
import com.etnetera.hr.service.JavaScriptFrameworkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple REST controller for accessing application logic.
 *
 * @author Etnetera
 */
@RestController
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
    public ResponseEntity<Errors> addNewFramework(@RequestBody JavaScriptFramework newFramework) {
        Errors errors = frameworkService.save(newFramework);
        if (!errors.getErrors().isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(errors);
        }
    }

}
