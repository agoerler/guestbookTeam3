package guestbook;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Basic;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_ENTRY")
@NamedQuery(name = "getAllEntries", query = "select e from Entry e")
public class Entry {

	@Id
	@GeneratedValue
	private Long id; //Note that the id attributes of the entries are filled when they are added to the entity manager
	@Basic
	private String name;
	@Basic
	private String text;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Entry)) {
			return false;
		}
		Entry entry = (Entry) obj;
		//Note id generation remark (only be entity manager)
		if(this.getId() != null && entry.getId() != null) {
			return this.getId() == entry.getId();
		} else {
			return this.getName().equals(entry.getName()) && this.getText().equals(entry.getText()) && this.getDate().equals(entry.getDate());
		}
	}

}
