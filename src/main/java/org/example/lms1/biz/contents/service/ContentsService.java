package org.example.lms1.biz.contents.service;

import org.example.lms1.biz.contents.model.dto.ContentsDTO;
import org.example.lms1.biz.contents.repository.ContentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentsService {

    @Autowired
    private ContentsMapper contentsMapper;

    public void createContent(ContentsDTO content) {
        contentsMapper.insertContent(content);
    }

    public List<ContentsDTO> getContentsByCourseId(int courseId) {
        return contentsMapper.getContentsByCourseId(courseId);
    }

    public void updateContent(ContentsDTO content) {
        contentsMapper.updateContent(content);
    }

    public void deleteContent(int contentId) {
        contentsMapper.deleteContent(contentId);
    }

    // ✅ Thêm phương thức này để controller có thể gọi
    public Integer getModuleIdByContentId(int contentId) {
        return contentsMapper.getModuleIdByContentId(contentId);
    }
}
