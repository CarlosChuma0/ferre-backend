package ar.edu.unju.fi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ar.edu.unju.fi.entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, Integer> { }