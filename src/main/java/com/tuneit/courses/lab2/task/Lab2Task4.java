package com.tuneit.courses.lab2.task;

import com.tuneit.courses.Task;
import com.tuneit.courses.db.LabTaskQA;
import com.tuneit.courses.db.schema.Column;
import com.tuneit.courses.lab1.schema.Condition;
import com.tuneit.courses.lab2.Lab2Task;
import com.tuneit.courses.lab2.schema.Reference;
import com.tuneit.courses.lab2.schema.Schema02;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lab2Task4 extends Lab2Task {
    @Override
    public LabTaskQA generate(Schema02 schema02, Task task) {
        query = new StringBuilder();
        answer = new StringBuilder();

        Random random = task.getRandom();

        Reference.ChainTable chainTable = schema02.getRandomChainTable(random);

        schema02.getConditionTables().removeIf(conditionTable ->
                !conditionTable.getSqlTableName().equalsIgnoreCase(chainTable.getRightTable().getTableName()));
        Condition condition = schema02.getConditionTables().get(0).getRandomCondition(random);
        String option = condition.getRandomOption(random);
        Condition.PairSign conditionSign = condition.getConditionSign(random);

        List<Column> columns = chainTable.getLeftTable().getRandomColumns(random, 1);
        List<String> columnsRevisedForWrite = new ArrayList<>();
        columns.forEach(
                column -> columnsRevisedForWrite.add(column.getNamePlural()));

        query.append("Сделать запрос для получения атрибутов из указанных таблиц, " +
                "применив фильтры по указанным условиям. Таблицы: ")
                .append(chainTable.getLeftTable().getNameGenitive())
                .append(", ")
                .append(chainTable.getRightTable().getNameGenitive())
                .append(". Атрибуты: все ");
        writeColumnToQuery(columnsRevisedForWrite, ", ", query);
        query.append(" из таблицы ")
                .append(chainTable.getLeftTable().getNameGenitive())
                .append(". Фильтры: ")
                .append(condition.getNativeColumnName())
                .append(conditionSign.getSignConditionNative())
                .append("\"")
                .append(option)
                .append("\". В запросе должен использоваться INNER JOIN.");

        columnsRevisedForWrite.clear();
        columns.forEach(
                column -> columnsRevisedForWrite.add(chainTable.getLeftTable().getTableName() + "." + column.getColumnName()));

        answer.append("select ");
        writeColumnToQuery(columnsRevisedForWrite, ", ", answer);
        answer.append(" from ")
                .append(chainTable.getLeftTable().getTableName())
                .append(" inner join ")
                .append(chainTable.getRightTable().getTableName())
                .append(" on ")
                .append(chainTable.getLeftTable().getTableName())
                .append(".")
                .append(chainTable.getLeftColumn().getColumnName())
                .append(" = ")
                .append(chainTable.getRightTable().getTableName())
                .append(".")
                .append(chainTable.getRightColumn().getColumnName())
                .append(" where ")
                .append(chainTable.getRightTable().getTableName())
                .append(".")
                .append(condition.getSqlColumnName())
                .append(conditionSign.getSignConditionSql())
                .append("'")
                .append(option)
                .append("';");

        return new LabTaskQA(task.getId(), query.toString(), answer.toString());
    }

}