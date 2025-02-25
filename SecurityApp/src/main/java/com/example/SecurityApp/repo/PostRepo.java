package com.example.SecurityApp.repo;

import com.example.SecurityApp.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepo extends JpaRepository<PostEntity,Long> {
}
