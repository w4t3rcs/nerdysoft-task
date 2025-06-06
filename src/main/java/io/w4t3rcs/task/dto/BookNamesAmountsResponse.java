package io.w4t3rcs.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor @NoArgsConstructor
public class BookNamesAmountsResponse implements Serializable {
    private String name;
    private Integer amount;
}
