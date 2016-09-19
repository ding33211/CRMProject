package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

import java.util.Date;

public class MyDaoGenerator {
    public static void main(String[] args) throws Exception {
        //生成的相关文件的包名路径
        Schema schema = new Schema(1, "com.soubu.crmproject.base.greendao");
        addTable(schema);
        //生成相关文件的具体路径
        new DaoGenerator().generateAll(schema, "../CRMProject/app/src/main/java");
    }

    //客户表
    private static void addTable(Schema schema) {
        //员工列表
        Entity staff = schema.addEntity("Staff");
        staff.setTableName("Staff");
        //id主键自增长
        staff.addIdProperty().primaryKey().autoincrement();
        staff.addStringProperty("nickname");
        staff.addStringProperty("username");
        staff.addStringProperty("department");
        staff.addStringProperty("position");
        staff.addStringProperty("mobile");
        staff.addStringProperty("email");
        staff.addStringProperty("employeeNumber");
        staff.addStringProperty("officeAddress");
        staff.addBooleanProperty("activated");
        staff.addDateProperty("activatedAt");
        staff.addDateProperty("createdAt");
        staff.addDateProperty("updatedAt");
        staff.addStringProperty("staff_id");

        //联系人列表
        Entity contact = schema.addEntity("Contact");
        contact.setTableName("Contact");
        //id主键自增长
        contact.addIdProperty().primaryKey().autoincrement();
        contact.addStringProperty("name");
        contact.addStringProperty("customer");
        contact.addStringProperty("position");
        contact.addStringProperty("phone");
        contact.addStringProperty("mobile");
        contact.addStringProperty("qq");
        contact.addStringProperty("wechat");
        contact.addStringProperty("wangwang");
        contact.addStringProperty("department");
        contact.addDateProperty("createdAt");
        contact.addDateProperty("updatedAt");
        contact.addDateProperty("touchedAt");
        contact.addIntProperty("touchedCount");
        contact.addStringProperty("contact_id");

//        //产品表
//        Entity product = schema.addEntity("Product");
//        product.setTableName("Product");
//        product.addIdProperty().primaryKey().autoincrement();
//        product.addLongProperty("product_id").notNull();
//        product.addLongProperty("classification");
//        product.addStringProperty("title");
//        product.addDoubleProperty("price");
//        product.addStringProperty("img");
//        //库存
//        product.addLongProperty("stock");
//        //成本
//        product.addLongProperty("cost");
//        //购买方式,现货还是订货
//        product.addIntProperty("buy_state");
//        //幅宽
//        product.addLongProperty("width");
//        //品名
//        product.addStringProperty("product_name");
//        //成分
//        product.addStringProperty("ingredient");
//        //用途
//        product.addStringProperty("use");
//        //季节
//        product.addIntProperty("season");
//        //工艺
//        product.addIntProperty("crafts");
//        //颜色
//        product.addStringProperty("color");
//        //花型
//        product.addStringProperty("flower");
//
//        //客户表
//        Entity client = schema.addEntity("Client");
//        client.setTableName("Client");
//        client.addIdProperty().primaryKey().autoincrement();
//        //客户id
//        client.addLongProperty("client_id").notNull();
//        client.addStringProperty("name");
//        client.addIntProperty("type");
//        client.addStringProperty("phone");
//        client.addStringProperty("email");
//        //传真
//        client.addStringProperty("fax");
//        //网址
//        client.addStringProperty("network");
//        //地区
//        client.addStringProperty("area");
//        //简介
//        client.addStringProperty("summary");
//        //邮编
//        client.addStringProperty("zip_code");
//        //跟进状态
//        client.addStringProperty("follow_state");
//        //客户来源
//        client.addStringProperty("from");
//        //行业
//        client.addStringProperty("industry");
//        //规模
//        client.addIntProperty("scale");
//        //负责人
//        client.addStringProperty("principal");
//        //地址
//        client.addStringProperty("address");
//        //主营产品
//        client.addStringProperty("main_products");
//        //营业额
//        client.addStringProperty("turnover");
//        //经验模式
//        client.addStringProperty("empirical_mode");
//        //主营行业
//        client.addStringProperty("major_business");
//
//
//
//        //线索表
//        Entity clue = schema.addEntity("Clue");
//        clue.setTableName("Clue");
//        clue.addIdProperty().primaryKey().autoincrement();
//        //线索id
//        clue.addStringProperty("clue_id").notNull();
//        clue.addStringProperty("wechat");
//        clue.addStringProperty("phone");
//        //座机
//        clue.addStringProperty("landline");
//        //职务
//        clue.addStringProperty("post");
//        //部门
//        clue.addStringProperty("department");
//        //公司名称
//        clue.addStringProperty("company");
//        //线索来源
//        clue.addIntProperty("from");
//        //姓名
//        clue.addStringProperty("name");
//        //负责人
//        clue.addStringProperty("principal");
//        //地区
//        clue.addStringProperty("area");
//        //地址
//        clue.addStringProperty("address");
//        //邮编
//        clue.addStringProperty("zip_code");
//        //备注
//        clue.addStringProperty("remark");
//        //网址
//        clue.addStringProperty("network");
//        //邮箱
//        clue.addStringProperty("email");
//        //旺旺号
//        clue.addStringProperty("wangwang");
//        //跟进状态
//        clue.addIntProperty("follow_state");
//        //qq
//        clue.addStringProperty("qq");
//
        Entity remind = schema.addEntity("Remind");
        remind.setTableName("Remind");
        remind.addIdProperty().primaryKey().autoincrement();
        //提醒日期
        remind.addDateProperty("date");
        //是否需要提醒   0表示不提醒,1表示提醒
        remind.addIntProperty("remind_state");
        //是否完成   0表示没完成,1表示完成
        remind.addIntProperty("complete_state");
        //内容
        remind.addStringProperty("content");
        //地址
        remind.addStringProperty("address");
        //电话
        remind.addStringProperty("phone");
    }

}
