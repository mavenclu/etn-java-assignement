package com.etnetera.hr.mapper;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.dto.JSFrameworkRequestDto;
import com.etnetera.hr.dto.JSFrameworkResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface JavaScriptFrameworkMapper {

    @Mapping(target = "archived", ignore = true)
    @Mapping(target = "id", ignore = true)
    JavaScriptFramework jSFrameworkDtoToJavaScriptFramework(JSFrameworkRequestDto frameworkDto);


    @Mapping(target = "archived", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateJavaScriptFrameworkFromDto(JSFrameworkRequestDto dto, @MappingTarget JavaScriptFramework framework);

    JSFrameworkResponseDto mapToResponseDto(JavaScriptFramework framework);
}
