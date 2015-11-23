import java.util.ArrayList;
import java.util.List;
import models.Disciplina;
import models.Tema;
// 20151123 - alyssonribeiro - INICIO
import models.User;
// 20151123 - alyssonribeiro - FIM
import models.dao.GenericDAOImpl;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;


public class Global extends GlobalSettings {

	private static GenericDAOImpl dao = new GenericDAOImpl();
	private List<Disciplina> disciplinas = new ArrayList<>();
	private List<User> userList = new ArrayList<User>();

	
	@Override
	public void onStart(Application app) {
		Logger.info("Aplicação inicializada...");

		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
				if(dao.findAllByClassName(Disciplina.class.getName()).size() == 0){
					criaDisciplinaTemas();
				}
				
				// 20151123 - alyssonribeiro - INICIO
				// Se tiver menos de 10 usuarios cadastrados ele cria a quantidade de usuarios 
				// informada como parametro da funcao 'criaUsers' assim que a aplicacao eh inicializada
				if(dao.findAllByClassName(User.class.getName()).size() < 10){
					criaUsers(10);
				}
				// 20151123 - alyssonribeiro - FIM
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
	        
			// 20151123 - alyssonribeiro - INICIO
	        // Apaga os users de exemplo criados automaticamente na inicializacao
	        for (User user: userList) {
	        	dao.removeById(User.class, user.getId());
	        }
			// 20151123 - alyssonribeiro - FIM

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

		// 20151121 - alyssonribeiro - INICIO
		// Adicao de novas disciplinas e temas		
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
		dao.persist(eda);
		// 20151121 - alyssonribeiro - FIM
		
		dao.persist(si1);
		dao.flush();
	
	}
	
	// 20151123 - alyssonribeiro - INICIO
	private void criaUsers(int qtde){
				
		for (int i = 0; i < qtde; i++) {
			// Cria um novo usuario e o adiciona em uma lista a ser persistida
			userList.add(new User("user" + i, "123456", "User" + i));
			
			// Persiste o objeto criado no banco de dados
			dao.persist(userList.get(i));
		}
		
		dao.flush();
	}
	// 20151123 - alyssonribeiro - FIM

}
