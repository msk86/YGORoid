package org.msk86.ygoroid.newcore.impl;

import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newcore.BmpGenerator;
import org.msk86.ygoroid.newcore.Bmpable;
import org.msk86.ygoroid.newcore.constant.Const;
import org.msk86.ygoroid.newcore.impl.bmp.LinkMarkerGenerator;

public class LinkMarker implements Bmpable {

    private static int[] MARKER_POSITIONS = new int[] {Const.LINK_DIRECTION_NORTH, Const.LINK_DIRECTION_NORTHEAST, Const.LINK_DIRECTION_EAST,
            Const.LINK_DIRECTION_SOUTHEAST, Const.LINK_DIRECTION_SOUTH, Const.LINK_DIRECTION_SOUTHWEST, Const.LINK_DIRECTION_WEST,
            Const.LINK_DIRECTION_NORTHWEST};

    private int markerInt;
    private int[] markers;

    private BmpGenerator generator;

    public LinkMarker(int link, int markerInt) {
        this.markerInt = markerInt;

        int[] markerResources = new int[] {R.raw.card_link_marker_on_n, R.raw.card_link_marker_on_ne, R.raw.card_link_marker_on_e,
                R.raw.card_link_marker_on_se, R.raw.card_link_marker_on_s, R.raw.card_link_marker_on_sw, R.raw.card_link_marker_on_w,
                R.raw.card_link_marker_on_nw};

        this.markers = new int[link];
        int i = 0;

        for (int rotation = 0; rotation < MARKER_POSITIONS.length; rotation ++) {
            int markerPosition = MARKER_POSITIONS[rotation];
            if((markerInt & markerPosition) > 0) {
                this.markers[i++] = markerResources[rotation];
            }
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int markPos : MARKER_POSITIONS) {
            switch (markerInt & markPos) {
                case Const.LINK_DIRECTION_NORTH:
                    result.append("↑");
                    break;
                case Const.LINK_DIRECTION_NORTHEAST:
                    result.append("↗");
                    break;
                case Const.LINK_DIRECTION_EAST:
                    result.append("→");
                    break;
                case Const.LINK_DIRECTION_SOUTHEAST:
                    result.append("↘");
                    break;
                case Const.LINK_DIRECTION_SOUTH:
                    result.append("↓");
                    break;
                case Const.LINK_DIRECTION_SOUTHWEST:
                    result.append("↙");
                    break;
                case Const.LINK_DIRECTION_WEST:
                    result.append("←");
                    break;
                case Const.LINK_DIRECTION_NORTHWEST:
                    result.append("↖");
                    break;
            }
        }
        return result.toString();
    }

    @Override
    public BmpGenerator getBmpGenerator() {
        if(generator == null) {
            generator = new LinkMarkerGenerator(markerInt, markers);
        }
        return generator;
    }
}
