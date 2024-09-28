package de.itemify.web;

import de.itemify.common.ItemDto;
import de.itemify.common.mapper.ItemMapper;
import de.itemify.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;

    private final ItemMapper itemMapper;

    @PostMapping
    public ResponseEntity<ItemWebResponse> save(ItemCreateWebRequest request) {
        Optional<ItemDto> optionalItemDto = this.itemService.save(this.itemMapper.toItemCreateDto(request));
        if (optionalItemDto.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
