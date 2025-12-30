package com.example.demo.entities;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "TypeCompte")
@XmlEnum
public enum TypeCompte {
    COURANT,
    EPARGNE
}
