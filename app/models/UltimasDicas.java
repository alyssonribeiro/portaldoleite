package models;

import java.util.*;
import java.util.stream.Collectors;

public class UltimasDicas implements DicasOrdenadas {

    private static final int MAX = 10;
    private Queue<Dica> dicas;

    public UltimasDicas(){
        this.dicas = new PriorityQueue<>(MAX, new Comparator<Dica>() {
            @Override
            public int compare(Dica x, Dica y) {
                return (int)(x.getId() - y.getId());
            }
        });
    }

    @Override
    public void addDica(Dica d) {
        if (this.dicas.size() < MAX){
            this.dicas.add(d);
        } else {
            this.dicas.poll();
            this.dicas.add(d);
        }
    }

    @Override
    public List<Dica> getDicas() {
        List<Dica> list = dicas.stream().collect(Collectors.toList());
        return list;
    }

}
