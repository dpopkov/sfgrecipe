package learn.sfg.sfgrecipe.repositories;

import learn.sfg.sfgrecipe.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
