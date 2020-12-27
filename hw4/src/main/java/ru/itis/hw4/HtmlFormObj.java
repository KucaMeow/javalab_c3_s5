package ru.itis.hw4;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HtmlFormObj {

    public HtmlFormObj(HtmlForm form) {
        method = form.method();
        action = form.action();
    }

    private String method;
    private String action;
}
