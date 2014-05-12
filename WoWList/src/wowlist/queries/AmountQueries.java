package wowlist.queries;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import wowlist.entities.Amount;
import wowlist.entities.Gruppe;
import wowlist.entities.Item;



public class AmountQueries {

private EntityManager em;
	
	public AmountQueries(){
		setEM();
	}
	
	public List<Amount> getAllAmountsFromGivenGroup(Gruppe g){
		Query q = em.createNamedQuery("getAllItemsFromGivenGroup");
		q.setParameter("p", g);
		List<Amount> list = q.getResultList();
		return list;
	}
	
	
	public void newAmount(Gruppe g, Item i, int amount){
		EntityTransaction et = em.getTransaction();
		try{
			et.begin();
			Amount m = new Amount();
			m.setFk_group(g);
			m.setFk_Item(i);
			m.setA_amount(amount);
			em.persist(m);
			et.commit();
		}catch( RuntimeException ex )
		{
			if( et != null && et.isActive() ) et.rollback();
				System.out.println(ex);
		}
	}
	
	public void deleteAmount(Amount m){
		EntityTransaction et = em.getTransaction();
		try{
			et.begin();
			Amount yolo = em.merge(m);
			em.remove(yolo);
			et.commit();
		}catch( RuntimeException ex )
		{
			if( et != null && et.isActive() ) et.rollback();
				System.out.println(ex);
		}
	}
	
	
	public void editAmount(Amount m) {
		EntityTransaction et = em.getTransaction();
		try{
			et.begin();
			em.merge(m);
			et.commit();
		}catch( RuntimeException ex )
		{
			if( et != null && et.isActive() ) et.rollback();
				System.out.println(ex);
		}
	}

		
	private void setEM(){
		em = Persistence.createEntityManagerFactory("WoWList").createEntityManager();
	}
}
