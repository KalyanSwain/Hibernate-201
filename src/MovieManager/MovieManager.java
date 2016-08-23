package MovieManager;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class MovieManager {

	static SessionFactory factory;
	static Session session;
	
	public static void setup(){
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistryBuilder srBuilder = new ServiceRegistryBuilder();
		srBuilder.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = srBuilder.buildServiceRegistry();
		factory = configuration.buildSessionFactory(serviceRegistry);
		session=factory.openSession();
	}
	
	public MovieManager(){
		setup();
	}
	
	public UserDetails addUser(String uname){
		Query query=session.createQuery("from UserDetails u where "
				+ "u.name=:name");
		query.setParameter("name", uname);
		UserDetails user=(UserDetails) query.uniqueResult();
		if(user==null){
			user=new UserDetails(uname,"pass");
			session.save(user);
		}
		else
			System.out.println("User '"+uname+"' already exists.");
				
		return user;
	}
	
	public MovieDomain addMovies(String Title,String Director,String synopsis){
		
		Query query=session.createQuery("from Movies m where "
				+ "m.Title=:Title");

		query.setParameter("Title",Title );
		MovieDomain movie=(MovieDomain) query.uniqueResult();
		if(movie==null  ){
			movie=new MovieDomain(Title,Director,synopsis);
			session.save(movie);
		}
		else
			System.out.println("Movie '"+Title+"' already exists in the list.");
		
		return movie;
		
	}
	
	public void wishMovie(String movie,String username,long prioriy){
		MovieDomain movies=(MovieDomain)session.createQuery("from Movies m where m.Title=:Title").setParameter("Title", movie).uniqueResult();
		UserDetails user=(UserDetails)session.createQuery("from UserDetails u where u.name=:username").setParameter("username", username).uniqueResult();
		
		WishList wish=new WishList(prioriy,user,movies);
		session.save(wish);
		
		
	}
	public void editPriority(String movie,String username,int priority){
		session.getTransaction().begin();
		Query query=session.createQuery("from WishList w where "
				+ "w.UID.name=:name and "
				
				+ "w.MID.Title=:Title");
		query.setString("name",username);
		query.setString("Title",movie);
		WishList w=(WishList) query.uniqueResult();
		w.setPriority(priority);
		session.saveOrUpdate(w);
		session.getTransaction().commit();
		//session.getTransaction().commit();
		
		//session.update(w);
		
		
	}
	public void deleteWish(String movie,String username){
		Transaction tx=session.beginTransaction();
		Query query=session.createQuery("from WishList w where "
				+ "w.UID.name=:name and "
				
				+ "w.MID.Title=:Title");
		query.setString("name",username);
		query.setString("Title",movie);
		WishList w=(WishList) query.uniqueResult();
		session.delete(w);
		tx.commit();
	}
	
	public List<MovieDomain> getMovies(){
		Query query=session.createQuery("from Movies");
		List<MovieDomain> movieslist=query.list();
		return movieslist;
	}
	
	public MovieDomain findMovie(String title){
		Query query=session.createQuery("from Movies m where "
				+ "m.Title=:title");
		query.setParameter("title", title);
		MovieDomain movie=(MovieDomain) query.uniqueResult();
		return movie;
		
	}
	
	
	public static void main(String[] args) {
		setup();
		
		MovieManager m=new MovieManager();
		//m.addUser("Kalyan");
		//m.addMovies("Top Gun", "Tony Scott", " When Maverick encounters a pair of MiGs… ");
		//m.addMovies("Jaws", "Steven Spielberg", " A tale of a white shark! ");
		//m.wishMovie("Top Gun", "Kalyan",4);
		//m.editPriority("Top Gun", "Kalyan",9);
		//m.deleteWish("Top Gun", "Kalyan");
		for(MovieDomain movie:m.getMovies()){
			System.out.println(movie.getTitle());
		}
		System.out.println(m.findMovie("Top Gun").getId());
		session.close();
		
		
	}
	
	
	

}
