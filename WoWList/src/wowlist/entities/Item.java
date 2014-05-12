package wowlist.entities;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Item
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "getAllItems", query = "SELECT i FROM Item i"),
	@NamedQuery(name = "getItemsLikeString", query = "SELECT i FROM Item i WHERE LOWER(i.i_name) LIKE LOWER(:p)")
})
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="i_id")
	private int i_id;
	
	@Column(name="i_name")
	private String i_name;
	
	@Column(name="i_price")
	private double i_price;
	
	@Override
	public String toString() {
		return i_name;
	}

	public String 	getI_name() 				{ return i_name; }
	public void 	setI_name(String i_name) 	{ this.i_name = i_name; }

	public int 	getId() 		{ return i_id; }
	public void setId(int id) 	{ this.i_id = id; }

	public double 	getI_price() 				{ return i_price; }
	public void 	setI_price(double i_price) 	{ this.i_price = i_price; }
	
	public Item() {
		super();
	}
   
}
