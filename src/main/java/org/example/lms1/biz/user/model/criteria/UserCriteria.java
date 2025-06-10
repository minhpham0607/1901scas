package org.example.lms1.biz.user.model.criteria;



import lombok.Getter;
import lombok.Setter;
import org.example.lms1.common.http.criteria.Page;

@Getter
@Setter
public class UserCriteria extends Page {
    private Long roleId;
    private String roleName;

    public UserCriteria() {}

    public UserCriteria(Long roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }
}