package models;

import models.Dica;

import java.util.List;

public interface DicasOrdenadas {
    void addDica(Dica d);
    List<Dica> getDicas();
}
