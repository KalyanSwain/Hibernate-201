package Day1;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class TestPerson {
	
	static SessionFactory factory;
	
	public static void setup(){
		
		Configuration config=new Configuration();
		config.configure();
		ServiceRegistryBuilder srBuilder=new ServiceRegistryBuilder();
		srBuilder.applySettings(config.getProperties());
		ServiceRegistry serviceregistry=srBuilder.buildServiceRegistry();
		factory=config.buildSessionFactory();
	}
	
	public static void main(String[] args) {
		
		setup();
		Session session=factory.openSession();
		Transaction txn=session.beginTransaction();
		Employee e1=new Employee("Kalyan");
		session.save(e1);
		txn.commit();
		session.close();
	}

}
