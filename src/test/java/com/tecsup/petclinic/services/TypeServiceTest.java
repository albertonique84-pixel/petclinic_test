package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class TypeServiceTest {

    @Autowired
    private TypeService typeService;

    /**
     * Busqueda de tipo de mascota por ID
     */
    @Test
    public void testFindTypeById() {

        final String NAME_EXPECTED = "cat";
        Integer ID = 1;

        TypeDTO type = null;

        try {
            type = this.typeService.findById(ID);
        } catch (TypeNotFoundException e) {
            fail(e.getMessage());
        }
        log.info("TYPE FOUND : " + type);

        assertEquals(NAME_EXPECTED, type.getName());
    }

    /**
     * Busqueda de tipo de mascota por nombre
     */
    @Test
    public void testFindTypeByName() {

        String FIND_NAME = "dog";
        int SIZE_EXPECTED = 1;

        List<TypeDTO> types = this.typeService.findByName(FIND_NAME);

        assertEquals(SIZE_EXPECTED, types.size());
    }

    /**
     * Busqueda de tipos de mascota por nivel de cuidado
     */
    @Test
    public void testFindTypeByCareLevel() {

        String CARE_LEVEL = "high";
        int SIZE_EXPECTED = 2;   // lizard y snake

        List<TypeDTO> types = this.typeService.findByCareLevel(CARE_LEVEL);

        assertEquals(SIZE_EXPECTED, types.size());
    }

    /**
     * Creacion de tipo de mascota
     */
    @Test
    public void testCreateType() {

        String NAME = "ferret";
        String DESCRIPTION = "Small carnivorous mammal";
        String SIZE_CATEGORY = "small";
        Integer AVERAGE_LIFESPAN = 7;
        String CARE_LEVEL = "medium";

        TypeDTO typeDTO = TypeDTO.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .active(true)
                .sizeCategory(SIZE_CATEGORY)
                .averageLifespan(AVERAGE_LIFESPAN)
                .careLevel(CARE_LEVEL)
                .build();

        TypeDTO newTypeDTO = this.typeService.create(typeDTO);

        log.info("TYPE CREATED : " + newTypeDTO);

        assertNotNull(newTypeDTO.getId());
        assertEquals(NAME, newTypeDTO.getName());
        assertEquals(DESCRIPTION, newTypeDTO.getDescription());
        assertEquals(SIZE_CATEGORY, newTypeDTO.getSizeCategory());
        assertEquals(AVERAGE_LIFESPAN, newTypeDTO.getAverageLifespan());
        assertEquals(CARE_LEVEL, newTypeDTO.getCareLevel());
    }

    /**
     * Actualizacion de tipo de mascota
     */
    @Test
    public void testUpdateType() {

        String NAME = "fish";
        String DESCRIPTION = "Aquarium fish";

        String UP_NAME = "goldfish";
        String UP_DESCRIPTION = "Freshwater aquarium fish";
        Integer UP_AVERAGE_LIFESPAN = 10;

        TypeDTO typeDTO = TypeDTO.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .active(true)
                .sizeCategory("small")
                .averageLifespan(5)
                .careLevel("low")
                .build();

        // ------------ Create ---------------

        log.info(">" + typeDTO);
        TypeDTO typeDTOCreated = this.typeService.create(typeDTO);
        log.info(">>" + typeDTOCreated);

        // ------------ Update ---------------

        // Prepare data for update
        typeDTOCreated.setName(UP_NAME);
        typeDTOCreated.setDescription(UP_DESCRIPTION);
        typeDTOCreated.setAverageLifespan(UP_AVERAGE_LIFESPAN);

        // Execute update
        TypeDTO upgradeTypeDTO = this.typeService.update(typeDTOCreated);
        log.info(">>>>" + upgradeTypeDTO);

        //            EXPECTED        ACTUAL
        assertEquals(typeDTOCreated.getId(), upgradeTypeDTO.getId());
        assertEquals(UP_NAME, upgradeTypeDTO.getName());
        assertEquals(UP_DESCRIPTION, upgradeTypeDTO.getDescription());
        assertEquals(UP_AVERAGE_LIFESPAN, upgradeTypeDTO.getAverageLifespan());
    }

    /**
     * Eliminacion de tipo de mascota
     */
    @Test
    public void testDeleteType() {

        // ------------ Create ---------------

        TypeDTO typeDTO = TypeDTO.builder()
                .name("parrot")
                .description("Tropical bird")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(40)
                .careLevel("medium")
                .build();

        TypeDTO newTypeDTO = this.typeService.create(typeDTO);
        log.info("" + newTypeDTO);

        // ------------ Delete ---------------

        try {
            this.typeService.delete(newTypeDTO.getId());
        } catch (TypeNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validation ---------------

        try {
            this.typeService.findById(newTypeDTO.getId());
            assertTrue(false);
        } catch (TypeNotFoundException e) {
            assertTrue(true);
        }
    }
}
