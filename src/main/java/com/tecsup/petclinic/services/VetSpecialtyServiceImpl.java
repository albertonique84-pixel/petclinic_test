package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import com.tecsup.petclinic.mappers.VetSpecialtyMapper;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class VetSpecialtyServiceImpl implements VetSpecialtyService {

	private final VetSpecialtyRepository vetSpecialtyRepository;
	private final VetSpecialtyMapper vetSpecialtyMapper;

	@Override
	public VetSpecialtyDTO assign(VetSpecialtyDTO vetSpecialtyDTO) {
		VetSpecialty newVetSpecialty = vetSpecialtyRepository.save(vetSpecialtyMapper.mapToEntity(vetSpecialtyDTO));
		return vetSpecialtyMapper.mapToDto(newVetSpecialty);
	}

	@Override
	public VetSpecialtyDTO findById(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException {
		Optional<VetSpecialty> vetSpecialty = vetSpecialtyRepository.findById(new VetSpecialtyId(vetId, specialtyId));
		if (!vetSpecialty.isPresent())
			throw new VetSpecialtyNotFoundException("Record not found...!");
		return this.vetSpecialtyMapper.mapToDto(vetSpecialty.get());
	}

	@Override
	public List<VetSpecialtyDTO> findSpecialtiesByVet(Integer vetId) {
		List<VetSpecialty> list = vetSpecialtyRepository.findByVetId(vetId);
		list.forEach(vs -> log.info("{}", vs));
		return list.stream().map(this.vetSpecialtyMapper::mapToDto).collect(Collectors.toList());
	}

	@Override
	public List<VetSpecialtyDTO> findVetsBySpecialty(Integer specialtyId) {
		List<VetSpecialty> list = vetSpecialtyRepository.findBySpecialtyId(specialtyId);
		list.forEach(vs -> log.info("{}", vs));
		return list.stream().map(this.vetSpecialtyMapper::mapToDto).collect(Collectors.toList());
	}

	@Override
	public void remove(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException {
		VetSpecialtyDTO vetSpecialty = findById(vetId, specialtyId);
		vetSpecialtyRepository.delete(this.vetSpecialtyMapper.mapToEntity(vetSpecialty));
	}
}
