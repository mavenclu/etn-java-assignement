package com.etnetera.hr.dto;

import com.etnetera.hr.data.FanaticIrrationalAdmirationLevel;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "JavascriptFramework")
public class JSFrameworkRequestDto {
    @Schema(description = "name of the framework")
    @NotNull(message = "NotEmpty")
    @Size(max = 30, message = "Size")
    private String name;
    @Schema(name = "version", description = "available versions of the framework")
    private List<String> version = new ArrayList<>();
    @Schema(name = "depricationDate", description = "deprication date, in case the framework is depricated")
    private LocalDate deprecationDate;
    @Schema(name = "hyepLevel", description = "current level of admiration of the framework")
    private FanaticIrrationalAdmirationLevel hypeLevel;

    public JSFrameworkRequestDto(String name) {
        this.name = name;
    }


}
