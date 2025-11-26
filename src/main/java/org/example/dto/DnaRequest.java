package org.example.dto;
import java.util.List;

public class DnaRequest {
    private List<String> dna;
    public List<String> getDna(){ return dna; }
    public void setDna(List<String> dna){ this.dna = dna; }
}
