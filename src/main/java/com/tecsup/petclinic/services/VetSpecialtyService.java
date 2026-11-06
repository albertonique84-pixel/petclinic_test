package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;

import java.util.List;

public interface VetSpecialtyService {

	/**
	 * Asigna una especialidad a un veterinario
	 */
	VetSpecialtyDTO assign(VetSpecialtyDTO vetSpecialtyDTO);

	/**
	 * Busca una asignacion por su llave compuesta
	 */
	VetSpecialtyDTO findById(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException;

	/**
	 * Especialidades de un veterinario
	 */
	List<VetSpecialtyDTO> findSpecialtiesByVet(Integer vetId);

	/**
	 * Veterinarios de una especialidad
	 */
	List<VetSpecialtyDTO> findVetsBySpecialty(Integer specialtyId);

	/**
	 * Elimina la relacion veterinario - especialidad
	 */
	void remove(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException;
}
