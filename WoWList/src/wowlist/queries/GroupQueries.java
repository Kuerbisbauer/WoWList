package wowlist.queries;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import wowlist.entities.Gruppe;


public class GroupQueries {
	private EntityManager em;
	
	public GroupQueries(){
		setEM();
	}
	
	public List<Gruppe> getAllGroups(){
		Query q = em.createNamedQuery("getAllGroups");
		List<Gruppe> list = q.getResultList();
		return list;
	}
	
	public void newGruppe(Gruppe g){
		EntityTransaction et = em.getTransaction();
		try{
			et.begin();
			em.persist(g);
			et.commit();
		}catch( RuntimeException ex )
		{
			if( et != null && et.isActive() ) et.rollback();
				System.out.println(ex);
		}
	}

	public void deleteGruppe(Gruppe g) {
		EntityTransaction et = em.getTransaction();
		try{
			et.begin();
			Gruppe yolo = em.merge(g);
			em.remove(yolo);
			et.commit();
		}catch( RuntimeException ex )
		{
			if( et != null && et.isActive() ) et.rollback();
				System.out.println(ex);
		}	
	}
	
	public void editGruppe(Gruppe g) {
		EntityTransaction et = em.getTransaction();
		try{
			et.begin();
			em.merge(g);
			et.commit();
		}catch( RuntimeException ex )
		{
			if( et != null && et.isActive() ) et.rollback();
				System.out.println(ex);
		}	
	}
	
	private void setEM()
	{
		em = Persistence.createEntityManagerFactory("WoWList").createEntityManager();
	}
}
