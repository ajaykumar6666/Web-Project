package dao;

import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

@ManagedBean
public class entry {
	private List<entry>entries=null;
	public String date,time,text,message,unamr,id;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getUnamr() {
		return unamr;
	}

	public void setUnamr(String unamr) {
		this.unamr = unamr;
	}
	
	public entry() {

		Calendar c = Calendar.getInstance();
		time = String.format("%02d:%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
		date = String.format("%4d-%02d-%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1,
				c.get(Calendar.DAY_OF_MONTH));

	}
	

	public String load() {
		entry e=EntryDAO.getEntry(id);
		this.date = e.date;
		this.time = e.time;
		this.id = e.id;
		this.text = e.text;
		this.unamr = e.unamr;
		return null; 
	}
	public String getPreviewText()
	{
		return text.length()<50 ? text:text.substring(0, 50)+"  [More..]";
	}
	
	public void add(ActionEvent avg)
	{
		unamr=util.getUname();
		boolean done=EntryDAO.add(this);
		if (done) {
			message = "Successfully added entry!";
			text = "";
		} else {
			message = "Sorry! Could not add entry!";
		}
	}
	public void update(ActionEvent evt)
	{
		unamr=util.getUname();
		boolean done=EntryDAO.update(this);
		if(done)
		{
			message="Successfully updated entry!";
			text="";
		}
		else
		{
			message="Sorry could not update!";
		}
	}
	public void delete(ActionEvent evt)
	{
		EntryDAO.delete(util.getParameter("id"));
	}
	
	public List<entry> getRecentEntries()
	{
		if(entries==null)
		{
			entries=EntryDAO.getRecentEntries(util.getUname());
		}
		return entries;
	}

}
