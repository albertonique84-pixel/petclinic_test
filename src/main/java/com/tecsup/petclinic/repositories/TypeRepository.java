package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository
	extends JpaRepository<Type, Integer> {

	// Fetch types by name
	List<Type> findByName(String name);

	// Fetch types by care level
	List<Type> findByCareLevel(String careLevel);

	@Override
	List<Type> findAll();

}
