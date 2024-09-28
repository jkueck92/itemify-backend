package de.itemify.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemWebResponse {

    private long id;

    private String uuid;

    private String serialNumber;

    private String size;

    private ItemTypeWebResponse itemType;

    private PersonWebResponse person;

}
