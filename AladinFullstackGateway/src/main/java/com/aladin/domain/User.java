package com.aladin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.aladin.config.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * A user.
 */
@Table("jhi_user")
public class User extends com.aladin.domain.AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    @Column("first_name")
    private String firstName;

    @Size(max = 50)
    @Column("last_name")
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @NotNull
    private int activated = 0;

    @Size(min = 2, max = 10)
    @Column("lang_key")
    private String langKey;

    @Size(max = 256)
    @Column("image_url")
    private String imageUrl;

    @JsonIgnore
    @Transient
    private Set<com.aladin.domain.Authority> authorities = new HashSet<>();

    public String getId() {
        return id;
    }
    //for supporting SSO google id: id='25c25de3-1e7b-4739-82c8-dcebb19b2e4d'
    public void setId(String id) {
        String idfix = id.replaceAll("[^0-9.]", "");
        if (idfix.length()>17) {
            this.id = idfix.substring(0,16);
        }
        else
        {
            this.id =idfix;
        }
    }

    public String getLogin() {
        return login;
    }

    // Lowercase the login before saving it in database
    public void setLogin(String login) {
        this.login = StringUtils.lowerCase(login, Locale.ENGLISH);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActivated() {
        return activated==1?true:false;
    }

    public void setActivated(boolean activated) {
        this.activated = activated==true?1:1;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Set<com.aladin.domain.Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<com.aladin.domain.Authority> authorities) {
        this.authorities = authorities;
    }
    public void setAuthorities(String authority) {
        Authority authority1 = new Authority();
        authority1.setName(authority);
        this.authorities.add(authority1) ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof com.aladin.domain.User)) {
            return false;
        }
        return id != null && id.equals(((com.aladin.domain.User) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    //ID, LOGIN, first_name, last_name, EMAIL, ACTIVATED, lang_key, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE
    @Override
    public String toString() {
        return "User{" +
            "id='" + id + '\'' +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated='" + activated + '\'' +
            ", langKey='" + langKey + '\'' +
            ", createdBy='" + getCreatedBy() + '\'' +
            ", createdDate='" + getCreatedDate() + '\'' +
            ", modifiedBy='" + getLastModifiedBy() + '\'' +
            ", modifiedDate='" + getLastModifiedDate() + '\'' +
            ", authorities='" + getAuthorities() + '\'' +
            "}";
    }
}
