package de.itemify.common.mapper;

import de.itemify.common.ItemCreateDto;
import de.itemify.common.ItemDto;
import de.itemify.common.ItemTypeDto;
import de.itemify.common.PersonDto;
import de.itemify.database.model.ItemModel;
import de.itemify.database.model.ItemTypeModel;
import de.itemify.database.model.PersonModel;
import de.itemify.web.ItemCreateWebRequest;
import de.itemify.web.ItemTypeWebResponse;
import de.itemify.web.PersonWebResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {

    ItemMapper MAPPER = Mappers.getMapper(ItemMapper.class );

    ItemDto toItemDto(ItemModel itemModel);

    ItemTypeDto toItemTypeDto(ItemTypeModel itemTypeModel);

    PersonDto toPersonDto(PersonModel personModel);

    ItemCreateDto toItemCreateDto(ItemCreateWebRequest itemCreateWebRequest);

    ItemTypeWebResponse toItemTypeWebResponse(ItemTypeDto itemTypeDto);

    PersonWebResponse toPersonWebResponse(PersonDto personDto);

}
