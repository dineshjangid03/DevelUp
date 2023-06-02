package com.develup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.develup.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
	
	long countByRemainingQuantityLessThan(double threshold);
	
	@Query("SELECT i.groupName FROM Item i")
    List<String> findGroupNames();
	
}
