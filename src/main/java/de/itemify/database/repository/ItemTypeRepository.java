package de.itemify.database.repository;

import de.itemify.database.model.ItemTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemTypeRepository extends JpaRepository<ItemTypeModel, Long> {

}
