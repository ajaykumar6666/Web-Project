package dao;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@ManagedBean
public class User {
	public String unamr,fullname,password,email,mobile,message,newPassword;
	private Date joinedon;

	public String getUnamr() {
		return unamr;
	}

	public void setUnamr(String unamr) {
		this.unamr = unamr;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public Date joinedon()
	{
		return joinedon;
	}
	  public void setJoinedon(Date joinedon) {
	        this.joinedon = joinedon;
	    }
	  
	  public String register()
	  {
		  this.joinedon = new Date(); // system date for joinedon
	        boolean done = UserDAO.register(this);
	        if ( done )
	             return "login?faces-redirect=true";
	        else {
	             message = "Sorry! Could not register. Please try again!";
	             return "register";
	        }
	  }
	  public String login()
	  {
		  User u=UserDAO.login(unamr, password);
		  if(u!=null) {
			  util.addToSession("unamr", unamr);
			  util.addToSession("fullname",u.getFullname());
			  util.addToSession("mobile", u.getMobile());
			  return "/home?faces-redirect=true";
		  }
		  else
		  {
			  message = "Sorry! Invalid Login!";
			  return "login";
		  }
		
	  }
	  public void changePassword(ActionEvent evt)
	  {
		  boolean done=UserDAO.changePassword(util.getUname(), password, newPassword);
		  if(done)
		  {
			  message="Password Changed Successfully";
		  }
		  else
		  {
			  message="Sorry Not possible!";
		  }
		  
	  }
	 
	  public void recoverPassword(ActionEvent evt){
	        User  u = UserDAO.getUser(unamr, email);
	        if( u == null) {
	            message  = "Sorry! Could not find user with the given username or email address!";
	            return;
	        }
	        
	        // send mail with details
	        
	        String body = "Dear " + u.fullname + ",<p/>" +
	                   "Please use the following details to login.<p/>" + 
	                   "Username : " + u.unamr + "<br/>" +
	                   "Password : " + u.password + "<p/>" +
	                   "Team,<br/>PersonalDiary.Com";
	        
	         try {
	            Properties props = System.getProperties();
	            Session session = Session.getDefaultInstance(props, null);
	            // construct the message
	            Message msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress("webmaster@personaldiary.com"));
	            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(u.email));
	            msg.setDataHandler(new DataHandler(new String(body), "text/html"));
	            msg.setSubject("Password Recovery");
	            // send message
	            Transport.send(msg);
	            message="A mail has been sent with your details. Please use those details to login again!";
	        } catch (Exception ex) {
	            System.out.println("Error sending mail : " + ex.getMessage());
	            message="Sorry! Could not send mail! Please try again!";
	        }
	        
	    }

	    @Override
	    public String toString() {
	        return "User{" + "uname=" + unamr + ", password=" + password + ", fullname=" + fullname + ", email=" + email + ", mobile=" + mobile + ", message=" + message + ", newPassword=" + newPassword + ", joinedon=" + joinedon + '}';
	    }
	  public String logout()
	  {
		  util.terminateSession();
		  return "/all/login?faces-redirect=true";
	  }

}
