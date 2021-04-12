package com.example.catmash.manager;

import com.example.catmash.entity.Cat;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CatManager {
    private final NamedParameterJdbcTemplate template;

    public List<Cat> getAll() {
        return template.query(
                "select id, name, image_url, rating from cats order by rating limit 15",
                (resultSet, i) -> new Cat(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("image_url"),
                        resultSet.getInt("rating")
                )
        );
    }

    public Cat getById(long id) {
        return template.queryForObject(
                "select id, name, image_url, rating from cats where id = :id",
                Map.of("id", id),
                ((resultSet, i) -> new Cat(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("image_url"),
                        resultSet.getInt("rating")
                ))
        );
    }

    public List<Cat> pair(long id) {
        final var cats = template.query(
                "select c.id, c.name, c.image_url, c.rating from cats c where c.id " +
                "not in (select m.c_id from m2m_cats_users m where m.u_id = :id) order by " +
                "random() limit 2"
                ,
                Map.of("id", id),
                (resultSet, i) -> new Cat(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("image_url"),
                        resultSet.getInt("rating")
                )
        );

//        "select * from cats where id not in (select m.c_id from m2m_cats_users m where m.u_id = :id)"
//        select * from cats where id not in (select m.c_id from m2m_cats_users m where m.u_id = 1) order by random();
        for (Cat cat : cats) {
            template.update(
                    "insert into m2m_cats_users values (:cat_id, :user_id)",
                    new MapSqlParameterSource(Map.of(
                            "cat_id", cat.getId(),
                            "user_id", id
                    ))
            );
        }
        return cats;
    }

    public void vote(long id) {
        template.update(
                "update cats set rating = rating + 1 where id = :id",
                Map.of("id", id)
        );
    }
}
