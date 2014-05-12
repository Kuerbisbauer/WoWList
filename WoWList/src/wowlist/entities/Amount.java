package wowlist.entities;

import java.io.Serializable;

import javax.persistence.*;


/**
 * Entity implementation class for Entity: Amount
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "getAllItemsFromGivenGroup", query = "SELECT a FROM Amount a WHERE a.fk_group = :p"),
})
public class Amount implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="a_id")
	private int a_id;
	
	@ManyToOne
	@JoinColumn(name="fk_item")
	private Item fk_Item;
	
	@ManyToOne
	@JoinColumn(name="fk_group")
	private Gruppe fk_group;
	
	@Column(name="a_amount")
	private int a_amount;

	public int getM_id() {
		return a_id;
	}

	public void setM_id(int a_id) {
		this.a_id = a_id;
	}

	public Item getFk_Item() {
		return fk_Item;
	}

	public void setFk_Item(Item fk_Item) {
		this.fk_Item = fk_Item;
	}

	public Gruppe getFk_group() {
		return fk_group;
	}

	public void setFk_group(Gruppe fk_group) {
		this.fk_group = fk_group;
	}

	public int getA_amount() {
		return a_amount;
	}

	public void setA_amount(int a_amount) {
		this.a_amount = a_amount;
	}
	
	public Amount(){
		super();
	}
	
/*
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="m_id")
	private int m_id;
	
	@ManyToOne
	@JoinColumn(name="fk_item")
	private Item fk_item;
	
	@ManyToOne
	@JoinColumn(name="fk_group")
	private Group fk_group;
	
	@Column(name="m_amount")
	private int m_amount;
	
	
	public int 	getM_amount() 				{ return m_amount; }
	public void setM_amount(int m_amount) 	{ this.m_amount = m_amount; }

	public int 	getM_id() 			{ return m_id; }
	public void setM_id(int m_id) 	{ this.m_id = m_id; }

	public Item getFk_item() 				{ return fk_item; }
	public void setFk_item(Item fk_item) 	{ this.fk_item = fk_item; }

	public Group 	getFk_group() 				{ return fk_group; }
	public void 	setFk_group(Group fk_group) { this.fk_group = fk_group; }
	
	public Amount() {
		super();
	}
   */
}
