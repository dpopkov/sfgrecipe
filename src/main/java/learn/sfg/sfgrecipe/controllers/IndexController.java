package learn.sfg.sfgrecipe.controllers;

import learn.sfg.sfgrecipe.domain.Category;
import learn.sfg.sfgrecipe.domain.UnitOfMeasure;
import learn.sfg.sfgrecipe.repositories.CategoryRepository;
import learn.sfg.sfgrecipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @GetMapping({"", "/", "index"})
    public String getIndexPage() {
        Optional<Category> catOpt = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> uomOpt = unitOfMeasureRepository.findByDescription("Teaspoon");
        System.out.println("Cat id is " + catOpt.orElseThrow().getId());
        System.out.println("Uom id is " + uomOpt.orElseThrow().getId());
        return "index";
    }
}
