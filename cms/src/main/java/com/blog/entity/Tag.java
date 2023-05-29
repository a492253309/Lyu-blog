package com.blog.entity;

import com.blog.utils.Entity;
import lombok.Data;

@Data
public class Tag extends Entity {
    private Integer id;
    private String tagName;
}
