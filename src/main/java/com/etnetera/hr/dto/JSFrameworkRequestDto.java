package com.etnetera.hr.dto;

import com.etnetera.hr.data.FanaticIrrationalAdmirationLevel;
import com.etnetera.hr.data.Version;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(name = "JavascriptFramework")
public class JSFrameworkRequestDto {
    @Schema(description = "name of the framework")
    @Size(max = 30, message = "Size")
    @NotEmpty
    private String name;
    @Schema(name = "version", description = "available versions of the framework")
    private List<Version> versions = new ArrayList<>();
    @Schema(name = "hyepLevel", description = "current level of admiration of the framework")
    private FanaticIrrationalAdmirationLevel hypeLevel;

    public JSFrameworkRequestDto(String name) {
        this.name = name;
    }


}
