package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class VetSpecialtyServiceTest {

    @Autowired
    private VetSpecialtyService vetSpecialtyService;

    /**
     * Asignacion de una especialidad a un veterinario
     */
    @Test
    public void testAssignSpecialtyToVet() {

        Integer VET_ID = 1;          // James Carter (no tiene especialidades)
        Integer SPECIALTY_ID = 3;    // dentistry
        LocalDate CERTIFICATION_DATE = LocalDate.of(2020, 5, 10);
        Integer YEARS_EXPERIENCE = 4;

        VetSpecialtyDTO vetSpecialtyDTO = VetSpecialtyDTO.builder()
                .vetId(VET_ID)
                .specialtyId(SPECIALTY_ID)
                .certificationDate(CERTIFICATION_DATE)
                .yearsExperience(YEARS_EXPERIENCE)
                .primary(true)
                .notes("Dental care for small pets")
                .build();

        VetSpecialtyDTO newVetSpecialtyDTO = this.vetSpecialtyService.assign(vetSpecialtyDTO);

        log.info("SPECIALTY ASSIGNED : " + newVetSpecialtyDTO);

        assertEquals(VET_ID, newVetSpecialtyDTO.getVetId());
        assertEquals(SPECIALTY_ID, newVetSpecialtyDTO.getSpecialtyId());
        assertEquals(CERTIFICATION_DATE, newVetSpecialtyDTO.getCertificationDate());
        assertEquals(YEARS_EXPERIENCE, newVetSpecialtyDTO.getYearsExperience());
        assertTrue(newVetSpecialtyDTO.getPrimary());

        // ------------ Validation ---------------

        try {
            VetSpecialtyDTO found = this.vetSpecialtyService.findById(VET_ID, SPECIALTY_ID);
            assertEquals(YEARS_EXPERIENCE, found.getYearsExperience());
        } catch (VetSpecialtyNotFoundException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Busqueda de especialidades por veterinario
     */
    @Test
    public void testFindSpecialtiesByVet() {

        Integer VET_ID = 3;          // Linda Douglas : surgery y dentistry
        int SIZE_EXPECTED = 2;

        List<VetSpecialtyDTO> specialties = this.vetSpecialtyService.findSpecialtiesByVet(VET_ID);

        log.info("SPECIALTIES OF VET " + VET_ID + " : " + specialties);

        assertEquals(SIZE_EXPECTED, specialties.size());
        specialties.forEach(vs -> assertEquals(VET_ID, vs.getVetId()));
    }

    /**
     * Busqueda de veterinarios por especialidad
     */
    @Test
    public void testFindVetsBySpecialty() {

        Integer SPECIALTY_ID = 2;    // surgery : Linda Douglas y Rafael Ortega
        int SIZE_EXPECTED = 2;

        List<VetSpecialtyDTO> vets = this.vetSpecialtyService.findVetsBySpecialty(SPECIALTY_ID);

        log.info("VETS OF SPECIALTY " + SPECIALTY_ID + " : " + vets);

        assertEquals(SIZE_EXPECTED, vets.size());
        vets.forEach(vs -> assertEquals(SPECIALTY_ID, vs.getSpecialtyId()));
    }

    /**
     * Eliminacion de la relacion veterinario - especialidad
     */
    @Test
    public void testRemoveSpecialtyFromVet() {

        Integer VET_ID = 6;          // Sharon Jenkins (no tiene especialidades)
        Integer SPECIALTY_ID = 1;    // radiology

        // ------------ Assign ---------------

        VetSpecialtyDTO vetSpecialtyDTO = VetSpecialtyDTO.builder()
                .vetId(VET_ID)
                .specialtyId(SPECIALTY_ID)
                .certificationDate(LocalDate.of(2019, 8, 1))
                .yearsExperience(3)
                .primary(false)
                .notes("Temporary assignment")
                .build();

        VetSpecialtyDTO newVetSpecialtyDTO = this.vetSpecialtyService.assign(vetSpecialtyDTO);
        log.info("" + newVetSpecialtyDTO);

        // ------------ Remove ---------------

        try {
            this.vetSpecialtyService.remove(VET_ID, SPECIALTY_ID);
        } catch (VetSpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validation ---------------

        try {
            this.vetSpecialtyService.findById(VET_ID, SPECIALTY_ID);
            assertTrue(false);
        } catch (VetSpecialtyNotFoundException e) {
            assertTrue(true);
        }
    }
}
