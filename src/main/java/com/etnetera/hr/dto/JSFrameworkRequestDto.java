package com.etnetera.hr.dto;

import com.etnetera.hr.data.FanaticIrrationalAdmirationLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class JSFrameworkRequestDto {

    @NotNull(message = "NotEmpty")
    @Size(max = 30, message = "Size")
    private String name;

    private List<String> version = new ArrayList<>();

    private LocalDate deprecationDate;

    private FanaticIrrationalAdmirationLevel hypeLevel;

    public JSFrameworkRequestDto(String name) {
        this.name = name;
    }


}
