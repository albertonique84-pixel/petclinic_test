package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import com.tecsup.petclinic.mappers.TypeMapper;
import com.tecsup.petclinic.repositories.TypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

	private final TypeRepository typeRepository;
	private final TypeMapper typeMapper;

	@Override
	public TypeDTO create(TypeDTO typeDTO) {
		Type newType = typeRepository.save(typeMapper.mapToEntity(typeDTO));
		return typeMapper.mapToDto(newType);
	}

	@Override
	public TypeDTO update(TypeDTO typeDTO) {
		Type updatedType = typeRepository.save(typeMapper.mapToEntity(typeDTO));
		return typeMapper.mapToDto(updatedType);
	}

	@Override
	public void delete(Integer id) throws TypeNotFoundException {
		TypeDTO type = findById(id);
		typeRepository.delete(this.typeMapper.mapToEntity(type));
	}

	@Override
	public TypeDTO findById(Integer id) throws TypeNotFoundException {
		Optional<Type> type = typeRepository.findById(id);
		if (!type.isPresent())
			throw new TypeNotFoundException("Record not found...!");
		return this.typeMapper.mapToDto(type.get());
	}

	@Override
	public List<TypeDTO> findByName(String name) {
		List<Type> types = typeRepository.findByName(name);
		types.forEach(type -> log.info("{}", type));
		return types.stream().map(this.typeMapper::mapToDto).collect(Collectors.toList());
	}

	@Override
	public List<TypeDTO> findByCareLevel(String careLevel) {
		List<Type> types = typeRepository.findByCareLevel(careLevel);
		types.forEach(type -> log.info("{}", type));
		return types.stream().map(this.typeMapper::mapToDto).collect(Collectors.toList());
	}

	@Override
	public List<TypeDTO> findAll() {
		return typeRepository.findAll().stream().map(this.typeMapper::mapToDto).collect(Collectors.toList());
	}
}
