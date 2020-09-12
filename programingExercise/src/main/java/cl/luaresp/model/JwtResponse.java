package cl.luaresp.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * object to return a token with expiration time
 * 
 * @author luaresp
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "timestamp", "expired", "token" })
public class JwtResponse implements Serializable {

	public JwtResponse() {
		super();
		this.timestamp = new Date();
	}

	private static final long serialVersionUID = 259908309703472596L;

	@JsonProperty("timestamp")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date timestamp;

	@JsonProperty("expired")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date expired;

	@JsonProperty("token")
	private String jwttoken;

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Date getExpired() {
		return expired;
	}

	public void setExpired(Date expired) {
		this.expired = expired;
	}

	public String getJwttoken() {
		return jwttoken;
	}

	public void setJwttoken(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JwtResponse [timestamp=");
		builder.append(timestamp);
		builder.append(", expired=");
		builder.append(expired);
		builder.append(", jwttoken=");
		builder.append(jwttoken);
		builder.append("]");
		return builder.toString();
	}

}