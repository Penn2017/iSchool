package com.imis.jxufe.base.model.course;

import java.io.Serializable;
import java.util.List;

/**
 * 章节树
 *
 * @author zhongping
 * @date 2017/4/3
 */
public class SectionNode  implements Serializable{

    private Integer id;

    private String name;

    private Integer type;

    private List<SectionNode> children;


    public SectionNode() {
        /**
         * empty
         */
    }

    public SectionNode(Integer id, String name, List<SectionNode> rows) {
        this.id = id;
        this.name = name;
        this.children = rows;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SectionNode> getChildren() {
        return children;
    }

    public void setChildren(List<SectionNode> children) {
        this.children = children;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
