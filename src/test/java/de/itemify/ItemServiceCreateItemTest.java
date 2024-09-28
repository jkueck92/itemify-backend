package de.itemify;

import de.itemify.common.ItemCreateDto;
import de.itemify.common.ItemDto;
import de.itemify.common.mapper.ItemMapper;
import de.itemify.database.model.ItemModel;
import de.itemify.database.model.ItemTypeModel;
import de.itemify.database.model.PersonModel;
import de.itemify.database.repository.ItemRepository;
import de.itemify.database.repository.ItemTypeRepository;
import de.itemify.database.repository.PersonRepository;
import de.itemify.service.ItemService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ItemServiceCreateItemTest {

    @InjectMocks
    private ItemService itemService;

    @Spy
    private ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);

    @Spy
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemTypeRepository itemTypeRepository;

    @Mock
    private PersonRepository personRepository;


    private ItemTypeModel itemType;

    private PersonModel person;

    private ItemModel item;

    @BeforeEach
    public void setUp() {
        this.itemType = new ItemTypeModel();
        this.itemType.setId(1L);
        this.itemType.setSymbolicName("SYMBOLIC_NAME_TEST");
        this.itemType.setDisplayName("DISPLAY_NAME_TEST");

        this.person = new PersonModel();
        this.person.setId(1L);
        this.person.setFirstName("Max");
        this.person.setLastName("Mustermann");
        this.person.setUuid(UUID.randomUUID().toString());

        this.item = new ItemModel();
        this.item.setId(1L);
        this.item.setSerialNumber("ABC-123");
        this.item.setSize("M");
        this.item.setUuid(UUID.randomUUID().toString());
        this.item.setPerson(this.person);
        this.item.setItemType(this.itemType);

    }

    @Test
    void everythingIsFilledAndSetCorrect() {
        Mockito.when(this.itemTypeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(this.itemType));
        Mockito.when(this.personRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(this.person));
        Mockito.when(this.itemRepository.save(Mockito.any(ItemModel.class))).thenReturn(this.item);
        Optional<ItemDto> itemDto = this.itemService.save(ItemCreateDto.builder().personId(this.item.getId()).itemTypeId(this.itemType.getId()).size(this.item.getSize()).serialNumber(this.item.getSerialNumber()).build());
        Assertions.assertTrue(itemDto.isPresent());
        Assertions.assertEquals(this.item.getId(), itemDto.get().getId());
        Assertions.assertEquals(this.item.getUuid(), itemDto.get().getUuid());
        Assertions.assertEquals(this.item.getSerialNumber(), itemDto.get().getSerialNumber());
        Assertions.assertEquals(this.item.getSize(), itemDto.get().getSize());
        Assertions.assertEquals(this.person.getId(), itemDto.get().getPerson().getId());
        Assertions.assertEquals(this.person.getUuid(), itemDto.get().getPerson().getUuid());
        Assertions.assertEquals(this.person.getFirstName(), itemDto.get().getPerson().getFirstName());
        Assertions.assertEquals(this.person.getLastName(), itemDto.get().getPerson().getLastName());
        Assertions.assertEquals(this.itemType.getId(), itemDto.get().getItemType().getId());
        Assertions.assertEquals(this.itemType.getSymbolicName(), itemDto.get().getItemType().getSymbolicName());
        Assertions.assertEquals(this.itemType.getDisplayName(), itemDto.get().getItemType().getDisplayName());
    }

    @Test
    void personIsNotSetInCreateDto() {
        this.item.setPerson(null);
        Mockito.when(this.itemTypeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(this.itemType));
        Mockito.when(this.itemRepository.save(Mockito.any(ItemModel.class))).thenReturn(this.item);
        Optional<ItemDto> itemDto = this.itemService.save(ItemCreateDto.builder().itemTypeId(this.itemType.getId()).size(this.item.getSize()).serialNumber(this.item.getSerialNumber()).build());
        Assertions.assertTrue(itemDto.isPresent());
        Assertions.assertEquals(this.item.getId(), itemDto.get().getId());
        Assertions.assertEquals(this.item.getUuid(), itemDto.get().getUuid());
        Assertions.assertEquals(this.item.getSerialNumber(), itemDto.get().getSerialNumber());
        Assertions.assertEquals(this.item.getSize(), itemDto.get().getSize());
        Assertions.assertNull(this.item.getPerson());
        Assertions.assertEquals(this.itemType.getId(), itemDto.get().getItemType().getId());
        Assertions.assertEquals(this.itemType.getSymbolicName(), itemDto.get().getItemType().getSymbolicName());
        Assertions.assertEquals(this.itemType.getDisplayName(), itemDto.get().getItemType().getDisplayName());
    }

    @Test
    void itemTypeIdNotFound() {
        Mockito.when(this.itemTypeRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Optional<ItemDto> itemDto = this.itemService.save(ItemCreateDto.builder().itemTypeId(this.itemType.getId()).size(this.item.getSize()).serialNumber(this.item.getSerialNumber()).build());
        Assertions.assertTrue(itemDto.isEmpty());
    }

    @Test
    void itemTypeIdIs0OrSmaller() {
        Optional<ItemDto> itemDto1 = this.itemService.save(ItemCreateDto.builder().itemTypeId(0).size(this.item.getSize()).serialNumber(this.item.getSerialNumber()).build());
        Assertions.assertTrue(itemDto1.isEmpty());

        Optional<ItemDto> itemDto2 = this.itemService.save(ItemCreateDto.builder().itemTypeId(-1).size(this.item.getSize()).serialNumber(this.item.getSerialNumber()).build());
        Assertions.assertTrue(itemDto2.isEmpty());
    }

    @Test
    void sizeEmpty() {
        Optional<ItemDto> itemDto1 = this.itemService.save(ItemCreateDto.builder().itemTypeId(this.itemType.getId()).size("").serialNumber(this.item.getSerialNumber()).build());
        Assertions.assertTrue(itemDto1.isEmpty());

        Optional<ItemDto> itemDto2 = this.itemService.save(ItemCreateDto.builder().itemTypeId(this.itemType.getId()).size(" ").serialNumber(this.item.getSerialNumber()).build());
        Assertions.assertTrue(itemDto2.isEmpty());

        Optional<ItemDto> itemDto3 = this.itemService.save(ItemCreateDto.builder().itemTypeId(this.itemType.getId()).size(null).serialNumber(this.item.getSerialNumber()).build());
        Assertions.assertTrue(itemDto3.isEmpty());
    }

}
