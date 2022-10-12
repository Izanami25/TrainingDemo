package ulan.kz.training.controller;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("message")
public class MessageController {
    int counter = 4;

    public List<Map<String,String>> messages = new ArrayList<Map<String, String>>() {{
       add (new HashMap<String, String>() {{ put("id", "1"); put("text", "First message");  }});
       add (new HashMap<String, String>() {{ put("id", "2"); put("text", "Second message");  }});
       add (new HashMap<String, String>() {{ put("id", "3"); put("text", "Third message");  }});
    }};

    @GetMapping
    public List<Map<String, String>> list() {
        return messages;
    }
    @GetMapping("{id}")
    public Map<String, String> pick(@PathVariable String id) throws ClassNotFoundException {
        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(ClassNotFoundException::new);
    }

    @PostMapping("{id}")
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(counter++));

        messages.add(message);
        return message;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) throws ClassNotFoundException {
        Map<String, String> messageFromDb = pick(message.get("id"));

        messageFromDb.putAll(message);
        messageFromDb.put("id", id);

        return messageFromDb;

    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) throws ClassNotFoundException {
        Map<String, String> message = pick(id);
        messages.remove(message);
    }
}
