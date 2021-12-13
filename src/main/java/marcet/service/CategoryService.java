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

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public List<CategoryDTO> getCategoriyes() {
        List<Category> categoryList = categoryRepository.findAll();
        Type listType = new TypeToken<List<CategoryDTO>>() {
        }.getType();
        List<CategoryDTO> categoryDTOList = modelMapper.map(categoryList, listType);
        return categoryDTOList;
    }

    public CategoryDTO convertToDto(Category category) {
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        return categoryDTO;
    }

    public Category convertyToEntity(CategoryDTO categoryDTO) throws ParserException {
        Category category = modelMapper.map(categoryDTO, Category.class);
        return category;
    }
}
