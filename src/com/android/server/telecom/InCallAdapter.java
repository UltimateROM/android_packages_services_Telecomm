/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.server.telecom;

import android.os.Binder;
import android.telecom.PhoneAccountHandle;

import com.android.internal.telecom.IInCallAdapter;

/**
 * Receives call commands and updates from in-call app and passes them through to CallsManager.
 * {@link InCallController} creates an instance of this class and passes it to the in-call app after
 * binding to it. This adapter can receive commands and updates until the in-call app is unbound.
 */
class InCallAdapter extends IInCallAdapter.Stub {
    private final CallsManager mCallsManager;
    private final CallIdMapper mCallIdMapper;
    private final TelecomSystem.SyncRoot mLock;

    /** Persists the specified parameters. */
    public InCallAdapter(CallsManager callsManager, CallIdMapper callIdMapper,
            TelecomSystem.SyncRoot lock) {
        mCallsManager = callsManager;
        mCallIdMapper = callIdMapper;
        mLock = lock;
    }

    @Override
    public void answerCall(String callId, int videoState) {
        try {
            Log.startSession("ICA.aC");
            long token = Binder.clearCallingIdentity();
            try {
                synchronized (mLock) {
                    Log.d(this, "answerCall(%s,%d)", callId, videoState);
                    Call call = mCallIdMapper.getCall(callId);
                    if (call != null) {
                        mCallsManager.answerCall(call, videoState);
                    } else {
                        Log.w(this, "answerCall, unknown call id: %s", callId);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        } finally {
            Log.endSession();
        }
    }

    @Override
    public void rejectCall(String callId, boolean rejectWithMessage, String textMessage) {
        try {
            Log.startSession("ICA.aC");
            long token = Binder.clearCallingIdentity();
            try {
                synchronized (mLock) {
                    Log.d(this, "rejectCall(%s,%b,%s)", callId, rejectWithMessage, textMessage);
                    Call call = mCallIdMapper.getCall(callId);
                    if (call != null) {
                        mCallsManager.rejectCall(call, rejectWithMessage, textMessage);
                    } else {
                        Log.w(this, "setRingback, unknown call id: %s", callId);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        } finally {
            Log.endSession();
        }
    }

    @Override
    public void playDtmfTone(String callId, char digit) {
        try {
            Log.startSession("ICA.pDT");
            long token = Binder.clearCallingIdentity();
            try {
                synchronized (mLock) {
                    Log.d(this, "playDtmfTone(%s,%c)", callId, digit);
                    Call call = mCallIdMapper.getCall(callId);
                    if (call != null) {
                        mCallsManager.playDtmfTone(call, digit);
                    } else {
                        Log.w(this, "playDtmfTone, unknown call id: %s", callId);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        } finally {
            Log.endSession();
        }
    }

    @Override
    public void stopDtmfTone(String callId) {
        try {
            Log.startSession("ICA.sDT");
            long token = Binder.clearCallingIdentity();
            try {
                synchronized (mLock) {
                    Log.d(this, "stopDtmfTone(%s)", callId);
                    Call call = mCallIdMapper.getCall(callId);
                    if (call != null) {
                        mCallsManager.stopDtmfTone(call);
                    } else {
                        Log.w(this, "stopDtmfTone, unknown call id: %s", callId);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        } finally {
            Log.endSession();
        }
    }

    @Override
    public void postDialContinue(String callId, boolean proceed) {
        try {
            Log.startSession("ICA.pDC");
            long token = Binder.clearCallingIdentity();
            try {
                synchronized (mLock) {
                    Log.d(this, "postDialContinue(%s)", callId);
                    Call call = mCallIdMapper.getCall(callId);
                    if (call != null) {
                        mCallsManager.postDialContinue(call, proceed);
                    } else {
                        Log.w(this, "postDialContinue, unknown call id: %s", callId);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        } finally {
            Log.endSession();
        }
    }

    @Override
    public void disconnectCall(String callId) {
        try {
            Log.startSession("ICA.dC");
            long token = Binder.clearCallingIdentity();
            try {
                synchronized (mLock) {
                    Log.v(this, "disconnectCall: %s", callId);
                    Call call = mCallIdMapper.getCall(callId);
                    if (call != null) {
                        mCallsManager.disconnectCall(call);
                    } else {
                        Log.w(this, "disconnectCall, unknown call id: %s", callId);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        } finally {
            Log.endSession();
        }
    }

    @Override
    public void holdCall(String callId) {
        try {
            Log.startSession("ICA.hC");
            long token = Binder.clearCallingIdentity();
            try {
                synchronized (mLock) {
                    Call call = mCallIdMapper.getCall(callId);
                    if (call != null) {
                        mCallsManager.holdCall(call);
                    } else {
                        Log.w(this, "holdCall, unknown call id: %s", callId);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        } finally {
            Log.endSession();
        }
    }

    @Override
    public void unholdCall(String callId) {
        try {
            Log.startSession("ICA.uC");
            long token = Binder.clearCallingIdentity();
            try {
                synchronized (mLock) {
                    Call call = mCallIdMapper.getCall(callId);
                    if (call != null) {
                        mCallsManager.unholdCall(call);
                    } else {
                        Log.w(this, "unholdCall, unknown call id: %s", callId);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        } finally {
            Log.endSession();
        }
    }

    @Override
    public void phoneAccountSelected(String callId, PhoneAccountHandle accountHandle,
            boolean setDefault) {
        try {
            Log.startSession("ICA.pAS");
            long token = Binder.clearCallingIdentity();
            try {
                synchronized (mLock) {
                    Call call = mCallIdMapper.getCall(callId);
                    if (call != null) {
                        mCallsManager.phoneAccountSelected(call, accountHandle, setDefault);
                    } else {
                        Log.w(this, "phoneAccountSelected, unknown call id: %s", callId);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        } finally {
            Log.endSession();
        }
    }

    @Override
    public void mute(boolean shouldMute) {
        try {
            Log.startSession("ICA.m");
            long token = Binder.clearCallingIdentity();
            try {
                synchronized (mLock) {
                    mCallsManager.mute(shouldMute);
                }
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        } finally {
            Log.endSession();
        }
    }

    @Override
    public void setAudioRoute(int route) {
        try {
            Log.startSession("ICA.sAR");
            long token = Binder.clearCallingIdentity();
            try {
                synchronized (mLock) {
                    mCallsManager.setAudioRoute(route);
                }
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        } finally {
            Log.endSession();
        }
    }

    @Override
    public void conference(String callId, String otherCallId) {
        try {
            Log.startSession("ICA.c");
            long token = Binder.clearCallingIdentity();
            try {
                synchronized (mLock) {
                    Call call = mCallIdMapper.getCall(callId);
                    Call otherCall = mCallIdMapper.getCall(otherCallId);
                    if (call != null && otherCall != null) {
                        mCallsManager.conference(call, otherCall);
                    } else {
                        Log.w(this, "conference, unknown call id: %s or %s", callId, otherCallId);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        } finally {
            Log.endSession();
        }
    }

    @Override
    public void splitFromConference(String callId) {
        try {
            Log.startSession("ICA.sFC");
            long token = Binder.clearCallingIdentity();
            try {
                synchronized (mLock) {
                    Call call = mCallIdMapper.getCall(callId);
                    if (call != null) {
                        call.splitFromConference();
                    } else {
                        Log.w(this, "splitFromConference, unknown call id: %s", callId);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        } finally {
            Log.endSession();
        }
    }

    @Override
    public void mergeConference(String callId) {
        try {
            Log.startSession("ICA.mC");
            long token = Binder.clearCallingIdentity();
            try {
                synchronized (mLock) {
                    Call call = mCallIdMapper.getCall(callId);
                    if (call != null) {
                        call.mergeConference();
                    } else {
                        Log.w(this, "mergeConference, unknown call id: %s", callId);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        } finally {
            Log.endSession();
        }
    }

    @Override
    public void swapConference(String callId) {
        try {
            Log.startSession("ICA.sC");
            long token = Binder.clearCallingIdentity();
            try {
                synchronized (mLock) {
                    Call call = mCallIdMapper.getCall(callId);
                    if (call != null) {
                        call.swapConference();
                    } else {
                        Log.w(this, "swapConference, unknown call id: %s", callId);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        } finally {
            Log.endSession();
        }
    }

    @Override
    public void turnOnProximitySensor() {
        try {
            Log.startSession("tOPS");
            long token = Binder.clearCallingIdentity();
            try {
                synchronized (mLock) {
                    mCallsManager.turnOnProximitySensor();
                }
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        } finally {
            Log.endSession();
        }
    }

    @Override
    public void turnOffProximitySensor(boolean screenOnImmediately) {
        try {
            Log.startSession("ICA.tOPS");
            long token = Binder.clearCallingIdentity();
            try {
                synchronized (mLock) {
                    mCallsManager.turnOffProximitySensor(screenOnImmediately);
                }
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        } finally {
             Log.endSession();
        }
    }
}
