package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="Timeline")
public class Timeline {

    private int MAX = 10;

    @Id
    @GeneratedValue
    @Column
    private long id;

    @OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.LAZY)
    private List<Dica> dicas;

    public Timeline(){
        this.dicas = new ArrayList<>();
    }

    public long getId(){
        return this.id;
    }

    public void addDica(Dica dica){
        if (this.dicas.size() < 10){
            this.dicas.add(dica);
        } else {
            this.dicas.remove(0);
            this.dicas.add(dica);
        }
    }

    public void setDicas(List<Dica> dicas){
        this.dicas = dicas;
    }


    public List<Dica> getDicas(){
        return this.dicas;
    }

}
