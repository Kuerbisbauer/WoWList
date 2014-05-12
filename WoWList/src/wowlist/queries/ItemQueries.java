package wowlist.queries;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import wowlist.entities.Item;

public class ItemQueries {
	private EntityManager em;
	
	public ItemQueries(){
		setEM();
	}
	
	public List<Item> getAllItems(){
		Query q = em.createNamedQuery("getAllItems");
		List<Item> list = q.getResultList();
		return list;
	}
	
	public List<Item> getItemsLikeString(String s){
		Query q = em.createNamedQuery("getItemsLikeString");
		q.setParameter("p", "%" + s + "%");
		List<Item> list = q.getResultList();
		return list;
	}
	
	private void setEM()
	{
		em = Persistence.createEntityManagerFactory("WoWList").createEntityManager();
	}
}
