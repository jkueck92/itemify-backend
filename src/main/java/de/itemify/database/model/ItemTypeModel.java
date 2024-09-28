package de.itemify.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item_types")
public class ItemTypeModel extends BaseModel {

    @Column(unique = true, nullable = false, updatable = false)
    private String symbolicName;

    @Column(nullable = false)
    private String displayName;

}
