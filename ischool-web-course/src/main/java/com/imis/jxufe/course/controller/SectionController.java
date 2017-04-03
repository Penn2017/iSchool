package com.imis.jxufe.course.controller;

import com.imis.jxufe.base.model.ResponseEntity;
import com.imis.jxufe.base.model.course.Section;
import com.imis.jxufe.base.model.course.SectionNode;
import com.imis.jxufe.course.facade.SectionServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 章节管理
 * @author zhongping
 * @date 2017/3/30
 */
@RestController
public class SectionController {


    @Autowired
    private SectionServiceFacade sectionService;


    /***
     * 添加章节/小节
     * @return
     */
    @RequestMapping(value = "/section/addSection")
    public ResponseEntity addSection(Section section){
        boolean flag = sectionService.addSection(section);
        if (flag) {
            return new ResponseEntity(200, "添加章节成功");
        }
        return new ResponseEntity(400, "操作失败，请重新操作");
    }


    /***
     * 删除章节
     * @return
     */
    @RequestMapping(value = "/section/deleteSection/{sectionId}")
    public ResponseEntity deleteSection(@PathVariable("sectionId")Integer sectionId){
        boolean flag = sectionService.deleteSection(sectionId);
        if (flag) {
            return new ResponseEntity(200, "删除章节成功");
        }
        return new ResponseEntity(400, "操作失败，请重新操作");
    }


    /**
     * 更新章节
     * @return
     */
    @RequestMapping(value = "/section/updateSection")
    public  ResponseEntity updateSection(Section section){
        boolean flag = sectionService.updateSection(section);
        if (flag) {
            return new ResponseEntity(200, "更新章节成功");
        }
        return new ResponseEntity(400, "操作失败，请重新操作");
    }

    /**
     * 查询所有的章节
     * @return
     */
    @RequestMapping(value = "/section/queryAllSections/{courseId}")
    public ResponseEntity queryAllSections(@PathVariable("courseId")Integer courseId){
        List<SectionNode> sectionNodes = sectionService.queryAllSection(courseId);
        if (sectionNodes != null || !(sectionNodes.isEmpty())) {
            ResponseEntity responseEntity = new ResponseEntity(200, "查询成功");
            responseEntity.getParams().put("rows", sectionNodes);
            return responseEntity;
        }

        return new ResponseEntity(404, "没有任何章节");
    }





}
