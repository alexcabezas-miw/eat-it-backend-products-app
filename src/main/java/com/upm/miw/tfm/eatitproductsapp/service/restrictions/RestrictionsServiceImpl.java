package com.upm.miw.tfm.eatitproductsapp.service.restrictions;

import com.upm.miw.tfm.eatitproductsapp.exception.IngredientDoesNotExistValidationException;
import com.upm.miw.tfm.eatitproductsapp.exception.RestrictionAlreadyExistsValidationException;
import com.upm.miw.tfm.eatitproductsapp.repository.IngredientsRepository;
import com.upm.miw.tfm.eatitproductsapp.repository.RestrictionsRepository;
import com.upm.miw.tfm.eatitproductsapp.service.mapper.RestrictionsMapper;
import com.upm.miw.tfm.eatitproductsapp.service.model.Ingredient;
import com.upm.miw.tfm.eatitproductsapp.service.model.Restriction;
import com.upm.miw.tfm.eatitproductsapp.web.dto.restriction.RestrictionCreationDTO;
import com.upm.miw.tfm.eatitproductsapp.web.dto.restriction.RestrictionDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestrictionsServiceImpl implements RestrictionsService {

    private final RestrictionsRepository restrictionsRepository;
    private final IngredientsRepository ingredientsRepository;
    private final RestrictionsMapper restrictionsMapper;

    public RestrictionsServiceImpl(RestrictionsRepository restrictionsRepository,
                                   IngredientsRepository ingredientsRepository,
                                   RestrictionsMapper restrictionsMapper) {
        this.restrictionsRepository = restrictionsRepository;
        this.ingredientsRepository = ingredientsRepository;
        this.restrictionsMapper = restrictionsMapper;
    }

    @Override
    public void createRestriction(RestrictionCreationDTO creationDTO) {
        List<Ingredient> mappedIngredients = creationDTO.getIngredients().stream()
                .map(ingredient -> this.ingredientsRepository.findByName(ingredient)
                        .orElseThrow(() -> new IngredientDoesNotExistValidationException(ingredient)))
                .collect(Collectors.toList());
        Optional<Restriction> restrictionFromDb = this.restrictionsRepository.findByName(creationDTO.getName());
        if(restrictionFromDb.isPresent()) {
            throw new RestrictionAlreadyExistsValidationException(creationDTO.getName());
        }
        Restriction restriction = this.restrictionsMapper.fromCreationDTO(creationDTO);
        restriction.setIngredients(mappedIngredients);
        this.restrictionsRepository.save(restriction);
    }

    @Override
    public Optional<RestrictionDTO> getRestrictionByName(String name) {
        return this.restrictionsRepository.findByName(name)
                .map(this.restrictionsMapper::toRestrictionDTO);
    }

    @Override
    public Collection<RestrictionDTO> getAllRestrictions() {
        return this.restrictionsRepository.findAll().stream()
                .map(this.restrictionsMapper::toRestrictionDTO)
                .collect(Collectors.toList());
    }
}
