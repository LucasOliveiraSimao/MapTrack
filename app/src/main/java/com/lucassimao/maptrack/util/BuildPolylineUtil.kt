package com.lucassimao.maptrack.util

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.lucassimao.maptrack.util.Constants.ROUTE_LINE_COLOR
import com.lucassimao.maptrack.util.Constants.ROUTE_LINE_WIDTH

fun buildPolylineOption(
    preLastPosition: LatLng,
    lastPosition: LatLng
): PolylineOptions {
    return PolylineOptions()
        .color(ROUTE_LINE_COLOR)
        .width(ROUTE_LINE_WIDTH)
        .add(preLastPosition)
        .add(lastPosition)
}

fun buildPolylineOption(
    path: List<LatLng>
): PolylineOptions {
    return PolylineOptions()
        .color(ROUTE_LINE_COLOR)
        .width(ROUTE_LINE_WIDTH)
        .addAll(path)
}