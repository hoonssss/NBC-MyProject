package com.sparta.jhboardapp.repository;

import com.sparta.jhboardapp.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardAppRepository extends JpaRepository<BoardEntity,Long> {
    List<BoardEntity> findAllByOrderByCreatedAtDesc();
}
