import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//20151123 - alyssonribeiro - INICIO
import models.*;
// 20151123 - alyssonribeiro - FIM
import models.dao.GenericDAOImpl;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.db.jpa.JPA;


public class Global extends GlobalSettings {

	private static GenericDAOImpl dao = new GenericDAOImpl();
	private List<Disciplina> disciplinas = new ArrayList<>();
	// 20151123 - alyssonribeiro - INICIO
	private List<User> userList = new ArrayList<User>();
	private List<Dica> dicasList = new ArrayList<Dica>();
	// 20151123 - alyssonribeiro - FIM
    // 20151125 - ddspog - INICIO
    private Disciplina si1;
    private Disciplina eda;
    private Disciplina tc;
    // 20151125 - ddspog - FIM
	
	@Override
	public void onStart(Application app) {
		Logger.info("Aplicação inicializada...");

		JPA.withTransaction(new play.libs.F.Callback0() {
			@Override
			public void invoke() throws Throwable {
                // 20151125 - ddspog - INICIO
                // Cria dicas usando Users
                if(dao.findAllByClassName(Disciplina.class.getName()).size() == 0){
                    criaDisciplinaTemas();
                    // 20151123 - alyssonribeiro - INICIO
                    // Se tiver menos de 10 usuarios cadastrados ele cria a quantidade de usuarios
                    // informada como parametro da funcao 'criaUsers' assim que a aplicacao eh inicializada
                    if(dao.findAllByClassName(User.class.getName()).size() < 10){
                        criaUsers();
                        if(dao.findAllByClassName(Dica.class.getName()).size() == 0){
                            criaDicas();
                        }
                    }
                    // 20151123 - alyssonribeiro - FIM
                }
                // 20151125 - ddspog - FIM
			}
		});
	}

    @Override
	public void onStop(Application app) {
        JPA.withTransaction(new play.libs.F.Callback0() {
            @Override
            public void invoke() throws Throwable {
                Logger.info("Aplicação finalizando...");
                disciplinas = dao.findAllByClassName("Disciplina");

                for (Disciplina disciplina : disciplinas) {
                    dao.removeById(Disciplina.class, disciplina.getId());
                }
                for (Dica dica : dicasList) {
                    dao.removeById(Dica.class, dica.getId());
                }

                // 20151123 - alyssonribeiro - INICIO
                // Apaga os users de exemplo criados automaticamente na inicializacao
                for (User user : userList) {
                    dao.removeById(User.class, user.getId());
                }
                // 20151123 - alyssonribeiro - FIM

	    }}); 
	}
	
