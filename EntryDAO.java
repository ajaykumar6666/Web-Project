package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import oracle.jdbc.rowset.OracleCachedRowSet;

public class EntryDAO {

	public static boolean add(entry e) {
		try(Connection con=DataBase.getConnection())
		{
			PreparedStatement ps=con.prepareStatement("insert into diaryentries values(entryid_seq.nextval,?,?,?,?)");
			ps.setString(1, e.getUnamr());
			ps.setString(2, e.getDate());
			ps.setString(3, e.getTime());
			ps.setString(4, e.getText());
			int count=ps.executeUpdate();
			return count==1;
		}
		catch(Exception ex)
		{
			System.out.println("EntryDAO"+ex.getMessage());
			return false;
		}

	}
	  public static entry getEntry(String id) {
	       try (Connection con = DataBase.getConnection()) {
	            CachedRowSet crs = new OracleCachedRowSet();
	            crs.setCommand("select * from diaryentries where entryid = ?");
	            crs.setString(1,id);
	            crs.execute(con);
	            if ( crs.next()) {
	                entry e = new entry();
	                e.setId(crs.getString("entryid"));
	                e.setDate(crs.getString("entrydate"));
	                e.setTime(crs.getString("entrytime"));
	                e.setText(crs.getString("entrytext"));
	                return e;
	            }
	            return null;
	        } catch (Exception ex) {
	            System.out.println("EntryDAO.getEntry() -- > " + ex.getMessage());
	            return null;
	        }
	     }

	public static List<entry> getRecentEntries(String uname) {
        try (Connection con = DataBase.getConnection()) {
            CachedRowSet crs = new OracleCachedRowSet();
            crs.setCommand(
                    "select * from (select * from diaryentries where unamr = ? order by entrydate desc, entrytime desc) where rownum <= 20");
            crs.setString(1, uname);
            crs.execute(con);
            ArrayList<entry> entries = new ArrayList<>();
            while (crs.next()) {
                entry e = new entry();
                e.setId(crs.getString("entryid"));
                e.setDate(crs.getString("entrydate"));
                e.setTime(crs.getString("entrytime"));
                e.setText(crs.getString("entrytext"));
                entries.add(e);
            }
            return entries;
        } catch (Exception ex) {
            System.out.println("EntryDAO.getRecentEntries() -- > " + ex.getMessage());
            return null;
        }
    }
	 public static boolean update(entry e) {
	        try (Connection con = DataBase.getConnection()) {
	            PreparedStatement ps = con.prepareStatement
	                ("update diaryentries set entrytext= ? , entrydate=? , entrytime=? where entryid=?");
	            ps.setString(1, e.getText());
	            ps.setString(2, e.getDate());
	            ps.setString(3, e.getTime());
	            ps.setString(4, e.getId());
	            int count = ps.executeUpdate();
	            return count == 1;
	        } catch (Exception ex) {
	            System.out.println("EntryDAO-> update() : " + ex.getMessage());
	            return false;
	        }
	    }
	  public static boolean delete(String id) {
	        try (Connection con = DataBase.getConnection()) {
	            PreparedStatement ps = con.prepareStatement
	                 ("delete from diaryentries where entryid=?");
	            ps.setString(1,id);
	            int count = ps.executeUpdate();
	            return count == 1;
	        } catch (Exception ex) {
	            System.out.println("EntryDAO-> delete() : " + ex.getMessage());
	            return false;
	        }
	    }
	  public static List<entry> search(String uname,String fromdate,String todate,String text)
	  {
		  try(Connection con=DataBase.getConnection())
		  {
			  CachedRowSet crs = new OracleCachedRowSet();
	            // construct condition
	            String cond = " unamr = '" + uname + "'";
	            
	            if ( fromdate.length() > 0 )
	                  cond += " and entrydate >= '" +  fromdate + "'";
	            
	            if ( todate.length() > 0 )
	                  cond += " and entrydate <= '" +  todate + "'";
	            
	            if ( text.length() > 0 )
	                  cond += " and  upper(entrytext) like '%" +  text.toUpperCase() + "%'";
	            crs.setCommand("select * from diaryentries where" +cond+ "order by entrydate desc,entrytime desc");
	            crs.execute(con);
	            ArrayList<entry> entries=new ArrayList<>();
	            while(crs.next())
	            {
	            	entry e=new entry();
	            	e.setId(crs.getString("entryid"));
	            	e.setDate(crs.getString("entrydate"));
	                e.setTime(crs.getString("entrytime"));
	                e.setText(crs.getString("entrytext"));
	                 entries.add(e);
	            }
	            return entries;
		  }
		  catch(Exception e)
		  {
			  System.out.println("SearchBean"+e.getMessage());
			  return null;
		  }
	  }

}
