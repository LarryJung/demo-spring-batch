package com.larry.hellobatch.mybatis;

import org.apache.ibatis.jdbc.SQL;

public class SqlUtils {

    public static String buildPaySelectAll() {
        return new SQL() {{
            SELECT("*");
            FROM("pay");
            ORDER_BY("id desc");
            LIMIT("#{_skiprows}, #{_pagesize}");
        }}.toString();
    }

    public static String updatePay() {
        return new SQL() {{
            UPDATE("pay");
            SET("amount=#{amount}, tx_name=#{txName}, tx_date_time=#{txDateTime}");
            WHERE("id=#{id}");
        }}.toString();
    }

    public static void main(String[] args) {
        System.out.println(buildPaySelectAll());
    }

}
