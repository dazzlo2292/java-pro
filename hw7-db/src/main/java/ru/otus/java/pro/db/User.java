package ru.otus.java.pro.db;

import ru.otus.java.pro.db.annotations.*;

@RepositoryTable(title = "users_tab")
public class User {
    @RepositoryIdField(title = "id")
    private Integer id;
    @RepositoryDataField(title = "login")
    private String login;
    @RepositoryDataField(title = "password")
    private String password;
    @RepositoryDataField(title = "nickname")
    private String nickname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public User() {
    }

    public User(Integer id, String login, String password, String nickname) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
