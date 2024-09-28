package de.itemify.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private long id;

    private String serialNumber;

    private String size;

    private String uuid;

    private PersonDto person;

    private ItemTypeDto itemType;

}