	private void criaDisciplinaTemas(){
		si1 = new Disciplina("Sistemas de Informação 1");
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
		eda = new Disciplina("Estrutura de Dados e Algoritmos");
		eda.addTema(new Tema("Vetores ou Arrays"));
		eda.addTema(new Tema("Lista"));
		eda.addTema(new Tema("Fila"));
		eda.addTema(new Tema("Pilha"));
		eda.addTema(new Tema("Árvores"));
		eda.addTema(new Tema("Grafos"));
		eda.addTema(new Tema("Tabela de hashing"));

		tc = new Disciplina("Teoria da Computação");
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
	private void criaUsers(){
		// 20151125 - ddspog - INICIO
        // Cria usuários com detalhes
        userList.add(new User("miguxo.joao@gmail.com", "123456", "joaomigs"));
        userList.get(0).setNome("João Miguel");
        userList.add(new User("willcg.prog@gmail.com", "123456", "willamancio"));
        userList.get(1).setNome("Will Amâncio");
        userList.add(new User("esterpg@gmail.com", "123456", "ester"));
        userList.get(2).setNome("Ester Rodrigues");
        userList.add(new User("lourivesprog@gmail.com", "123456", "lourival"));
        userList.get(3).setNome("Lourival Júnior");
        userList.add(new User("tsetutscg@gmail.com", "123456", "tsetuttut"));
        userList.get(4).setNome("Tio Tuts");
        userList.add(new User("analistakiko@gmail.com", "123456", "kikao"));
        userList.get(5).setNome("Kiko Gordo");
        userList.add(new User("caioabreu@gmail.com", "123456", "abreulima"));
        userList.get(6).setNome("Abreu Lima");
        userList.add(new User("felipewizart@gmail.com", "123456", "gandalf"));
        userList.get(7).setNome("Felipe Mago");
        userList.add(new User("julio.webdes@gmail.com", "123456", "juvernes"));
        userList.get(8).setNome("Júlio Verne");
        userList.add(new User("patriciaddprog@gmail.com", "123456", "pattys"));
        userList.get(9).setNome("Pati Dandara");
        // 20151125 - ddspog - FIM

        // 20151125 - alyssonribeiro - INCIO
        // Iterator para varrer pela lista e persistir os objetos criados
        Iterator<User> it = userList.iterator();
        
		while (it.hasNext()) {
			User userit = it.next();
			dao.persist(userit);
		}
		// 20151125 - alyssonribeiro - FIM
		
		dao.flush();
	}
	// 20151123 - alyssonribeiro - FIM

    // 20151125 - ddspog - INICIO
    private void criaDicas() {
        Timeline timeline = new Timeline(dao);

        Tema javascr = si1.getTemaByNome("JavaScript");
        DicaMaterial material01 = new DicaMaterial("https://www.codecademy.com/pt-BR/learn/javascript");
        javascr.addDica(material01);
        material01.setTema(javascr);
        material01.setUser(userList.get(5).getNome());
        material01.addUsuarioQueVotou(userList.get(0).getLogin());
        material01.incrementaConcordancias();
        material01.addUsuarioQueVotou(userList.get(3).getLogin());
        material01.incrementaConcordancias();
        material01.addUsuarioQueVotou(userList.get(7).getLogin());
        material01.incrementaConcordancias();
        material01.addUsuarioQueVotou(userList.get(6).getLogin());
        material01.incrementaConcordancias();
        timeline.addDica(material01);
        dicasList.add(material01);
        dao.persist(material01);

        DicaConselho conselho01 = new DicaConselho("No Browser do Google Chrome, ao apertar F12, você pode" +
                " utilizar um espaço para digitar comandos JavaScript. Com isso, pode-se modificar o site" +
                " em execução e treinar bastante no assunto.");
        javascr.addDica(conselho01);
        conselho01.setTema(javascr);
        conselho01.setUser(userList.get(2).getNome());
        conselho01.addUsuarioQueVotou(userList.get(3).getLogin());
        conselho01.incrementaConcordancias();
        timeline.addDica(conselho01);
        dicasList.add(conselho01);
        dao.persist(conselho01);

        Tema automats = tc.getTemaByNome("Autômatos");
        DicaMaterial material02 = new DicaMaterial("http://www.simuladordeautomatos.com/");
        automats.addDica(material02);
        material02.setTema(automats);
        material02.setUser(userList.get(0).getNome());
        material02.addUsuarioQueVotou(userList.get(1).getLogin());
        material02.addUserCommentary(userList.get(1).getLogin(), "Muito chato de usar");
        material02.incrementaDiscordancias();
        material02.addUsuarioQueVotou(userList.get(8).getLogin());
        material02.addUserCommentary(userList.get(8).getLogin(), "Achei bem complicado");
        material02.incrementaDiscordancias();
        material02.addUsuarioQueVotou(userList.get(5).getLogin());
        material02.incrementaConcordancias();
        timeline.addDica(material02);
        dicasList.add(material02);
        dao.persist(material02);

        Tema play = si1.getTemaByNome("Play");
        DicaConselho conselho02 = new DicaConselho("Procure sempre exemplos de projetos feitos com Play no github," +
                " para então poder tirar várias dúvidas que você possa ter. Além de aprender soluções novas para" +
                " alguns problemas.");
        play.addDica(conselho02);
        conselho02.setTema(play);
        conselho02.setUser(userList.get(8).getNome());
        conselho02.addUsuarioQueVotou(userList.get(0).getLogin());
        conselho02.incrementaConcordancias();
        conselho02.addUsuarioQueVotou(userList.get(2).getLogin());
        conselho02.incrementaConcordancias();
        timeline.addDica(conselho02);
        dicasList.add(conselho02);
        dao.persist(conselho02);

        Tema projeto = si1.getTemaByNome("Projeto");
        DicaAssunto assunto01 = new DicaAssunto("Maestria em Play! Framework");
        projeto.addDica(assunto01);
        assunto01.setTema(projeto);
        assunto01.setUser(userList.get(4).getNome());
        timeline.addDica(assunto01);
        dicasList.add(assunto01);
        dao.persist(assunto01);

        Tema gof = si1.getTemaByNome("GoF");
        DicaMaterial material03 = new DicaMaterial("http://www.uml.org.cn/c++/pdf/DesignPatterns.pdf");
        gof.addDica(material03);
        material03.setTema(gof);
        material03.setUser(userList.get(2).getNome());
        material03.addUsuarioQueVotou(userList.get(0).getLogin());
        material03.incrementaConcordancias();
        material03.addUsuarioQueVotou(userList.get(4).getLogin());
        material03.incrementaConcordancias();
        material03.addUsuarioQueVotou(userList.get(1).getLogin());
        material03.incrementaConcordancias();
        material03.addUsuarioQueVotou(userList.get(5).getLogin());
        material03.incrementaConcordancias();
        material03.addUsuarioQueVotou(userList.get(6).getLogin());
        material03.incrementaConcordancias();
        material03.addUsuarioQueVotou(userList.get(7).getLogin());
        material03.incrementaConcordancias();
        timeline.addDica(material03);
        dicasList.add(material03);
        dao.persist(material03);

        dao.flush();
    }
    // 20151125 - ddspog - FIM

}
