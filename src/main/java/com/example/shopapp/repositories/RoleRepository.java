package com.example.shopapp.repositories;

import com.example.shopapp.models.Role;
import com.example.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long>{

}
