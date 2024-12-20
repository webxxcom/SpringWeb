package ua.nure.st.kpp.example.demo.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public abstract class Journal extends Entity {
    final String outputUnit;
    JournalRecord[] listOfRecords = new JournalRecord[1];
    int length = 0;

    public static class JournalRecord {
        ProductDescription product;
        Date date;
        String traderName;

        public Date date() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String traderName() {
            return traderName;
        }

        public void setTraderName(String traderName) {
            this.traderName = traderName;
        }

        public ProductDescription product() {
            return product;
        }
    }

    private void ensureCapacity() {
        if (length < listOfRecords.length)
            return;

        final int newLength = length << 1;
        JournalRecord[] newList = new JournalRecord[newLength];
        System.arraycopy(listOfRecords, 0, newList, 0, length);

        listOfRecords = newList;
    }

    private void add(JournalRecord jr){
        listOfRecords[length++] = jr;
    }

    protected Journal(int id, String name, String outputUnit) {
        super(id, name);

        this.outputUnit = outputUnit;
    }

    /**
     * Add record to the journal if and only if {@code product}, {@code date} and {@code traderName}
     * are valid fields
     * @param product non-null reference to a Product object
     * @param traderName non-empty string representing trader's name
     */
    protected void addRecord(ProductDescription product, String traderName){
        if(product == null || traderName == null || traderName.isEmpty())
            return;

        ensureCapacity();
        //add(new JournalRecord(product, new Date(LocalDate.now().toString()), traderName));
    }

    protected boolean removeRecord(int id){
        if(id >= length) return false;

        System.arraycopy(listOfRecords, id + 1, listOfRecords, id, length - 1);
        --length;
        return true;
    }

    @Override
    public String toString() {
        if(length == 0) return "No records in the " + outputUnit + " journal";

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; ++i){
            JournalRecord jr = listOfRecords[i];
            sb.append(outputUnit).append('#').append(i).append(": ")
                    .append(jr.product).append(", ")
                    .append(jr.date).append(", ")
                    .append(jr.traderName).append('\n');
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;

        Journal that = (Journal)o;
        return super.equals(o)
                && Objects.equals(that.outputUnit, outputUnit)
                && Arrays.equals(listOfRecords, that.listOfRecords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), outputUnit.hashCode(), Arrays.hashCode(listOfRecords));
    }
}
