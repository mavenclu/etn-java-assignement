package com.etnetera.hr.dto;

import lombok.Getter;

@Getter
public class JSFrameworkResponseDto extends JSFrameworkRequestDto {

    private Long id;

    public JSFrameworkResponseDto(String name) {
        super(name);
    }
}
