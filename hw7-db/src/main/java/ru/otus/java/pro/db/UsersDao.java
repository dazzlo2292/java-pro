package ru.otus.java.pro.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UsersDao {
    private final DataSource dataSource;

    public UsersDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<User> getUserByLoginAndPassword(String login, String password) {
        try (ResultSet rs = dataSource.getStatement().executeQuery("select * from users_tab where login = '" + login + "' AND password = '" + password + "'")) {
            return Optional.of(new User(rs.getInt("id"), rs.getString("login"), rs.getString("password"), rs.getString("nickname")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<User> getUserById(Long id) {
        try (ResultSet rs = dataSource.getStatement().executeQuery("select * from users_tab where id = " + id)) {
            if (rs.next()) {
                return Optional.of(new User(rs.getInt("id"), rs.getString("login"), rs.getString("password"), rs.getString("nickname")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (ResultSet rs = dataSource.getStatement().executeQuery("select * from users_tab")) {
            while (rs.next()) {
                result.add(new User(rs.getInt("id"), rs.getString("login"), rs.getString("password"), rs.getString("nickname")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.unmodifiableList(result);
    }

    public void save(User user) throws SQLException {
        dataSource.getStatement().executeUpdate(String.format("insert into users_tab (login, password, nickname) values ('%s', '%s', '%s');", user.getLogin(), user.getPassword(), user.getNickname()));
    }

    public void saveAll(List<User> users) throws SQLException {
        dataSource.getConnection().setAutoCommit(false);
        for (User u : users) {
            dataSource.getStatement().executeUpdate(String.format("insert into users_tab (login, password, nickname) values ('%s', '%s', '%s');", u.getLogin(), u.getPassword(), u.getNickname()));
        }
        dataSource.getConnection().setAutoCommit(true);
    }
}
