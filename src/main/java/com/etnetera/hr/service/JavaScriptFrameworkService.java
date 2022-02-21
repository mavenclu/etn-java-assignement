package com.etnetera.hr.service;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.dto.JSFrameworkRequestDto;
import com.etnetera.hr.dto.JSFrameworkResponseDto;
import com.etnetera.hr.mapper.JavaScriptFrameworkMapper;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.etnetera.hr.rest.Errors;
import com.etnetera.hr.rest.ValidationError;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JavaScriptFrameworkService {
    private final JavaScriptFrameworkRepository frameworkRepository;
    private final JavaScriptFrameworkMapper frameworkMapper;
    private final ValidationService validationService;

    public JavaScriptFrameworkService(JavaScriptFrameworkRepository frameworkRepository, JavaScriptFrameworkMapper frameworkMapper, ValidationService validationService) {
        this.frameworkRepository = frameworkRepository;
        this.frameworkMapper = frameworkMapper;
        this.validationService = validationService;
    }

    public Iterable<JavaScriptFramework> findAll() {
        return frameworkRepository.findAllByArchivedFalse();
    }


    public JavaScriptFramework addNew(JSFrameworkRequestDto newFramework) {
        JavaScriptFramework framework = frameworkMapper.jSFrameworkDtoToJavaScriptFramework(newFramework);
        return frameworkRepository.save(framework);
    }

    public Errors addNewFramework(JSFrameworkRequestDto dto) {
        Errors errors = new Errors();
        List<ValidationError> validationErrors;
        if (!validationService.validateName(dto).isEmpty()) {
            validationErrors = validationService.validateName(dto);
        } else {
            JavaScriptFramework framework = frameworkMapper.jSFrameworkDtoToJavaScriptFramework(dto);
            frameworkRepository.save(framework);
            validationErrors = new ArrayList<>();
        }
        errors.setErrors(validationErrors);
        return errors;
    }

    public Errors editFramework(Long id, JSFrameworkRequestDto dto) {
        Errors errors = new Errors();
        List<ValidationError> validationErrors;

        if (!validationService.validateExistence(id).isEmpty()) {
            validationErrors = validationService.validateExistence(id);
        } else if (!validationService.validateName(dto).isEmpty()) {
            validationErrors = validationService.validateName(dto);
        } else {
            JavaScriptFramework framework = frameworkRepository.findById(id).orElseThrow();
            frameworkMapper.updateJavaScriptFrameworkFromDto(dto, framework);
            frameworkRepository.save(framework);
            validationErrors = new ArrayList<>();
        }
        errors.setErrors(validationErrors);
        return errors;
    }

    public Errors delete(Long id) {
        Errors errors = new Errors();
        List<ValidationError> validationErrors;

        if (!validationService.validateExistence(id).isEmpty()) {
            validationErrors = validationService.validateExistence(id);
        } else {
            JavaScriptFramework framework = frameworkRepository.findById(id).orElseThrow();
            framework.setArchived(true);
            frameworkRepository.save(framework);
            validationErrors = new ArrayList<>();
        }
        errors.setErrors(validationErrors);
        return errors;
    }

    public Iterable<JSFrameworkResponseDto> searchForFramework(String name) {
        List<JSFrameworkResponseDto> found = new ArrayList<>();
        frameworkRepository.findAllByNameContaining(name).forEach(framework ->
                found.add(frameworkMapper.mapToResponseDto(framework))
        );
        return found;
    }

}
