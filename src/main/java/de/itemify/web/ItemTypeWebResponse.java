package de.itemify.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemTypeWebResponse {

    private long id;

    private String symbolicName;

    private String displayName;

}
