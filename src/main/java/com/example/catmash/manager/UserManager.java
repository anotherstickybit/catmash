package com.example.catmash.manager;

import com.example.catmash.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserManager {
    private final NamedParameterJdbcTemplate template;

    public User register(String name) {
        return template.queryForObject(
                "insert into users (name) values (:name) returning id, name",
                Map.of("name", name),
                ((resultSet, i) -> new User(
                        resultSet.getLong("id"),
                        resultSet.getString("name")
                )));
    }
}
