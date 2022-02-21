package com.etnetera.hr.service;

import com.etnetera.hr.dto.JSFrameworkRequestDto;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.etnetera.hr.rest.ValidationError;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidationService {

    private final JavaScriptFrameworkRepository repository;

    public ValidationService(JavaScriptFrameworkRepository repository) {
        this.repository = repository;
    }

    public List<ValidationError> validateName(JSFrameworkRequestDto framework) {
        List<ValidationError> errors = new ArrayList<>();
        if (framework.getName() == null) {
            errors.add(new ValidationError("name", "NotEmpty"));
        } else if (framework.getName().length() > 30) {
            errors.add(new ValidationError("name", "Size"));
        }
        return errors;
    }

    public List<ValidationError> validateExistence(Long id) {
        List<ValidationError> errors = new ArrayList<>();
        if (!repository.existsById(id)) {
            errors.add(new ValidationError("id", "NotFound"));
        }
        return errors;
    }
}
