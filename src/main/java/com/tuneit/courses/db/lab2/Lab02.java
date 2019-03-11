package com.tuneit.courses.db.lab2;

import com.tuneit.courses.db.Lab;
import com.tuneit.courses.db.LabTask;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author serge
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Lab02 extends Lab {
    @XmlElements({
        @XmlElement(name="task01", type=Task01.class),
        @XmlElement(name="task02", type=Task02.class),
        @XmlElement(name="task03", type=Task03.class),
        @XmlElement(name="task04", type=Task04.class),
        @XmlElement(name="task05", type=Task05.class),
        @XmlElement(name="task06", type=Task06.class),
        @XmlElement(name="task07", type=Task07.class),
        @XmlElement(name="task08", type=Task08.class),
        @XmlElement(name="task09", type=Task09.class),
        @XmlElement(name="task10", type=Task10.class),
        @XmlElement(name="task11", type=Task11.class),
        @XmlElement(name="task12", type=Task12.class)
    })
    private List<LabTask> labTask = new ArrayList<>();

    @Override
    public List<LabTask> getLabTask() {
        return labTask;
    }

    public void setLabTask(List<LabTask> labTask) {
        this.labTask = labTask;
    }

    @Override
    public String toString() {
        return "Lab02{" + super.toString()+", labTask=" + labTask + '}';
    }

}

