package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User; // Sesuaikan dengan lokasi Entity User lu

@Repository 
public interface UserRepo extends JpaRepository<User, String> {
    // seluruh kebutuhan CRUD disini, memanfaatkan jpa repo, dia secara otomatis sudah menggunakan seluruh kebutuhan
}