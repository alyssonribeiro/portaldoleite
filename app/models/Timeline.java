package models;

import controllers.Secured;
import models.dao.GenericDAOImpl;
import models.timeline.OrdenadorDeDicas;
import models.timeline.UltimasDicas;
import play.db.jpa.Transactional;
import play.mvc.Security;

import java.util.List;

public class Timeline {

    private GenericDAOImpl dao;
    private OrdenadorDeDicas dicas;

    public Timeline(GenericDAOImpl dao){
        this.dao = dao;
        this.dicas = new UltimasDicas();
    }

    public void setOrdem(OrdenadorDeDicas tipo, List<Disciplina> disciplinas){
        this.dicas = tipo;
        for(Disciplina disc : disciplinas){
            for (Tema tema : disc.getTemas()) {
                tema.getDicas().forEach(this.dicas::addDica);
            }
        }

    }


    public void addDica(Dica dica){
        this.dicas.addDica(dica);
    }

    public List<Dica> getDicas(){
        return this.dicas.getDicas();
    }

}
