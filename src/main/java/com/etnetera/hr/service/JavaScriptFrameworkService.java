package com.etnetera.hr.service;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.etnetera.hr.rest.Errors;
import com.etnetera.hr.rest.ValidationError;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JavaScriptFrameworkService {
    private final JavaScriptFrameworkRepository frameworkRepository;

    public JavaScriptFrameworkService(JavaScriptFrameworkRepository frameworkRepository) {
        this.frameworkRepository = frameworkRepository;
    }

    public Iterable<JavaScriptFramework> findAll() {
        return frameworkRepository.findAll();
    }


    public Errors save(JavaScriptFramework frameworkDto) {
        Errors errors = new Errors();
        errors.setErrors(new ArrayList<>());

        if (frameworkDto.getName() == null) {
            errors.getErrors().add(new ValidationError("name", "NotEmpty"));
        } else if (frameworkDto.getName().length() > 30) {
            errors.getErrors().add(new ValidationError("name", "Size"));
        } else {
            JavaScriptFramework newFramework = new JavaScriptFramework(frameworkDto.getName());
            frameworkRepository.save(newFramework);
        }
        return errors;
    }
}
