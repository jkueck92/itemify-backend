package de.itemify.common;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemCreateDto {

    private String serialNumber;

    @NotBlank
    private String size;

    @Min(value = 1)
    private Long personId;

    @Min(value = 1)
    private long itemTypeId;

}
