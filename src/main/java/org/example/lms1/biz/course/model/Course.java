package org.example.lms1.biz.course.model;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Course {
    private Integer courseId;
    private String title;
    private String description;
    private Integer categoryId;
    private Integer instructorId;
    private String status;
    private BigDecimal price;
    private String instructorName;

}
