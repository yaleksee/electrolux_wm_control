package com.electrolux.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Display {
    private String displayInfo;

    public Display(String displayInfo) {
        this.displayInfo = displayInfo;
    }
}
