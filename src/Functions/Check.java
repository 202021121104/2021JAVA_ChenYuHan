package Functions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Check {
    Connection conn = null; //数据库连接对象
    Statement stmt = null;  //数据库操作对象
    ResultSet rs = null;    //查询结果集
    String sql;

    public Check() {

    }

    public Check(Connection conn) throws SQLException {
        this.conn = conn;
        stmt = conn.createStatement();
        rs = null;
    }


    public int CheckBossRegister(String firstPassword, String secondPassword, String userName, String companyName) {
        //1:登陆成功 2:密码不一致 3:账号已存在 4:公司已存在 5:未填写完整

        //未填写完整返回5
        if (userName.equals("账号") || companyName.equals("公司") || firstPassword.equals("密码") || secondPassword.equals("确认密码")) {
            return 5;
        }

        //密码不一致返回2
        if (!firstPassword.equals(secondPassword)) {
            return 2;
        }

        //账号已存在返回3
        //sql 查询操作 select xxx from _table_ where yyy =
        sql = "select * from account where name = '" + userName + "'";
        try {
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return 3;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //公司已存在返回4
        sql = "select * from company where companyName ='" + companyName + "'";
        try {
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return 4;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //所有条件满足
        return 1;
    }

    public int CheckStaffRegister(String firstPassword, String secondPassword, String userName, String companyName) {
        //1:登陆成功 2:密码不一致 3:账号已存在 4:公司不存在 5:未填写完整

        //未填写完整返回5
        if (userName.equals("账号") || companyName.equals("公司") || firstPassword.equals("密码") || secondPassword.equals("确认密码")) {
            return 5;
        }

        //密码不一致返回2
        if (!firstPassword.equals(secondPassword)) {
            return 2;
        }

        //账号已存在返回3
        sql = "select * from account where name = '" + userName + "'";
        try {
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return 3;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //公司不存在返回4
        sql = "select * from company where companyName ='" + companyName + "'";
        try {
            rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                return 4;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //所有条件满足
        return 1;
    }

    public boolean CheckLogin(String name, String password) {
        sql = "select * from account where name = '" + name + "' and password = '" + password + "'";

        try {
            rs = stmt.executeQuery(sql);
            //如果用户名存在 返回true
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
