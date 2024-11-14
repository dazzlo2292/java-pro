package ru.otus.java.pro.db;

import ru.otus.java.pro.db.annotations.*;

@RepositoryTable(title = "users_tab")
public class User {
    @RepositoryDataField(title = "id")
    @RepositoryIdField(title = "id")
    private Integer id;
    @RepositoryDataField(title = "login")
    @RepositoryInsertField(title = "login")
    private String login;
    @RepositoryDataField(title = "password")
    @RepositoryInsertField(title = "password")
    private String password;
    @RepositoryDataField(title = "nickname")
    @RepositoryInsertField(title = "nickname")
    private String nickname;

    @RepositoryGetter(field = "id")
    public Integer getId() {
        return id;
    }

    @RepositorySetter(field = "id")
    public void setId(Integer id) {
        this.id = id;
    }

    @RepositoryGetter(field = "login")
    public String getLogin() {
        return login;
    }

    @RepositorySetter(field = "login")
    public void setLogin(String login) {
        this.login = login;
    }

    @RepositoryGetter(field = "password")
    public String getPassword() {
        return password;
    }

    @RepositorySetter(field = "password")
    public void setPassword(String password) {
        this.password = password;
    }

    @RepositoryGetter(field = "nickname")
    public String getNickname() {
        return nickname;
    }

    @RepositorySetter(field = "nickname")
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
