package Day3;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class TestCode {
	
	SessionFactory factory;
	
	public void setup(){
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistryBuilder srBuilder = new ServiceRegistryBuilder();
		srBuilder.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = srBuilder.buildServiceRegistry();
		factory = configuration.buildSessionFactory(serviceRegistry);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TestCode tc=new TestCode();
		tc.setup();
		Session session=tc.factory.openSession();
		Transaction tx=session.beginTransaction();
		
		Email email=new Email("First email");
		Message message=new Message("Firstmessage");
		//email.setMessage(message);
		message.setEmail(email);
		session.save(email);
		session.save(message);
		tx.commit();
		session.close();
		

	}

}
