package de.abstraqt.QuestLogHTTP;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Transactional
public class QuestDAO {

    private RedisTemplate<String, Quest> questRedisTemplate;
    private StringRedisTemplate stringRedisTemplate;

    public QuestDAO(RedisTemplate<String, Quest> questRedisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.questRedisTemplate = questRedisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void saveQuest(Quest quest) {
        Long id = stringRedisTemplate.opsForValue().increment("id:quests");
        quest.setId(id);

        questRedisTemplate.opsForValue().set("quest:" + id, quest);
        stringRedisTemplate.opsForSet().add("quests", String.valueOf(id));
    }

    public Boolean deleteQuest(long id) {
        stringRedisTemplate.opsForSet().remove("quests", String.valueOf(id));
        return questRedisTemplate.delete("quest:" + id);
    }

    public List<Quest> getAllQuests() {
        Set<String> ids = stringRedisTemplate.opsForSet().members("quests").stream()
                .map(id -> "quest:" + id)
                .collect(Collectors.toSet());

        return questRedisTemplate.opsForValue().multiGet(ids);
    }
}
