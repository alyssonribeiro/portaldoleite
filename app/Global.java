import java.util.ArrayList;
import java.util.List;

import models.Disciplina;
import models.Tema;
import models.dao.GenericDAOImpl;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;


public class Global extends GlobalSettings {

	private static GenericDAOImpl dao = new GenericDAOImpl();
	private List<Disciplina> disciplinas = new ArrayList<>();
	
	@Override
	public void onStart(Application app) {
		Logger.info("Aplicação inicializada...");

		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				if(dao.findAllByClassName(Disciplina.class.getName()).size() == 0){
					criaDisciplinaTemas();
				}
			}
		});
	}
	
	@Override
	public void onStop(Application app){
	    JPA.withTransaction(new play.libs.F.Callback0() {
	    @Override
	    public void invoke() throws Throwable {
	        Logger.info("Aplicação finalizando...");
	        disciplinas = dao.findAllByClassName("Disciplina");

	        for (Disciplina disciplina: disciplinas) {
	        dao.removeById(Disciplina.class, disciplina.getId());
	       } 
	    }}); 
	}
	
	private void criaDisciplinaTemas(){
		Disciplina si1 = new Disciplina("Sistemas de Informação 1");
		si1.addTema(new Tema("Análise x Design"));
		si1.addTema(new Tema("Orientação a objetos"));
		si1.addTema(new Tema("GRASP"));
		si1.addTema(new Tema("GoF"));
		si1.addTema(new Tema("Arquitetura"));
		si1.addTema(new Tema("Play"));
		si1.addTema(new Tema("JavaScript"));
		si1.addTema(new Tema("HTML / CSS / Bootstrap"));
		si1.addTema(new Tema("Heroku"));
		si1.addTema(new Tema("Labs"));
		si1.addTema(new Tema("Minitestes"));
		si1.addTema(new Tema("Projeto"));

		Disciplina eda = new Disciplina("Estrutura de Dados e Algoritmos");
		eda.addTema(new Tema("Vetores ou Arrays"));
		eda.addTema(new Tema("Lista"));
		eda.addTema(new Tema("Fila"));
		eda.addTema(new Tema("Pilha"));
		eda.addTema(new Tema("Árvores"));
		eda.addTema(new Tema("Grafos"));
		eda.addTema(new Tema("Tabela de hashing"));

		Disciplina tc = new Disciplina("Teoria da Computação");
		tc.addTema(new Tema("Autômatos"));
		tc.addTema(new Tema("Máquina de Turing"));
		tc.addTema(new Tema("Gramáticas Livres de Contexto"));
		
		dao.persist(tc);
		dao.persist(si1);
		dao.persist(eda);
		dao.flush();
	
	}
}
