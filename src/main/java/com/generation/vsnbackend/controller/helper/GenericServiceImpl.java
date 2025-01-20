package com.generation.vsnbackend.controller.helper;

import java.util.List;

public interface GenericServiceImpl<X, ID>
{
	List<X> getList();

	X getOneById(ID id);

	void deleteById(ID id);

	X save(X entity);

}

