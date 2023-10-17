package mobral.talhaoManagerAPI.controller;

import lombok.AllArgsConstructor;
import mobral.talhaoManagerAPI.domain.Field;
import mobral.talhaoManagerAPI.dto.FieldPostRequestBody;
import mobral.talhaoManagerAPI.service.FieldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fields")
@AllArgsConstructor
public class FieldController {

    private final FieldService fieldService;

    @PostMapping
    public ResponseEntity<Field> save(@RequestBody FieldPostRequestBody fieldPostRequestBody) {
        return new ResponseEntity<>(fieldService.save(fieldPostRequestBody), HttpStatus.CREATED);
    }
}
