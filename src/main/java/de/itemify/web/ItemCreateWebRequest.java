package de.itemify.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemCreateWebRequest {

    private String serialNumber;

    private String size;

    private Long personId;

    private long itemTypeId;

}
