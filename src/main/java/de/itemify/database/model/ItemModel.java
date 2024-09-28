package de.itemify.database.model;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class ItemModel extends BaseModel {

    @Column(unique = true, nullable = false, updatable = false)
    private String uuid;

    @Column(unique = true)
    private String serialNumber;

    @Column(nullable = false)
    private String size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_type_id")
    private ItemTypeModel itemType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private PersonModel person;

}
