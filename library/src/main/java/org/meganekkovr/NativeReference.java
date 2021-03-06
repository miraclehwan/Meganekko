package org.meganekkovr;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * This class handles native resource garbage collection.
 */
class NativeReference extends WeakReference<NativePointer> {

    private static final ReferenceQueue<NativePointer> sReferenceQueue = new ReferenceQueue<>();

    private long mNativePointer;

    NativeReference(NativePointer nativePointer) {
        super(nativePointer, sReferenceQueue);
        this.mNativePointer = nativePointer.get();
    }

    private static native void delete(long nativePointer);

    /**
     * Called from {@link org.meganekkovr.MeganekkoApp#update(FrameInput)}.
     */
    static void gc() {

        while (true) {
            Reference<? extends NativePointer> ref = NativeReference.sReferenceQueue.poll();
            if (ref == null) break;

            if (ref instanceof NativeReference) {
                ((NativeReference) ref).delete();
            }
        }
    }

    /**
     * Delete native pointer.
     */
    private void delete() {
        if (mNativePointer != 0) {
            delete(mNativePointer);
            mNativePointer = 0;
        }
    }
}