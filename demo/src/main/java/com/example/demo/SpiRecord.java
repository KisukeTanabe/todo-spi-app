package com.example.demo;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class SpiRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate studyDate;

    private int count;

    public SpiRecord() {}

    public SpiRecord(LocalDate studyDate, int count) {
        this.studyDate = studyDate;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getStudyDate() {
        return studyDate;
    }

    public void setStudyDate(LocalDate studyDate) {
        this.studyDate = studyDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
