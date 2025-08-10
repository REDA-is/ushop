package org.reda.ushop.web;

import org.reda.ushop.model.UserNeeds;
import org.reda.ushop.services.AiModelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AiController {

    private final AiModelService aiService;

    public AiController(AiModelService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/recommend")
    public List<String> recommend(@RequestBody UserNeeds needs) throws Exception {
        System.out.println("Received: " + needs);
        return aiService.predict(
                needs.getWantsPump(),
                needs.getWantsToDry(),
                needs.getIsTired(),
                needs.getWantsToBulk()
        );
    }
}

