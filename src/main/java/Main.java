import static util.Utils.puts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

import util.Utils;

public class Main {

    private Gson gson;

    Main() {
        this.gson = new Gson();
    }

    public static void main(String[] args) {
        try {
            Properties props = Utils.readProps(args[0]);

            String sql;
            if (args.length >= 2) {
                sql = Utils.readFile(args[1]);
            } else {
                sql = IOUtils.toString(System.in);
            }

            Main main = new Main();
            main.main(props, sql);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void main(Properties props, String sql) {
        if (props.containsKey("driver_name")) {
            String driverName = props.getProperty("driver_name");
            try {
                Class.forName(driverName);
            } catch(ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        String jdbcUrl = buildJdbcUrl(props);

        try (
                Connection conn = DriverManager.getConnection(
                        jdbcUrl,
                        props.getProperty("user"),
                        props.getProperty("password")
                        );
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                )
        {
            ResultSetMetaData metaData = rs.getMetaData();
            int numColumns = metaData.getColumnCount();

            List<Optional<Object>> colNames = toColNames(metaData, numColumns);

            // header columns
            puts(toJson(colNames));

            // body rows
            while (rs.next()) {
                List<Optional<Object>> cols = toCols(rs, numColumns);
                puts(toJson(cols));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildJdbcUrl(Properties props) {
        String jdbcUrl = props.getProperty("url");

        if (System.getenv().containsKey("HIVE_CONF_LIST")) {
            if (jdbcUrl.contains("?")) {
                throw new IllegalStateException("Conflict between URL in config and HIVE_CONF_LIST");
            }
            jdbcUrl += "?" + System.getenv("HIVE_CONF_LIST");
        }

        return jdbcUrl;
    }

    private List<Optional<Object>> toColNames(ResultSetMetaData metaData, int numColumns)
            throws SQLException
    {
        List<Optional<Object>> colNames = new ArrayList<>();

        for (int n = 1; n <= numColumns; n++) {
            colNames.add(
                    Optional.ofNullable(
                            metaData.getColumnName(n)
                            )
                    );
        }

        return colNames;
    }

    private List<Optional<Object>> toCols(ResultSet rs, int numColumns)
            throws SQLException
    {
        List<Optional<Object>> cols = new ArrayList<>();

        for (int n = 1; n <= numColumns; n++) {
            cols.add(
                    Optional.ofNullable(
                            rs.getObject(n)
                            )
                    );
        }

        return cols;
    }

    private String toJson(List<Optional<Object>> cols) {
        String content = cols.stream()
                .map(col -> {
                    if (col.isPresent()) {
                        return this.gson.toJson(col.get().toString());
                    } else {
                        return "null";
                    }
                })
                .collect(Collectors.joining(", "));

        return "[" + content + "]";
    }

}
