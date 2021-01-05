package ru.javalab.rabbitmq1p.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassportDetails implements Serializable {

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

    @ApiModelProperty(
            value = "Age of person",
            example = "25",
            required = true
    )
    private int age;

    @ApiModelProperty(
            value = "Date of passport issue",
            example = "1970-01-01",
            required = true
    )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date issue;
}
