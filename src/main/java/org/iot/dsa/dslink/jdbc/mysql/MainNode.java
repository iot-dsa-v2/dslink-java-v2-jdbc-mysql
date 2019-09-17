package org.iot.dsa.dslink.jdbc.mysql;

import org.iot.dsa.dslink.ActionResults;
import org.iot.dsa.dslink.jdbc.AbstractMainNode;
import org.iot.dsa.dslink.jdbc.JDBCPooledNode;
import org.iot.dsa.node.DSElement;
import org.iot.dsa.node.DSMap;
import org.iot.dsa.node.DSNode;
import org.iot.dsa.node.DSString;
import org.iot.dsa.node.action.DSAction;
import org.iot.dsa.node.action.DSIActionRequest;

public class MainNode extends AbstractMainNode {

    @Override
    protected String getHelpUrl() {
        return "https://github.com/iot-dsa-v2/dslink-java-v2-jdbc-mysql";
    }

    @Override
    protected DSAction makeNewConnectionAction() {
        DSAction act = new DSAction() {
            @Override
            public ActionResults invoke(DSIActionRequest req) {
                return ((MainNode) req.getTarget()).addNewDatabase(req.getParameters());
            }
        };
        act.addParameter(DB_NAME, DSString.NULL, null);
        act.addParameter(DB_URL, DSString.NULL, null)
           .setPlaceHolder("jdbc:mysql://127.0.0.1:3306");
        act.addParameter(DB_USER, DSString.NULL, null);
        act.addParameter(DB_PASSWORD, DSString.NULL, null).setEditor("password");
        return act;
    }

    private ActionResults addNewDatabase(DSMap parameters) {
        parameters.put(DRIVER, DSElement.make("com.mysql.cj.jdbc.Driver"));
        DSNode nextDB = new JDBCPooledNode(parameters);
        add(parameters.getString(DB_NAME), nextDB);
        return null;
    }
}
