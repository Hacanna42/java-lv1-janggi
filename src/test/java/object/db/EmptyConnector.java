package object.db;

import java.sql.Connection;

public class EmptyConnector implements DBConnector {
    @Override
    public Connection getConnection() {
        return null;
    }
}
