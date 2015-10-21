package com.pccrovers.tracker.api.data.attendance;

import com.pccrovers.tracker.api.data.BaseModel;

public class ModelAttendanceRecord extends BaseModel
{
    public Long userId;
    public Long date;
    public Presence presence;

    public enum Presence
    {
        PRESENT(0, "present"),
        LATE(1, "late"),
        ABSENT(2, "absent"),
        NO_SHOW(3, "no show");

        private int id;
        private String value;

        public static Presence getById(int id) {
            for(Presence e : values()) {
                if(e.id == id) return e;
            }
            throw new RuntimeException("Received invalid presence ID");
        }

        public static Presence getByValue(String value) {
            for(Presence e : values()) {
                if(e.value.equals(value)) return e;
            }
            throw new RuntimeException("Received invalid presence value");
        }

        public int getId()
        {
            return id;
        }

        public String getValue()
        {
            return value;
        }

        Presence(int id, String name)
        {
            this.id = id;
            this.value = name;
        }
    }
}
