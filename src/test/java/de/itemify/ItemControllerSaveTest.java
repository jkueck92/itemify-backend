package de.itemify;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.itemify.common.ItemCreateDto;
import de.itemify.common.ItemDto;
import de.itemify.common.ItemTypeDto;
import de.itemify.common.PersonDto;
import de.itemify.common.mapper.ItemMapper;
import de.itemify.service.ItemService;
import de.itemify.web.ItemController;
import de.itemify.web.ItemCreateWebRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Optional;
import java.util.UUID;

@ActiveProfiles("test")
@WebMvcTest(ItemController.class)
@Import(ItemMapper.class)
class ItemControllerSaveTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ItemService itemService;

    @Spy
    private ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);

    @Spy
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void test_01() throws Exception {
        ItemCreateWebRequest request = ItemCreateWebRequest.builder().build();
        request.setSerialNumber("ABC-123");
        request.setSize("M");
        request.setPersonId(1L);
        request.setItemTypeId(1L);

        ItemCreateDto itemCreateDto = new ItemCreateDto();
        itemCreateDto.setSerialNumber("ABC-123");
        itemCreateDto.setSize("M");
        itemCreateDto.setPersonId(1L);
        itemCreateDto.setItemTypeId(1L);

        ItemTypeDto itemTypeDto = new ItemTypeDto();
        itemTypeDto.setId(1L);
        itemTypeDto.setSymbolicName("SYMBOLIC_NAME");
        itemTypeDto.setDisplayName("DISPLAY_NAME");

        PersonDto personDto = new PersonDto();
        personDto.setId(1L);
        personDto.setUuid(UUID.randomUUID().toString());
        personDto.setFirstName("Max");
        personDto.setLastName("Mustermann");


        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setUuid(UUID.randomUUID().toString());
        itemDto.setSerialNumber("ABC-123");
        itemDto.setSize("M");
        itemDto.setItemType(itemTypeDto);
        itemDto.setPerson(personDto);


        Mockito.when(this.itemService.save(itemCreateDto)).thenReturn(Optional.of(itemDto));

        MockHttpServletResponse response = this.postWithBody("/api/v1/items", request);
        Assertions.assertEquals(HttpStatus.NOT_IMPLEMENTED.value(), response.getStatus());

    }

    private MockHttpServletResponse postWithoutBody(String uri) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri);
        return this.execute(requestBuilder);
    }

    private MockHttpServletResponse postWithBody(String uri, Object request) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(request));
        return this.execute(requestBuilder);
    }

    private MockHttpServletResponse execute(RequestBuilder requestBuilder) throws Exception {
        MvcResult mvcResult = this.mvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();
        return mvcResult.getResponse();
    }

}
