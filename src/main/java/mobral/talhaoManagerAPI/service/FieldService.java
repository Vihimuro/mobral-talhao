package mobral.talhaoManagerAPI.service;

import lombok.RequiredArgsConstructor;
import mobral.talhaoManagerAPI.domain.Field;
import mobral.talhaoManagerAPI.dto.FieldPostResquestBody;
import mobral.talhaoManagerAPI.repository.FieldRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FieldService {

    private final FieldRepository fieldRepository;

    @Transactional
    public Field save(FieldPostResquestBody fieldPostRequestBody){
        return fieldRepository.saveField(fieldPostRequestBody.getCdIdFazenda(), fieldPostRequestBody.getGeom());
    }
}
