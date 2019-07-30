package org.iot.dsa.dslink.jdbc.mysql;

import org.iot.dsa.dslink.jdbc.AbstractMainNode;
import org.iot.dsa.dslink.jdbc.JDBCPooledNode;
import org.iot.dsa.node.DSElement;
import org.iot.dsa.node.DSInfo;
import org.iot.dsa.node.DSMap;
import org.iot.dsa.node.DSNode;
import org.iot.dsa.node.DSValueType;
import org.iot.dsa.node.action.ActionInvocation;
import org.iot.dsa.node.action.ActionResult;
import org.iot.dsa.node.action.DSAction;

public class MainNode extends AbstractMainNode {

    @Override
    protected String getHelpUrl() {
        return "https://github.com/iot-dsa-v2/dslink-java-v2-jdbc-mysql";
    }

    @Override
    protected DSAction makeNewConnectionAction() {
        DSAction act = new DSAction.Parameterless() {
            @Override
            public ActionResult invoke(DSInfo target, ActionInvocation invocation) {
                return ((MainNode) target.get()).addNewDatabase(invocation.getParameters());
            }
        };
        act.addParameter(DB_NAME, DSValueType.STRING, null);
        act.addParameter(DB_URL, DSValueType.STRING, null)
           .setPlaceHolder("jdbc:mysql://127.0.0.1:3306");
        act.addParameter(DB_USER, DSValueType.STRING, null);
        act.addParameter(DB_PASSWORD, DSValueType.STRING, null).setEditor("password");
        return act;
    }

    private ActionResult addNewDatabase(DSMap parameters) {
        parameters.put(DRIVER, DSElement.make("com.mysql.cj.jdbc.Driver"));
        DSNode nextDB = new JDBCPooledNode(parameters);
        add(parameters.getString(DB_NAME), nextDB);
        return null;
    }
}
