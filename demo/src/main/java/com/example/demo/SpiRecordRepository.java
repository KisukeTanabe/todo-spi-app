package com.example.demo;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpiRecordRepository
extends JpaRepository<SpiRecord, Long>{

    Optional<SpiRecord> findByStudyDate(LocalDate date);

}