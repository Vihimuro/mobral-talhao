package mobral.talhaoManagerAPI.controller;

import lombok.AllArgsConstructor;
import mobral.talhaoManagerAPI.dto.FeatureCollectionJson;
import mobral.talhaoManagerAPI.dto.FieldPostRequestBody;
import mobral.talhaoManagerAPI.service.FieldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("fields")
@AllArgsConstructor
public class FieldController {

    private final FieldService fieldService;

    @PostMapping
    public ResponseEntity<FeatureCollectionJson> saveAll(@RequestBody FieldPostRequestBody fieldPostRequestBody) {
        return new ResponseEntity<>(fieldService.saveAll(fieldPostRequestBody), HttpStatus.CREATED);
    }

    @GetMapping("/farm/{cdIdFarm}")
    public ResponseEntity<FeatureCollectionJson> findAllByCdIdFarm(@PathVariable long cdIdFarm) {
        FeatureCollectionJson featureCollectionJson = fieldService.findAllByCdIdFarm(cdIdFarm);
        if (featureCollectionJson.getFeatures().isEmpty()){
            return new ResponseEntity<>(featureCollectionJson, HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(featureCollectionJson);
    }
}
