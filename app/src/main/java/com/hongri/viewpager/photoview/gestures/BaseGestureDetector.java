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

import android.content.Context;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import com.hongri.viewpager.photoview.PhotoViewAttacher;
import com.hongri.viewpager.photoview.log.LogManager;

/**
 * @author zhongyao
 */
public class BaseGestureDetector implements GestureDetector {

    protected OnGestureListener mListener;
    private static final String TAG = BaseGestureDetector.class.getSimpleName();
    float mLastTouchX;
    float mLastTouchY;
    float mBeginningTouchPointY;
    float mDragedDistanceY;
    float mEnddingTouchPointY;
    final float mTouchSlop;
    final float mMinimumVelocity;

    @Override
    public void setOnGestureListener(OnGestureListener listener) {
        this.mListener = listener;
    }

    public BaseGestureDetector(Context context) {
        final ViewConfiguration configuration = ViewConfiguration
            .get(context);
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    private VelocityTracker mVelocityTracker;
    private boolean mIsDragging;

    float getActiveX(MotionEvent ev) {
        return ev.getX();
    }

    float getActiveY(MotionEvent ev) {
        return ev.getY();
    }

    @Override
    public boolean isScaling() {
        return false;
    }

    @Override
    public boolean isDragging() {
        return mIsDragging;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mVelocityTracker = VelocityTracker.obtain();
                if (null != mVelocityTracker) {
                    mVelocityTracker.addMovement(ev);
                } else {
                    LogManager.getLogger().i(TAG, "Velocity tracker is null");
                }

                mLastTouchX = getActiveX(ev);
                mLastTouchY = getActiveY(ev);

                mBeginningTouchPointY = getActiveY(ev);

                mIsDragging = false;
                break;
            }

            //moto 2368 * 1440
            case MotionEvent.ACTION_MOVE: {
                final float x = getActiveX(ev);
                final float y = getActiveY(ev);
                final float dx = x - mLastTouchX, dy = y - mLastTouchY;
                mDragedDistanceY = Math.abs(y - mBeginningTouchPointY);

                if (!mIsDragging) {
                    mIsDragging = Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
                }

                if (mIsDragging) {
                    mListener.onDrag(mDragedDistanceY, dx, dy);
                    mLastTouchX = x;
                    mLastTouchY = y;

                    if (null != mVelocityTracker) {
                        mVelocityTracker.addMovement(ev);
                    }
                }
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                // Recycle Velocity Tracker
                if (null != mVelocityTracker) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
            }

            case MotionEvent.ACTION_UP: {
                if (mIsDragging) {
                    if (null != mVelocityTracker) {
                        mLastTouchX = getActiveX(ev);
                        mLastTouchY = getActiveY(ev);

                        mEnddingTouchPointY = getActiveY(ev);

                        mDragedDistanceY = mEnddingTouchPointY - mBeginningTouchPointY;
                        if (Math.abs(mDragedDistanceY) > PhotoViewAttacher.ALPHA_CHANGE_TIME) {
                            if (mDragedDistanceY > 0) {
                                //下滑
                                mListener.onDragRelease(true, PhotoViewAttacher.SCREEM_HEIGHT - mDragedDistanceY, 0);
                            } else {
                                //上滑
                                mListener.onDragRelease(true, -(PhotoViewAttacher.SCREEM_HEIGHT + mDragedDistanceY), 0);
                            }
                        } else {
                            mListener.onDragRelease(false, -mDragedDistanceY, 0);
                        }
                        // Compute velocity within the last 1000ms
                        mVelocityTracker.addMovement(ev);
                        mVelocityTracker.computeCurrentVelocity(1000);

                        final float vX = mVelocityTracker.getXVelocity(), vY = mVelocityTracker
                            .getYVelocity();

                        // If the velocity is greater than minVelocity, call
                        // listener
                        if (Math.max(Math.abs(vX), Math.abs(vY)) >= mMinimumVelocity) {
                            mListener.onFling(mLastTouchX, mLastTouchY, -vX,
                                -vY);
                        }
                    }
                }

                // Recycle Velocity Tracker
                if (null != mVelocityTracker) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
            }

            default:
                break;
        }

        return true;
    }
}
