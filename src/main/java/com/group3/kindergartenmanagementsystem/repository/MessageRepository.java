package com.group3.kindergartenmanagementsystem.repository;

import com.group3.kindergartenmanagementsystem.model.Message;
import com.group3.kindergartenmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByFromUserAndToUserOrFromUserAndToUserOrderBySendTimeAsc(User fromUser1, User toUser1, User fromUser2, User toUser2);
    @Query(value = "SELECT m.*\n" +
            "FROM message m\n" +
            "         JOIN (\n" +
            "    SELECT\n" +
            "        CASE\n" +
            "            WHEN from_user = ?1 THEN to_user\n" +
            "            ELSE from_user\n" +
            "            END AS other_user,\n" +
            "        MAX(id) AS max_id\n" +
            "    FROM message\n" +
            "    WHERE from_user = '1' OR to_user = '1'\n" +
            "    GROUP BY other_user\n" +
            ") sub\n" +
            "              ON (m.from_user = sub.other_user OR m.to_user = sub.other_user)\n" +
            "                  AND m.id = sub.max_id", nativeQuery = true)
    List<Message> findNewestMessageListByUser(Integer userId);
}
