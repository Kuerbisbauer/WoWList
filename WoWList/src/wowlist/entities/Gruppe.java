package wowlist.entities;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Group
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "getAllGroups", query = "SELECT g FROM Gruppe g")
})
public class Gruppe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="g_id")
	private int g_id;
	
	@Column(name="g_name")
	private String g_name;
	
	@Override
	public String toString() {
		return g_name;
	}
	
	public int 	getId() 		{ return g_id; }
	public void setId(int id) 	{ this.g_id = id; }

	public String 	getName() 				{ return g_name; }
	public void 	setName(String name) 	{ this.g_name = name; }

	public Gruppe() {
		super();
	}
   
}
