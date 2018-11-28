package org.iot.dsa.dslink.jdbc.mysql;

import org.iot.dsa.dslink.jdbc.C3P0PooledDBConnectionNode;
import org.iot.dsa.dslink.jdbc.JDBCv2Helpers;
import org.iot.dsa.node.DSElement;
import org.iot.dsa.node.DSInfo;
import org.iot.dsa.node.DSMap;
import org.iot.dsa.node.DSNode;
import org.iot.dsa.node.DSValueType;
import org.iot.dsa.node.action.ActionInvocation;
import org.iot.dsa.node.action.ActionResult;
import org.iot.dsa.node.action.DSAction;

public class MainNode extends org.iot.dsa.dslink.jdbc.MainNode {
	
	@Override
	protected DSAction makeAddDatabaseAction() {
		DSAction act = new DSAction.Parameterless() {
            @Override
            public ActionResult invoke(DSInfo target, ActionInvocation invocation) {
                return ((MainNode) target.get()).addNewDatabase(invocation.getParameters());
            }
        };
        act.addParameter(JDBCv2Helpers.DB_NAME, DSValueType.STRING, null);
        act.addParameter(JDBCv2Helpers.DB_URL, DSValueType.STRING, null)
           .setPlaceHolder("jdbc:mysql://127.0.0.1:3306");
        act.addParameter(JDBCv2Helpers.DB_USER, DSValueType.STRING, null);
        act.addParameter(JDBCv2Helpers.DB_PASSWORD, DSValueType.STRING, null).setEditor("password");
        return act;
	}
	
	@Override
	protected String getHelpUrl() {
		return "https://github.com/iot-dsa-v2/dslink-java-v2-jdbc-mysql";
	}
	
	private ActionResult addNewDatabase(DSMap parameters) {
		parameters.put(JDBCv2Helpers.DRIVER, DSElement.make("com.mysql.cj.jdbc.Driver"));
        DSNode nextDB = new C3P0PooledDBConnectionNode(parameters);
        add(parameters.getString(JDBCv2Helpers.DB_NAME), nextDB);
        return null;
    }
}
