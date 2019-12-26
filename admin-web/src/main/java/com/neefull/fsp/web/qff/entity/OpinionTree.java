package com.neefull.fsp.web.qff.entity;

import com.neefull.fsp.web.system.entity.Dept;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Data
public class OpinionTree<T> implements Serializable {


    private static final long serialVersionUID = 6528789634376190938L;
    private String id;
    private String icon;
    private String href;
    private String name;
    private Map<String, Object> state;
    private boolean checked = false;
    private Map<String, Object> attributes;
    private List<OpinionTree<T>> children;
    private String parentId;
    private boolean hasParent = false;
    private boolean hasChild = false;

    private Opinion data;

    public void initChildren() {
        this.children = new ArrayList<>();
    }

}
