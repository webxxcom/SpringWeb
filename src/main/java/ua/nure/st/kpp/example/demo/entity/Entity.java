package ua.nure.st.kpp.example.demo.entity;

import java.util.Objects;

public abstract class Entity implements Comparable<Entity>{
    public static final int NO_ID = -1;

    protected long id;
    protected final String name;

    protected Entity(String name){
        this(NO_ID, name);
    }

    protected Entity(long id, String name){
        this.id = id;
        this.name = name;
    }

    public long getId(){
        return id;
    }

    /**
     * There is an ability to set the {@code id} only if {@code id}
     * equals to {@code -1}
     */
    public void setId(long id){
        if(this.id == -1)
            this.id = id;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id + ", " +
                "name='" + name + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;
        Entity entity = (Entity) o;
        return id == entity.id && Objects.equals(name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public int compareTo (Entity o) {
        return Long.compare(id, o.id);
    }
}
