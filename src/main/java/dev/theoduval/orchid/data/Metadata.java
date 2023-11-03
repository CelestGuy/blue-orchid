package dev.theoduval.orchid.data;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public record Metadata(String key, String value) {
    /**
     * Get
     * @return
     */
    public boolean getBooleanValue() {
        return parseBoolean(value);
    }

    public double getDoubleValue() {
        return parseDouble(value);
    }

    public float getFloatValue() {
        return parseFloat(value);
    }

    public long getLongValue() {
        return parseLong(value);
    }

    public int getIntValue() {
        return parseInt(value);
    }
}
