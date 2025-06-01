package com.blacky.brickapi.web;

import com.blacky.brickapi.dto.LegoSetDto;
import com.blacky.brickapi.dto.LegoSetValuesDto;
import com.blacky.brickapi.service.LegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lego")
public class LegoPriceController {

    private final LegoService legoService;

    @Autowired
    public LegoPriceController (LegoService legoService) {
        this.legoService = legoService;
    }

    @GetMapping("/set/{id}")
    @ResponseBody
    public List<LegoSetDto> getLegoSets(@PathVariable String id) {
        return legoService.getLegoSets(id);
    }

    @GetMapping("/retiring/scrap")
    @ResponseBody
    public List<LegoSetValuesDto> scrapRetiringSets()  {
        return legoService.scrapRetiringSets();
    }
}
