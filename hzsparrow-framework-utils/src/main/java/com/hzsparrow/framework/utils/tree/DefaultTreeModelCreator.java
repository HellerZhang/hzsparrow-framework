package com.hzsparrow.framework.utils.tree;

public class DefaultTreeModelCreator<AT extends TreeModelSource> implements TreeModelCreator<DefaultTree<AT>,AT>{

    @Override
    public DefaultTree<AT> creatTreeModel(AT source) {
        DefaultTree<AT> treeNode = new DefaultTree<AT>();
        treeNode.setId(source.getTreeModelId());
        treeNode.setParentId(source.getTreeModelParentId());
        treeNode.setName(source.getTreeModelName());
        treeNode.setAttribute(source);
        return treeNode;
    }
}
