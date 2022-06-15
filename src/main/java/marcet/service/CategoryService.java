package marcet.service;

import lombok.RequiredArgsConstructor;
import marcet.dto.CategoryDTO;
import marcet.model.Category;
import marcet.repository.CategoryRepository;
import org.aspectj.weaver.patterns.ParserException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

/*Класс сервис для работы с категориями */
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    /*Получение категорий*/
    public List<CategoryDTO> getCategoriyes() {
        List<Category> categoryList = categoryRepository.findAll();
        Type listType = new TypeToken<List<CategoryDTO>>() {
        }.getType();
        return modelMapper.map(categoryList, listType);
    }

    public CategoryDTO convertToDto(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    public Category convertyToEntity(CategoryDTO categoryDTO) throws ParserException {
        return modelMapper.map(categoryDTO, Category.class);
    }
}
