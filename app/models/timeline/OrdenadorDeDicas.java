package models.timeline;

import java.util.List;

import models.Dica;

public interface OrdenadorDeDicas {
    void addDica(Dica d);
    List<Dica> getDicas();
}
