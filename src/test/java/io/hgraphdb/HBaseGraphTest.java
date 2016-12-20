package io.hgraphdb;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.tinkerpop.gremlin.structure.util.GraphFactory;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;

public class HBaseGraphTest {

    protected static final boolean useMock = true;

    protected HBaseGraph graph;

    @Before
    public void makeGraph() {
        graph = (HBaseGraph) GraphFactory.open(generateGraphConfig("youzpgraph"));
    }

    protected HBaseGraphConfiguration generateGraphConfig(String graphNamespace) {
        HBaseGraphConfiguration config = new HBaseGraphConfiguration()
                .setGraphNamespace(graphNamespace)
                .setCreateTables(true)
                .setRegionCount(1);
        if (useMock) {
            return config.setInstanceType(HBaseGraphConfiguration.InstanceType.MOCK);
        } else {
            config.set("hbase.zookeeper.quorum", "panda3,panda4,panda5");
            config.set("hbase.zookeeper.property.clientport","2181");
            config.set("zookeeper.znode.parent", "/hbase_lions");
            return config.setInstanceType(HBaseGraphConfiguration.InstanceType.DISTRIBUTED);
        }
    }

    @After
    public void clearGraph() {
        graph.close(true);
    }

    protected static String id(int idNum) {
        return String.format("%08d", idNum);
    }
}
