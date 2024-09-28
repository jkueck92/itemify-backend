package de.itemify.service;

import de.itemify.common.ItemCreateDto;
import de.itemify.common.ItemDto;
import de.itemify.common.mapper.ItemMapper;
import de.itemify.database.model.ItemModel;
import de.itemify.database.model.ItemTypeModel;
import de.itemify.database.model.PersonModel;
import de.itemify.database.repository.ItemRepository;
import de.itemify.database.repository.ItemTypeRepository;
import de.itemify.database.repository.PersonRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final PersonRepository personRepository;

    private final ItemTypeRepository itemTypeRepository;

    private final ItemMapper itemMapper;

    private final Validator validator;

    public Optional<ItemDto> save(ItemCreateDto createDto) {

        Set<ConstraintViolation<ItemCreateDto>> violations = this.validator.validate(createDto);
        if (violations.isEmpty()) {
            Optional<ItemTypeModel> optionalItemType = this.itemTypeRepository.findById(createDto.getItemTypeId());
            if (optionalItemType.isPresent()) {

                ItemModel itemModel = new ItemModel();
                itemModel.setUuid(UUID.randomUUID().toString());
                itemModel.setSerialNumber(createDto.getSerialNumber());
                itemModel.setSize(createDto.getSize());
                itemModel.setItemType(optionalItemType.get());

                if (createDto.getPersonId() != null) {
                    Optional<PersonModel> optionalPerson = this.personRepository.findById(createDto.getPersonId());
                    optionalPerson.ifPresent(itemModel::setPerson);
                }

                ItemModel stored = this.itemRepository.save(itemModel);

                return Optional.of(this.itemMapper.toItemDto(stored));
            }
        }
        return Optional.empty();
    }


}
