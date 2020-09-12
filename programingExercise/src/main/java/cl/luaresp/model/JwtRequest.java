package cl.luaresp.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Objecto to mapper a username and password
 * 
 * @author luaresp
 *
 */
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = -5004605714800350218L;

	@JsonProperty("username")
	private String username;

	@JsonProperty("password")
	private String password;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JwtRequest [username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}

}