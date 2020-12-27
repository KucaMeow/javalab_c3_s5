package ru.itis.hm2.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassportDetails {

    @ApiModelProperty(
            value = "Person's name from passport",
            example = "Oleg",
            required = true
    )
    private String name;

    @ApiModelProperty(
            value = "Person's surname from passport",
            example = "Olegovich",
            required = true
    )
    private String surname;

    @ApiModelProperty(
            value = "Code of person's passport",
            example = "0000 000000",
            required = true
    )
    private String pass_code;
}
