package com.tool.box.utils;

import com.tool.box.vo.MenuVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 树操作工具类
 *
 * @Author v_haimiyang
 * @Date 2024/2/4 15:04
 * @Version 1.0
 */
public class TreeUtils {

    /**
     * 两层循环实现建树
     *
     * @param treeNodes 传入的树节点列表
     * @return 树
     */
    public static <T extends MenuVO> List<T> bulid(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<T>();
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentCode())) {
                trees.add(treeNode);
            }
            for (T it : treeNodes) {
                if (it.getParentCode().equals(treeNode.getMenuCode())) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.add(it);
                }
            }
        }
        return trees;
    }

    /**
     * 使用递归方法建树
     *
     * @param treeNodes 树集合
     * @return 树
     */
    public static <T extends MenuVO> List<T> buildByRecursive(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<T>();
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentCode())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes 树集合
     * @return 子节点
     */
    public static <T extends MenuVO> T findChildren(T treeNode, List<T> treeNodes) {
        for (T it : treeNodes) {
            if (treeNode.getMenuCode().equals(it.getParentCode())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }
}
