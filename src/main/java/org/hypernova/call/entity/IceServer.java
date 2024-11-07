package org.hypernova.call.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ice_servers")
public class IceServer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String username;
    private String credential;

    public IceServer() {}

    public IceServer(String url, String username, String credential) {
        this.url = url;
        this.username = username;
        this.credential = credential;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getCredential() {return credential;}
    public void setCredential(String credential) {this.credential = credential;}
}
