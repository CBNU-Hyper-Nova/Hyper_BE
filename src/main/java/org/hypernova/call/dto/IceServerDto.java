package org.hypernova.call.dto;

public class IceServerDto {
    private String url;
    private String username;
    private String credential;

    public IceServerDto() {}

    public IceServerDto(String url, String username, String credential) {
        this.url = url;
        this.username = username;
        this.credential = credential;
    }

    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getCredential() {return credential;}
    public void setCredential(String credential) {this.credential = credential;}
}
