package com.etnetera.hr.service;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.dto.JSFrameworkRequestDto;
import com.etnetera.hr.dto.JSFrameworkResponseDto;
import com.etnetera.hr.mapper.JavaScriptFrameworkMapper;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JavaScriptFrameworkService {
    private final JavaScriptFrameworkRepository frameworkRepository;
    private final JavaScriptFrameworkMapper frameworkMapper;

    public JavaScriptFrameworkService(JavaScriptFrameworkRepository frameworkRepository, JavaScriptFrameworkMapper frameworkMapper) {
        this.frameworkRepository = frameworkRepository;
        this.frameworkMapper = frameworkMapper;
    }

    public Iterable<JavaScriptFramework> findAll() {
        return frameworkRepository.findAllByArchivedFalse();
    }


    public JSFrameworkResponseDto addNew(JSFrameworkRequestDto newFramework) {
        JavaScriptFramework framework = frameworkMapper.jSFrameworkDtoToJavaScriptFramework(newFramework);
        JavaScriptFramework saved = frameworkRepository.save(framework);
        return frameworkMapper.mapToResponseDto(saved);
    }


    public JSFrameworkResponseDto editFramework(Long id, JSFrameworkRequestDto dto) {
        JavaScriptFramework framework = frameworkRepository.findById(id).orElseThrow();
        frameworkMapper.updateJavaScriptFrameworkFromDto(dto, framework);
        frameworkRepository.save(framework);
        return frameworkMapper.mapToResponseDto(framework);
    }

    public void delete(Long id) {
        JavaScriptFramework framework = frameworkRepository.findById(id).orElseThrow();
        framework.setArchived(true);
        frameworkRepository.save(framework);
    }

    public Iterable<JSFrameworkResponseDto> searchForFramework(String name) {
        List<JSFrameworkResponseDto> found = new ArrayList<>();
        frameworkRepository.findAllByNameContaining(name).forEach(framework ->
                found.add(frameworkMapper.mapToResponseDto(framework))
        );
        return found;
    }

}
