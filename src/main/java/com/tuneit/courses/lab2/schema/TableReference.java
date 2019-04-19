package com.tuneit.courses.lab2.schema;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
public class TableReference implements Cloneable {

    @XmlElement(name = "reference")
    private List<Reference> references;

    @XmlAttribute(name = "sql-name")
    private String sqlTableName;

    @Override
    public TableReference clone() {
        try {
            TableReference tableReference = (TableReference) super.clone();
            tableReference.sqlTableName = sqlTableName;
            tableReference.references = cloneListReference(references);
            return tableReference;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Reference> cloneListReference(List<Reference> references) {
        List<Reference> cloneList = new ArrayList<>();
        for (Reference reference : references) {
            cloneList.add(reference.clone());
        }
        return cloneList;
    }

    public Reference getRandomReference(Random random) {
        return references.get(random.nextInt(references.size()));
    }
}