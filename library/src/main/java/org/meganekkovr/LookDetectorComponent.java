package org.meganekkovr;

import android.support.annotation.NonNull;

/**
 * This notifies whether user is looking at {@link Entity} or not.
 */
public class LookDetectorComponent extends Component {

    private final LookDetector lookDetector = LookDetector.getInstance();
    private final LookListener lookListener;
    private boolean looking;

    public LookDetectorComponent(LookListener lookListener) {
        this.lookListener = lookListener;
    }

    @Override
    public void update(@NonNull FrameInput frame) {

        Entity entity = getEntity();
        boolean isLookingNow = lookDetector.isLookingAt(entity);

        if (isLookingNow) {

            // Start looking
            if (!looking) {
                lookListener.onLookStart(entity, frame);
            }

            lookListener.onLooking(entity, frame);

        } else {

            // Stop looking
            if (looking) {
                lookListener.onLookEnd(entity, frame);
            }
        }

        this.looking = isLookingNow;
        super.update(frame);
    }

    public interface LookListener {

        /**
         * Called when user starts looking at target {@link Entity}.
         *
         * @param entity Entity
         * @param frame  Frame information
         */
        void onLookStart(@NonNull Entity entity, @NonNull FrameInput frame);

        /**
         * Called when user stops looking at target {@link Entity}.
         *
         * @param entity Entity
         * @param frame  Frame information
         */
        void onLookEnd(@NonNull Entity entity, @NonNull FrameInput frame);

        /**
         * Called when user is looking at target {@link Entity} in every frame update.
         *
         * @param entity Entity
         * @param frame  Frame information
         */
        void onLooking(@NonNull Entity entity, @NonNull FrameInput frame);
    }
}
