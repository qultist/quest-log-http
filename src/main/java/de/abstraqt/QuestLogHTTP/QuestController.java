package de.abstraqt.QuestLogHTTP;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestController {

    private QuestDAO questDAO;

    public QuestController(QuestDAO questDAO) {
        this.questDAO = questDAO;
    }

    @GetMapping("/quests")
    List<Quest> getQuests() {
        return questDAO.getAllQuests();
    }

    @PostMapping("/quests")
    String createQuest(@RequestBody Quest quest) {
        questDAO.saveQuest(quest);

        return String.format("Created: \"%s\", id: %d", quest.getTitle(), quest.getId());
    }

    @DeleteMapping("/quests/{id:[\\d]+}")
    public String deleteQuest(@PathVariable long id) {
        return questDAO.deleteQuest(id)
                ? String.format("Successfully deleted quest with id %d", id)
                : String.format("There is no quest with id %d", id);
    }
}
