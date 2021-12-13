package marcet.controller;

import lombok.RequiredArgsConstructor;
import marcet.dto.CategoryDTO;
import marcet.service.CategoryService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get-all")
    public List<CategoryDTO> getCategoriyes() {
        return categoryService.getCategoriyes();
    }
}
