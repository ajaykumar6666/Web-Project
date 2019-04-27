package dao;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

@ManagedBean
public class SearchBean {
	public List<entry>entries=null;
	private String fromdate,todate,text;

	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public void search(ActionEvent evt)
	{
		entries=EntryDAO.search(util.getUname(), fromdate, todate, text);
	}
	public List<entry> getEntries()
	{
		return entries;
	}

}
