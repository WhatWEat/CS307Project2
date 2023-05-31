package com.cs307.bbsdatabase.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs307.bbsdatabase.Entity.Category;
import com.cs307.bbsdatabase.Mapper.CategoryMapper;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@MapperScan("com.cs307.bbsdatabase")
public class CategoryService extends ServiceImpl<CategoryMapper, Category> {

    @Autowired
    private CategoryMapper categoryMapper;

    public void addCategory(List<String> categories, int post_id) {
        categories = categories.stream().distinct().collect(Collectors.toList());
        if (categories.isEmpty()){
            categories.add("原创");
        }
        for (String category : categories) {
            Integer id = categoryMapper.findCat(category);
            System.out.println();
            System.out.println();
            System.out.println(id);
            System.out.println();
            System.out.println();
            if (id == null) {
                // insert category
                Category cat = new Category(category);
                categoryMapper.insertCat(cat);
                id = cat.getCategoryid();
            } else {
                System.out.println("字段相同");
            }
            categoryMapper.insertPostCat(id, post_id);
        }
    }

    public List<Map<String, String>> getCategories(int post_id) {
        List<String> categories = categoryMapper.findCatByPostId(post_id);
        List<Map<String, String>> res = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            String type = "success";
            switch (i % 4) {
                case 0:
                    type = "success";
                    break;
                case 1:
                    type = "info";
                    break;
                case 2:
                    type = "warning";
                    break;
                case 3:
                    type = "danger";
                    break;
            }
            res.add(Map.of("tag", categories.get(i), "type", type));
        }
        return res;
    }
}
