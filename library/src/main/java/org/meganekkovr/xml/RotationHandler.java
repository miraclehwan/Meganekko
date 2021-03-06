package org.meganekkovr.xml;

import android.content.Context;
import android.support.annotation.NonNull;

import org.joml.Quaternionf;
import org.meganekkovr.Entity;

import java.util.Locale;

/**
 * Define {@code rotation} attribute.
 */
class RotationHandler implements XmlAttributeParser.XmlAttributeHandler {

    @NonNull
    @Override
    public String attributeName() {
        return "rotation";
    }

    @Override
    public void parse(@NonNull Entity entity, @NonNull String rawValue, @NonNull Context context) {

        String[] strs = rawValue.split("\\s+", 3);
        if (strs.length >= 3) {

            float x = (float) Math.toRadians(Float.parseFloat(strs[0]));
            float y = (float) Math.toRadians(Float.parseFloat(strs[1]));
            float z = (float) Math.toRadians(Float.parseFloat(strs[2]));

            String rotOrder = strs.length == 4 ? strs[3].toUpperCase(Locale.US) : "XYZ";
            Quaternionf q = new Quaternionf();
            switch (rotOrder) {
                case "XYZ":
                    q.rotateXYZ(x, y, z);
                    break;
                case "YXZ":
                    q.rotateYXZ(x, y, z);
                    break;
                case "ZYX":
                    q.rotateZYX(x, y, z);
                    break;
            }
            entity.setRotation(q);
        }
    }
}
