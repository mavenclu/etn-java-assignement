package com.etnetera.hr.dto;

import com.etnetera.hr.data.FanaticIrrationalAdmirationLevel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class JavaScriptFrameworkRequestDto {

    @NotNull(message = "NotEmpty")
    @Size(max = 30, message = "Size")
    private String name;

    private List<String> version = new ArrayList<>();

    private ZonedDateTime deprecationDate;

    private FanaticIrrationalAdmirationLevel hypeLevel;

    public JavaScriptFrameworkRequestDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
