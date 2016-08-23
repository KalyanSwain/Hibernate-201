package Day3;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class SimpleObjectTestCode {

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
		
		TestCode tc = new TestCode();
		tc.setup();
		
		Session session = tc.factory.openSession();
		Transaction tx = session.beginTransaction();
		
		SimpleObject so1 = new SimpleObject();
		so1.setKey("so1");
		so1.setValue(1L);
		
		session.save(so1);
		
		Long id = so1.getId();
		tx.commit();
		session.evict(so1);
		
		System.out.println("Id of this object is " + id);
		
		//session = tc.factory.openSession();
		//tx = session.beginTransaction();
		
		SimpleObject so2 = (SimpleObject)session.load(SimpleObject.class, id);
		SimpleObject so3 = (SimpleObject)session.load(SimpleObject.class, id);
		
		
		if (so1.equals(so2)){
			System.out.println("They are same: so1 & so2");
		}
		
		System.out.println("so1 :" + so1);
		System.out.println("so2 :" + so2);
		System.out.println("so3 :" + so3);
		
		

	}

}