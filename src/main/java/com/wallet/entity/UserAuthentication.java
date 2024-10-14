package com.wallet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USER_AUTHENTICATION")
@Setter
@Getter
public class UserAuthentication {
	@Id
	private Integer userId;

	@OneToOne
	@MapsId
	@JoinColumn(name = "userId")
	private User user;

	@Column(name = "password", length = 80)
	@NotBlank(message = "Password cannot be blank")
	private String password;

	@Override
	public String toString() {
		return "UserAuthentication [userId=" + userId + ", user=" + user + ", password=" + password + "]";
	}
}
