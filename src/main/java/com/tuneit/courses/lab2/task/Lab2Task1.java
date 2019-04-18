package com.tuneit.courses.lab2.task;

import com.tuneit.courses.Task;
import com.tuneit.courses.db.LabTaskQA;
import com.tuneit.courses.db.schema.Column;
import com.tuneit.courses.db.schema.Table;
import com.tuneit.courses.lab2.Lab2Task;
import com.tuneit.courses.lab2.schema.Schema02;
import com.tuneit.courses.lab2.schema.Substring;
import com.tuneit.courses.lab2.schema.TableSubstring;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lab2Task1 extends Lab2Task {
    @Override
    public LabTaskQA generate(Schema02 schema02, Task task) {
        query = new StringBuilder();
        answer = new StringBuilder();

        Random random = task.getRandom();


        TableSubstring tableSubstring = schema02.getRandomTableSubstring(random);

        Table table = schema02.findTableBySqlName(tableSubstring.getSqlTableName());

        Substring substringType = tableSubstring.getRandomSubstring(random);

        Column likeColumn = table.findColumn(substringType.getSqlNameColumn());
        table.getColumns().remove(likeColumn);

        boolean isLeftSubstring = random.nextBoolean();

        String nativeSubstring;
        String sqlSubstring;
        if (isLeftSubstring) {
            String substring = substringType.getRandomLeftSubstring(random);
            nativeSubstring = "начинаются на \"" + substring + "\"";
            sqlSubstring = "'" + substring + "%'";
        } else {
            String substring = substringType.getRandomRightSubstring(random);
            nativeSubstring = "заканчиваются на \"" + substring + "\"";
            sqlSubstring = "'%" + substring + "'";
        }

        List<Column> columns = table.getRandomColumns(random, 2);
        List<String> columnsRevisedForWrite = new ArrayList<>();
        columns.forEach(
                column -> columnsRevisedForWrite.add(column.getNamePlural()));

        query.append("Выдать ");
        writeColumnToQuery(columnsRevisedForWrite, ", ", query);
        query.append(" из таблицы ")
                .append(table.getNameGenitive())
                .append(", ")
                .append(likeColumn.getNamePlural())
                .append(" которых ")
                .append(nativeSubstring)
                .append(".");

        columnsRevisedForWrite.clear();
        columns.forEach(
                column -> columnsRevisedForWrite.add(column.getColumnName()));

        answer.append("select ");
        writeColumnToQuery(columnsRevisedForWrite, ", ", answer);
        answer.append(" from ")
                .append(table.getTableName())
                .append(" where ")
                .append(likeColumn.getColumnName())
                .append(" like ")
                .append(sqlSubstring);
        System.out.println(query);
        System.out.println(answer);

//        Table table = schema02.getRandomTable(task.getRandom());
//        List<Column> columns = table.getRandomColumns(task.getRandom(), 2);
//
//        List<String> columnsRevisedForWrite = new ArrayList<>();
//        columns.forEach(
//                column -> columnsRevisedForWrite.add(column.getNamePlural() + " (" + column.getColumnName().toLowerCase() + ")"));
//
//        query.append("Выведите все ");
//        writeColumnToQuery(columnsRevisedForWrite, ", ", query);
//        query.append(" для таблицы ")
//                .append(table.getTableName())
//                .append(".");
//
//        columnsRevisedForWrite.clear();
//        columns.forEach(
//                column -> columnsRevisedForWrite.add(column.getColumnName()));
//
//        answer.append("SELECT ");
//        writeColumnToQuery(columnsRevisedForWrite, ", ", answer);
//        answer.append(" FROM ")
//                .append(table.getTableName())
//                .append(";");
//
        return new LabTaskQA(task.getId(), query.toString(), answer.toString());
    }

}