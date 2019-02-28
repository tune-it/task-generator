package com.tuneit.courses.db;

import com.tuneit.courses.Task;
import com.tuneit.courses.db.schema.Column;
import com.tuneit.courses.db.schema.Schema;
import com.tuneit.courses.db.schema.Table;

import javax.xml.bind.annotation.*;
import java.util.*;

/**
 *
 * @author serge
 */
@XmlAccessorType(XmlAccessType.NONE)
public abstract class LabTask {
    protected StringBuilder query = new StringBuilder();
    protected StringBuilder answer = new StringBuilder();

    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name="id") protected String id;
    
    @XmlElement(name="prolog") protected String prolog;
    @XmlElement(name="epilog") protected String epilog;

    @XmlElement(name = "forbidden-list")
    protected List<String> forbiddenList = new ArrayList<>();
    @XmlTransient
    static HashMap<String, List<Table>> allowed = new HashMap<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProlog() {
        return prolog;
    }

    public void setProlog(String prolog) {
        this.prolog = prolog;
    }

    public String getEpilog() {
        return epilog;
    }

    public void setEpilog(String epilog) {
        this.epilog = epilog;
    }
    
    protected static List<Table> removeForbidenElements(Schema s, List<String> forbidenElements) {
        ArrayList<Table> allowed = new ArrayList<>();
        for (Table table : s.getTables()) {
            if (forbidenElements.stream().noneMatch(str -> str.equalsIgnoreCase(table.getTableName()))) {
                Table allowedTable = table.clone();

                allowed.add(allowedTable);
            }
        }
        return allowed;
    }


    public List<String> getForbiddenList() {
        return forbiddenList;
    }

    public void setForbiddenList(List<String> forbiddenList) {
        this.forbiddenList = forbiddenList;
    }

    @Override
    public String toString() {
        return "LabTask{" + "description=" + description + ", id=" + id + 
                ", prolog=" + prolog + ", epilog=" + epilog + '}';
    }

    public LabTaskQA generate(Schema schema, Task task) {
        query = new StringBuilder();
        answer = new StringBuilder();

        Table table = getRandomTable(schema, task).clone();

        Collections.shuffle(table.getColumns(), getRandom(task));

        updateQuery(table, task);

        updateAnswer(table, task);

        return new LabTaskQA(task.getId(), query.toString(), answer.toString());
    }

    protected void updateQuery(Table table, Task task) {
        List<Column> columns = table.getColumns();
        query.append(getProlog());
        writeColumnFromTable(query, columns, task);
        query.append(getEpilog()).append(table.getTableName()).append('.');
    }

    protected void updateQueryPL(Table table, Task task) {
        List<Column> columns = table.getColumns();
        query.append(getProlog());
        writeColumnFromTablePL(query, columns, task);
        query.append(getEpilog()).append(table.getNameRPL()).append('.');
    }

    protected void updateAnswer(Table table, Task task) {
        List<Column> columns = table.getColumns();
        answer.append("SELECT ");
        writeColumnFromTable(answer, columns, task);
        answer.append(" FROM ").append(table.getTableName()).append(';');
    }

    protected void writeColumnFromTable(StringBuilder string, List<Column> columns, Task task) {
        Collections.shuffle(columns, getRandom(task));
        string.append(columns.get(0).getColumnName());
        for (int i = 1; i < getRandom(task).nextInt(columns.size()); i++) {
            string.append(", ").append(columns.get(i).getColumnName());
        }
    }

    protected void writeColumnFromTablePL(StringBuilder string, List<Column> columns, Task task) {
        Collections.shuffle(columns, getRandom(task));
        string.append(columns.get(0).getNamePL());
        for (int i = 1; i < getRandom(task).nextInt(columns.size()); i++) {
            string.append(", ").append(columns.get(i).getNamePL());
        }
    }

    protected Table getRandomTable(Schema schema, Task task) {
        if (!allowed.containsKey(schema.getName())) {
            allowed.put(schema.getName(), removeForbidenElements(schema, forbiddenList));
        }

        List<Table> tables = allowed.get(schema.getName());
        return tables.get(getRandom(task).nextInt(tables.size()));
    }

    protected Random getRandom(Task task) {
        int seed = task.getId().toUpperCase().hashCode();
        return new Random(seed);
    }
    
}


// old style seed generation. hashCode is much more simply
// keep it hear for posibility to use in future
//        long seed = 120483;
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");            
//            md.update(t.getId().toUpperCase().getBytes());
//            String md5 = DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
//            seed = Long.parseUnsignedLong(md5.substring(0, 16), 16);
//            
//        } catch (NoSuchAlgorithmException|NumberFormatException ex) {
//            Logger.getLogger(SchemaLoader.class.getName()).log(Level.SEVERE, null, ex);
//        }
