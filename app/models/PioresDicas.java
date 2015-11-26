package models;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class PioresDicas implements DicasOrdenadas {
    private static final int MAX = 10;
    private Queue<Dica> dicas;

    public PioresDicas(){
        this.dicas = new PriorityQueue<>(MAX, new Comparator<Dica>() {
            @Override
            public int compare(Dica x, Dica y) {
                if (x.getDiscordancias() > y.getDiscordancias()) {
                    return -1;
                } else if (x.getDiscordancias() < y.getDiscordancias()) {
                    return 1;
                } else {
                    return 0;
                }
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
