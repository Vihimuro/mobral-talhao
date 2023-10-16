package mobral.talhaoManagerAPI.controller;

import lombok.AllArgsConstructor;
import mobral.talhaoManagerAPI.service.FieldService;
import mobral.talhaoManagerAPI.domain.Field;
import mobral.talhaoManagerAPI.dto.FieldPostResquestBody;
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
    public ResponseEntity<Field> saveField(@RequestBody FieldPostResquestBody fieldPostResquestBody){
        return new ResponseEntity<>(fieldService.save(fieldPostResquestBody), HttpStatus.CREATED);
    }
}
