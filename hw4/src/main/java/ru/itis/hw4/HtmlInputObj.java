package ru.itis.hw4;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HtmlInputObj {

    public HtmlInputObj (HtmlInput input) {
        type = input.type();
        name = input.name();
        placeholder = input.placeholder();
    }

    private String type;
    private String name;
    private String placeholder;
}
