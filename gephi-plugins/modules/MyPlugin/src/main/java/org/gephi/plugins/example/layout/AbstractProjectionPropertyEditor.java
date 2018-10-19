package org.gephi.plugins.example.layout;

import java.beans.PropertyEditorSupport;

abstract class AbstractProjectionPropertyEditor extends PropertyEditorSupport {

    protected AbstractProjectionPropertyEditor() {
    }

    private String selectedRow;

    @Override
    public String[] getTags() {
        return TryLayout.attributes;
    }

    @Override
    public Object getValue() {
        return selectedRow;
    }

    @Override
    public void setValue(Object value) {
        for(int i=0;i<TryLayout.attributes.length;i++){
            if(TryLayout.attributes[i].equals((String)value)){
                selectedRow = TryLayout.attributes[i];
                break;
            }
        }
    }

    @Override
    public String getAsText() {
        return (String)getValue();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(text);
    }

    public boolean isNumberColumn(String column) {
        return false;
    }

    public boolean isStringColumn(String column) {
        return true;
    }
}
