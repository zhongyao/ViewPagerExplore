/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.hongri.viewpager.photoview.gestures;

/**
 * @author zhongyao
 */
public interface OnGestureListener {

    /**
     *
     * @param mMovedDistanceY 图片已经被拖拽的距离
     * @param dx
     * @param dy
     */
    void onDrag(float mMovedDistanceY, float dx, float dy);

    /**
     *
     * @param willExit 是否退出看图模式
     * @param mDragedDistanceY 松手后，图片需要自动回弹或者弹出(退出)的距离
     * @param dx
     */
    void onDragRelease(boolean willExit, float mDragedDistanceY, float dx);

    /**
     *
     * @param startX
     * @param startY
     * @param velocityX
     * @param velocityY
     */
    void onFling(float startX, float startY, float velocityX,
                 float velocityY);

    /**
     *
     * @param scaleFactor
     * @param focusX
     * @param focusY
     */
    void onScale(float scaleFactor, float focusX, float focusY);

    /**
     * 退出看图模式
     */
    void onExit();

}