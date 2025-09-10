package com.beytcho.Beytcho.Repositories;

import com.beytcho.Beytcho.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
