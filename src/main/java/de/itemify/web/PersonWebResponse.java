package de.itemify.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonWebResponse {

    private long id;

    private String uuid;

    private String firstName;

    private String lastName;

}
