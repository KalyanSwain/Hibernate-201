package Day2;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class TestAssignment {

	SessionFactory factory;
	
	public void setup(){
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistryBuilder srBuilder = new ServiceRegistryBuilder();
		srBuilder.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = srBuilder.buildServiceRegistry();
		factory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	
	public Skill findSkill(Session session, String name){
		Query query = session.createQuery("from Skill s where s.name=:name");
		query.setParameter("name", name);
		Skill skill = (Skill) query.uniqueResult();
		return skill;
	}
	
	public Skill saveSkill(Session session, String name){
		Skill skill = findSkill(session,name); 
		if (skill == null){
			skill = new Skill();
			skill.setName(name);
			session.save(skill);
		}
		return skill;
	}
	
	public Student findStudent(Session session, String name){
		Query query = session.createQuery("from Student s where s.name=:name");
		query.setParameter("name", name);
		Student student = (Student) query.uniqueResult();
		return student;
	}
	
	public Student saveStudent(Session session, String name){
		Student student = findStudent(session,name); 
		if (student == null){
			student = new Student();
			student.setName(name);
			session.save(student);
		}
		return student;
	}
	
	public void createData(Session session, String subjectName, String observerName, String skillName, int rank){
		Student subject = saveStudent(session,subjectName);
		Student observer = saveStudent(session,observerName);
		Skill skill = saveSkill(session,skillName);
		
		Ranking ranking = new Ranking();
		ranking.setSubject(subject);
		ranking.setObserver(observer);
		ranking.setSkill(skill);
		ranking.setRating(rank);
		
		session.save(ranking);
		
	}
	
	public void changeRank(Session session, String subjectName, String observerName, String skillName, int newRating){
		Query query = session.createQuery("from Ranking r "
				+ "where r.subject.name=:subject and "
				+ "r.observer.name=:observer and "
				+ "r.skill.name=:skill");
		query.setString("subject", subjectName);
		query.setString("observer",observerName);
		query.setString("skill", skillName);
		
		Ranking ranking = (Ranking) query.uniqueResult();
		
		if(ranking == null){
			System.out.println("Invalid Change Request");
		}
		
		ranking.setRating(newRating);
		
	 
	}
	
	
	public void deleteRank(Session session, String subjectName, String observerName, String skillName, int newRating){
		Query query = session.createQuery("from Ranking r "
				+ "where r.subject.name=:subject and "
				+ "r.observer.name=:observer and "
				+ "r.skill.name=:skill");
		query.setString("subject", subjectName);
		query.setString("observer",observerName);
		query.setString("skill", skillName);
		Ranking ranking = (Ranking) query.uniqueResult();
		session.delete(ranking);
	}
	
	
	public double getAverageBySkill(Session session, String skillName){
		Query query = session.createQuery("from Ranking r "
						+ " where r.skill.name=:skill");
		
		query.setString("skill", skillName);
		List<Ranking> ranklist  = query.list();
		double rating=0;
		for(Ranking rank:ranklist){
			rating=rating+rank.getRating();
			
		}
		
		return rating/(ranklist.size());
	}
	
	public double getAverageByStudent(Session session, String student){
		Query query = session.createQuery("from Ranking r "
						+ " where r.subject.name=:subject");
		
		query.setString("subject", student);
		List<Ranking> ranklist  = query.list();
		double rating=0;
		for(Ranking rank:ranklist){
			rating=rating+rank.getRating();
			
		}
		
		return rating/(ranklist.size());
	}
	
	public List<Student> getTopperBySkill(Session session, String skillName){
		Query query = session.createQuery("from Ranking r "
				+ " where r.skill.name=:skill");

		query.setString("skill", skillName);
		List<Ranking> ranklist  = query.list();
		List<Student> toppers= new ArrayList<Student>();
		double rating=0;
		for(Ranking rank:ranklist){
			if(rating<=rank.getRating()){
				rating=rank.getRating();
			}
			
		}
		for(Ranking rank:ranklist){
			if(rating==rank.getRating()){
				toppers.add(rank.getSubject());
			}
			
		}
		
		return toppers;
		
	}
	
	public List<Student> getTopper(Session session){
		Query query1 = session.createQuery("from Student");
		List<Student> slist=query1.list();
		Map<Student,Integer> smap=new HashMap<Student,Integer>();
		for(Student s:slist){
			int rating=0;
			Query query2 = session.createQuery("from Ranking r "
					+ " where r.subject.name=:subject");
			query2.setString("subject", s.getName());
			List<Ranking> ranklist  = query2.list();
			for(Ranking r:ranklist){
				rating=rating+r.getRating();
			}
			smap.put(s, rating);
		}
		
		List<Student> topper=new ArrayList<Student>();;
		int rating=0;
		for(Student s:smap.keySet()){
			
			if(rating<smap.get(s)){
				rating=smap.get(s);
			}
					
		}
		for(Student s:smap.keySet()){
			
			if(rating==smap.get(s)){
				topper.add(s);
			}
					
		}
		
		
		return topper;
		
	}
	
	public void sortStudentsBySkill(Session session,String skill){
		
		Criteria criteria0=session.createCriteria(Skill.class).add(Restrictions.eq("name",skill));
		Skill s=(Skill)criteria0.uniqueResult();
		
		Criteria criteria=session.createCriteria(Ranking.class).add(Restrictions.eq("skill",s)).addOrder(Order.asc("rating"));
		
		List<Ranking> ranklist=criteria.list();
		for(Ranking r:ranklist){
			System.out.println(r.getSubject().getName());
			
		}
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TestAssignment tp = new TestAssignment();
		tp.setup();
		
		Session session = tp.factory.openSession();
		Transaction tx = session.beginTransaction();
		
		// Add ranks
//		tp.createData(session, "Amit","Vijay","Django",3);
//		tp.createData(session, "Ajit","Nilesh","Spring",10);
		//tp.createData(session, "Amway","Dash","Django",4);
		//tp.deleteRank(session, "Amit","Vijay","Python",5);
		
	//	tp.changeRank(session, "Amway","Dash","Spring",12);
		// Update rating assigned by Jack Sparrow to Awantik Das
		//System.out.println(tp.getAverageByStudent(session, "Amway"));
//		session.evict(arg0);
//		session.load(arg0, arg1)
//		session.refresh(arg0);
//		session.merge(arg0)
//		session.persist(arg0);
//		session.save(arg0)
		
		/*List<Student> studentlist=tp.getTopperBySkill(session, "Python");
		for(Student s:studentlist){
			System.out.println(s.getName());
		}*/
		
		/*List<Student> studentlist=tp.getTopper(session);
		for(Student s:studentlist){
			System.out.println(s.getName());
		}*/
		tp.sortStudentsBySkill(session, "Django");
		
		tx.commit();
		//session.close();

	}

}