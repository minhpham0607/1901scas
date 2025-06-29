package org.example.lms1.biz.modules.service;

import org.example.lms1.biz.modules.model.dto.ModulesDTO;
import org.example.lms1.biz.modules.repository.ModulesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModulesService {

    @Autowired
    private ModulesMapper modulesMapper;

    // Thêm module — dùng ContentsDTO thay vì ModuleRequest
    public void createModule(ModulesDTO module) {
        modulesMapper.insertModule(module);
    }

    // Lấy danh sách modules theo courseId
    public List<ModulesDTO> getModulesByCourseId(int courseId) {
        return modulesMapper.getModulesByCourseId(courseId);
    }

    // Cập nhật module
    public void updateModule(ModulesDTO module) {
        modulesMapper.updateModule(module);
    }

    // Xóa module
    public void deleteModule(int moduleId) {
        modulesMapper.deleteModule(moduleId);
    }
    public int getCourseIdByModuleId(int moduleId) {
        return modulesMapper.getCourseIdByModuleId(moduleId);
    }


}
