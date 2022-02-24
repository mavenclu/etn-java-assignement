package com.etnetera.hr.service;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.dto.JSFrameworkRequestDto;
import com.etnetera.hr.dto.JSFrameworkResponseDto;
import com.etnetera.hr.mapper.JavaScriptFrameworkMapper;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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


    /**
     * Scheduled task to delte JavaScriptFrameworks that were archived before 5 seconds, time set for test purposes only
     * fixedDelay set to 500 for test purposes only. for real scenario should be changed.
     */
    @Scheduled(fixedDelay = 10000)
    public void deleteArchivedJSFrameworks() {
        LocalDateTime marginDateTime = LocalDateTime.now().minusSeconds(5);
        frameworkRepository.deleteAll(frameworkRepository.findAllByArchivedTrueAndModifiedBefore(marginDateTime));
    }

}
