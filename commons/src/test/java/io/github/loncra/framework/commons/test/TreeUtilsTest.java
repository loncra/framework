package io.github.loncra.framework.commons.test;

import io.github.loncra.framework.commons.id.StringIdEntity;
import io.github.loncra.framework.commons.tree.Tree;
import io.github.loncra.framework.commons.tree.TreeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serial;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class TreeUtilsTest {

    @Test
    public void testAll() {

        List<Data> data = new LinkedList<>();

        data.add(createData());
        data.add(createData());
        data.add(createData());

        data = TreeUtils.unBuildGenericTree(data);

        Assertions.assertEquals(45, data.size());

        data = TreeUtils.buildGenericTree(data);

        Assertions.assertEquals(3, data.size());

        for (Data d : data) {
            Assertions.assertEquals(4, d.getChildren().size());

            Assertions.assertEquals(3, d.getChildren().get(0).getChildren().size());
            Assertions.assertEquals(2, d.getChildren().get(1).getChildren().size());
            Assertions.assertEquals(0, d.getChildren().get(2).getChildren().size());
            Assertions.assertEquals(5, d.getChildren().get(3).getChildren().size());
        }

    }

    private Data createData() {
        Data parent = new Data("parent", null);

        Data data1 = new Data("c-1", parent.getId());
        Data data2 = new Data("c-1", parent.getId());
        Data data3 = new Data("c-1", parent.getId());
        Data data4 = new Data("c-1", parent.getId());

        parent.getChildren().add(data1);
        parent.getChildren().add(data2);
        parent.getChildren().add(data3);
        parent.getChildren().add(data4);

        data1.getChildren().add(new Data("c-1-1", data1.getId()));
        data1.getChildren().add(new Data("c-1-2", data1.getId()));
        data1.getChildren().add(new Data("c-1-3", data1.getId()));

        data2.getChildren().add(new Data("c-2-1", data2.getId()));
        data2.getChildren().add(new Data("c-2-2", data2.getId()));

        data4.getChildren().add(new Data("c-4-1", data4.getId()));
        data4.getChildren().add(new Data("c-4-2", data4.getId()));
        data4.getChildren().add(new Data("c-4-3", data4.getId()));
        data4.getChildren().add(new Data("c-4-4", data4.getId()));
        data4.getChildren().add(new Data("c-4-5", data4.getId()));

        return parent;
    }

    public static class Data extends StringIdEntity implements Tree<String, Data> {

        @Serial
        private static final long serialVersionUID = -9159953609644122758L;

        String name;

        private String parentId;

        private List<Tree<String, Data>> children = new ArrayList<>();

        public Data(
                String name,
                String parentId
        ) {
            this.name = name;
            setId(UUID.randomUUID().toString());
            this.parentId = parentId;
        }

        @Override
        public List<Tree<String, Data>> getChildren() {
            return children;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public void setChildren(List<Tree<String, Data>> children) {
            this.children = children;
        }

        @Override
        public String getParent() {
            return parentId;
        }
    }
}
