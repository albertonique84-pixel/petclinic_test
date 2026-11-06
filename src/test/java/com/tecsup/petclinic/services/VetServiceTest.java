package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class VetServiceTest {

    @Autowired
    private VetService vetService;

    /**
     * Busqueda de veterinario por ID
     */
    @Test
    public void testFindVetById() {

        final String FIRST_NAME_EXPECTED = "James";
        final String LAST_NAME_EXPECTED = "Carter";
        Integer ID = 1;

        VetDTO vet = null;

        try {
            vet = this.vetService.findById(ID);
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }
        log.info("VET FOUND : " + vet);

        assertEquals(FIRST_NAME_EXPECTED, vet.getFirstName());
        assertEquals(LAST_NAME_EXPECTED, vet.getLastName());
    }

    /**
     * Busqueda de veterinario por apellido
     */
    @Test
    public void testFindVetByLastName() {

        String FIND_LAST_NAME = "Leary";
        int SIZE_EXPECTED = 1;

        List<VetDTO> vets = this.vetService.findByLastName(FIND_LAST_NAME);

        assertEquals(SIZE_EXPECTED, vets.size());
    }

    /**
     * Creacion de veterinario
     */
    @Test
    public void testCreateVet() {

        String FIRST_NAME = "Juan";
        String LAST_NAME = "Sanchez";
        String EMAIL = "juan.sanchez@petclinic.com";
        String PHONE = "987654321";

        VetDTO vetDTO = VetDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .phone(PHONE)
                .active(true)
                .build();

        VetDTO newVetDTO = this.vetService.create(vetDTO);

        log.info("VET CREATED : " + newVetDTO);

        assertNotNull(newVetDTO.getId());
        assertEquals(FIRST_NAME, newVetDTO.getFirstName());
        assertEquals(LAST_NAME, newVetDTO.getLastName());
        assertEquals(EMAIL, newVetDTO.getEmail());
        assertEquals(PHONE, newVetDTO.getPhone());
    }

    /**
     * Actualizacion de veterinario
     */
    @Test
    public void testUpdateVet() {

        String FIRST_NAME = "Pedro";
        String LAST_NAME = "Rojas";
        String EMAIL = "pedro.rojas@petclinic.com";

        String UP_FIRST_NAME = "Pedro Luis";
        String UP_LAST_NAME = "Rojas Vega";
        String UP_EMAIL = "pedro.rojas.vega@petclinic.com";

        VetDTO vetDTO = VetDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .phone("911222333")
                .active(true)
                .build();

        // ------------ Create ---------------

        log.info(">" + vetDTO);
        VetDTO vetDTOCreated = this.vetService.create(vetDTO);
        log.info(">>" + vetDTOCreated);

        // ------------ Update ---------------

        // Prepare data for update
        vetDTOCreated.setFirstName(UP_FIRST_NAME);
        vetDTOCreated.setLastName(UP_LAST_NAME);
        vetDTOCreated.setEmail(UP_EMAIL);

        // Execute update
        VetDTO upgradeVetDTO = this.vetService.update(vetDTOCreated);
        log.info(">>>>" + upgradeVetDTO);

        //            EXPECTED        ACTUAL
        assertEquals(vetDTOCreated.getId(), upgradeVetDTO.getId());
        assertEquals(UP_FIRST_NAME, upgradeVetDTO.getFirstName());
        assertEquals(UP_LAST_NAME, upgradeVetDTO.getLastName());
        assertEquals(UP_EMAIL, upgradeVetDTO.getEmail());
    }

    /**
     * Eliminacion de veterinario
     */
    @Test
    public void testDeleteVet() {

        // ------------ Create ---------------

        VetDTO vetDTO = VetDTO.builder()
                .firstName("Rosa")
                .lastName("Quispe")
                .email("rosa.quispe@petclinic.com")
                .phone("944555666")
                .active(true)
                .build();

        VetDTO newVetDTO = this.vetService.create(vetDTO);
        log.info("" + newVetDTO);

        // ------------ Delete ---------------

        try {
            this.vetService.delete(newVetDTO.getId());
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validation ---------------

        try {
            this.vetService.findById(newVetDTO.getId());
            assertTrue(false);
        } catch (VetNotFoundException e) {
            assertTrue(true);
        }
    }
}
