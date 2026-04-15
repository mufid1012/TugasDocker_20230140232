package com.tugas.deploy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mahasiswa {
    private String nama;
    private String nim;
    private String jenisKelamin;
}
