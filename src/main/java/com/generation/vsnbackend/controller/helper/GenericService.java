package com.generation.vsnbackend.controller.helper;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class GenericService<X,ID> implements GenericServiceImpl<X,ID>
{
	private final JpaRepository<X,ID> repository;

	public GenericService(JpaRepository<X,ID> repository)
	{
		this.repository = repository;
	}

	@Override
	public List<X> getList() {
		return repository.findAll();
	}

	@Override
	public X getOneById(ID id) {
		return repository.findById(id).get();
	}

	@Override
	public X save(X entity) {
		return repository.save(entity);
	}

	@Override
	public void deleteById(ID id) {
		repository.deleteById(id);
	}
}
