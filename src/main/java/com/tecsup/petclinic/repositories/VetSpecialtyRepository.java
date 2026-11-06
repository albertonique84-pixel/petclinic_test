package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(exported = false)
public interface VetSpecialtyRepository
	extends JpaRepository<VetSpecialty, VetSpecialtyId> {

	// Fetch specialties assigned to a vet
	List<VetSpecialty> findByVetId(Integer vetId);

	// Fetch vets assigned to a specialty
	List<VetSpecialty> findBySpecialtyId(Integer specialtyId);

}
