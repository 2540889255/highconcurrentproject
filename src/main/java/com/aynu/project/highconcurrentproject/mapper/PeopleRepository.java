package com.aynu.project.highconcurrentproject.mapper;


import com.aynu.project.highconcurrentproject.bean.People;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository extends JpaRepository<People, Integer> {
}
