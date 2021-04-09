package com.hzsparrow.framework.utils.tree;

import java.util.List;

public class DefaultTree<AT extends TreeModelSource> implements Tree<DefaultTree<AT>,AT>{

    private Object id;

    private Object parentId;

    private String name;

    private AT attribute;

    private List<DefaultTree<AT>> child;

    public void setId(Object id) {
        this.id = id;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AT getAttribute() {
        return attribute;
    }

    public void setAttribute(AT attribute) {
        this.attribute = attribute;
    }

    public void setChild(List<DefaultTree<AT>> child) {
        this.child = child;
    }

    @Override
    public Object getId() {
        return id;
    }

    public List<DefaultTree<AT>> getChild() {
        return child;
    }

}
