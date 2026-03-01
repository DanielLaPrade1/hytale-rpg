package com.danmods.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T>  {
    private T data;
    private TreeNode<T> parent;
    private List<TreeNode<T>> children;

    public TreeNode(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode<T> child) {
        this.children.add(child);
        child.parent = this;
    }
}
