package com.water.ds.db;

import com.water.ds.utils.CaseFirstOne;
import com.water.ds.utils.PropertyUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbSqlServerHr {
    public static void main(String [] args)
    {
        syncHrData();


    }
   public static List<Map<String,Object>> syncHrData()
  {
           List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
           String driverName= PropertyUtil.getProperty("jdbc.driver");
           String dbURL=PropertyUtil.getProperty("jdbc.url");
           String userName=PropertyUtil.getProperty("jdbc.username");
           String userPwd=PropertyUtil.getProperty("jdbc.password");
          try
        {
            Class.forName(driverName);
             System.out.println("加载驱动成功！");
         }catch(Exception e){
             e.printStackTrace();
             System.out.println("加载驱动失败！");
         }


      //三大对象
      Connection conn=null;
      Statement st=null;
      ResultSet rs=null;

      //需要执行的sql语句
      String sql="select e.* from v_EmployeeInfoSimplify e";
        try{
            conn=DriverManager.getConnection(dbURL,userName,userPwd);
            System.out.println("开始同步人事系统数据！");
                //使用conn，创建语句对象st
                st=conn.createStatement();
                //使用st，创建结果集对象rs
                rs=st.executeQuery(sql);
                //获取列集
                ResultSetMetaData metaData = rs.getMetaData();
                //获取列的数量
                int columnCount = metaData.getColumnCount();

            while (rs.next()){
                Map<String,Object> map=new HashMap<String, Object>();
                //循环列
                for (int i = 0; i < columnCount; i++) {
                    //通过序号获取列名,起始值为1
                    String columnName = metaData.getColumnName(i+1);//"hywater\"
                    System.out.print("\t"+rs.getString(columnName));
                    if((columnName.toUpperCase()).equals("ACCOUNT") && rs.getString(columnName)!=null)
                        map.put(CaseFirstOne.toLowerCaseFirstOne(columnName),(rs.getString(columnName)).replace("hywater\\",""));
                    else
                        map.put(CaseFirstOne.toLowerCaseFirstOne(columnName),(rs.getString(columnName)));
                }
                list.add(map);
                System.out.println();
            }


            } catch (SQLException e) {
                e.printStackTrace();
                System.out.print("SQL Server连接失败！");
            }finally {
                //如果conn不为null，则关闭它
                if(rs!=null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if(st!=null) {
                    try {
                        st.close();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if(conn!=null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("Finished...");

        return list;
         }
 }