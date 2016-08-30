package org.meganekkovr.xml;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.meganekkovr.Entity;
import org.w3c.dom.Node;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * To add custom primitive, use {@code XmlPrimitiveFactory.getInstance().install(new YourPrimitiveHandler())}.
 */
public class XmlPrimitiveFactory {

    static {
        XmlPrimitiveFactory.getInstance().install(new DefautPrimitive());
    }

    public interface XmlPrimitiveHandler {
        /**
         * Create {@link Entity} from {@link Node}.
         * Typically, this checks {@link Node#getNodeName()} and create instance.
         *
         * @param node    Node
         * @param context Context
         * @return Created entity or {@code null} if this handler does not support passed node.
         */
        @Nullable
        Entity createEntity(@NonNull Node node, @NonNull Context context);
    }

    // singleton
    private static XmlPrimitiveFactory instance;

    public static synchronized XmlPrimitiveFactory getInstance() {
        if (instance == null) {
            instance = new XmlPrimitiveFactory();
        }
        return instance;
    }

    private final Set<XmlPrimitiveHandler> handlers = new CopyOnWriteArraySet<>();

    private XmlPrimitiveFactory() {
    }

    Entity parse(Node node, Context context) {

        for (XmlPrimitiveHandler handler : handlers) {
            Entity entity = handler.createEntity(node, context);
            if (entity != null) return entity;
        }

        return null;
    }

    public void install(@NonNull XmlPrimitiveHandler handler) {
        handlers.add(handler);
    }
}
