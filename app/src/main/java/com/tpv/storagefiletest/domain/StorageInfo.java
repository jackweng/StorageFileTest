package com.tpv.storagefiletest.domain;

import android.os.UserHandle;

import com.tpv.storagefiletest.ui.PercentageBarChart.Entry;

/**
 * Created by Jack.Weng on 2017/4/20.
 */

public class StorageInfo {
    private int mStorageId;                 //StorageVolume.mStorageId
    private String mPath;                   //StorageVolume.mPath
    private String mUserLabel;              //StorageVolume.mDescription
    private boolean mPrimary;               //StorageVolume.mPrimary
    private boolean mRemovable;             //StorageVolume.mRemovable
    private boolean mEmulated;              //StorageVolume.mEmulated
    private long mMtpReserveSize;           //StorageVolume.mMtpReserveSize
    private boolean mAllowMassStorage;      //StorageVolume.mAllowMassStorage
    private long mMaxFileSize;              //StorageVolume.mMaxFileSize
    private UserHandle mOwner;              //StorageVolume.mOwner
    private String mUuid;                   //StorageVolume.mFsUuid
    private String mState;                  //StorageVolume.mState
    private long mMaxSize;
    private long mUsedSize;
    private long mFreeSize;
    private Entry mUsedSizeEntry;

    public int getStorageId() {
        return mStorageId;
    }

    public void setStorageId(int StorageId) {
        this.mStorageId = StorageId;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String Path) {
        this.mPath = Path;
    }

    public String getUserLabel() {
        return mUserLabel;
    }

    public void setUserLabel(String UserLabel) {
        this.mUserLabel = UserLabel;
    }

    public boolean isPrimary() {
        return mPrimary;
    }

    public void setPrimary(boolean Primary) {
        this.mPrimary = Primary;
    }

    public boolean isRemovable() {
        return mRemovable;
    }

    public void setRemovable(boolean Removable) {
        this.mRemovable = Removable;
    }

    public boolean isEmulated() {
        return mEmulated;
    }

    public void setEmulated(boolean Emulated) {
        this.mEmulated = Emulated;
    }

    public long getMtpReserveSize() {
        return mMtpReserveSize;
    }

    public void setMtpReserveSize(long MtpReserveSize) {
        this.mMtpReserveSize = MtpReserveSize;
    }

    public boolean isAllowMassStorage() {
        return mAllowMassStorage;
    }

    public void setAllowMassStorage(boolean AllowMassStorage) {
        this.mAllowMassStorage = AllowMassStorage;
    }

    public long getMaxFileSize() {
        return mMaxFileSize;
    }

    public void setMaxFileSize(long MaxFileSize) {
        this.mMaxFileSize = MaxFileSize;
    }

    public UserHandle getOwner() {
        return mOwner;
    }

    public void setOwner(UserHandle Owner) {
        this.mOwner = Owner;
    }

    public String getUuid() {
        return mUuid;
    }

    public void setUuid(String Uuid) {
        this.mUuid = Uuid;
    }

    public String getState() {
        return mState;
    }

    public void setState(String State) {
        this.mState = State;
    }

    public long getMaxSize() {
        return mMaxSize;
    }

    public void setMaxSize(long MaxSize) {
        this.mMaxSize = MaxSize;
    }

    public long getUsedSize() {
        return mUsedSize;
    }

    public void setUsedSize(long mUsedSize) {
        this.mUsedSize = mUsedSize;
    }

    public long getFreeSize() {
        return mFreeSize;
    }

    public void setFreeSize(long FreeSize) {
        this.mFreeSize = FreeSize;
    }

    public Entry getUsedSizeEntry() {
        return mUsedSizeEntry;
    }

    public void setUsedSizeEntry(Entry FreeSizeEntry) {
        this.mUsedSizeEntry = FreeSizeEntry;
    }

    @Override
    public String toString() {
        return getUserLabel() + ":\n"
                + "StorageId = " + getStorageId() + ";\n"
                + "Path = " + getPath() + ";\n"
                + "isPrimary = " + isPrimary() + ";\n"
                + "isRemovable = " + isRemovable() + ";\n"
                + "isEmulated = " + isEmulated() + ";\n"
                + "MtpReserveSize = " + getMtpReserveSize() + ";\n"
                + "isAllowMassStorage = " + isAllowMassStorage() + ";\n"
                + "MaxFileSize = " + getMaxFileSize() + ";\n"
                + "Owner = " + getOwner() + ";\n"
                + "Uuid = " + getUuid() + ";\n"
                + "State = " + getState() + ";\n"
                + "MaxSize = " + getMaxSize() + ";\n"
                + "UsedSize = " + getUsedSize() + ";\n"
                + "FreeSize = " + getFreeSize() + ";\n"
                + "UsedSizeEntry = " + getUsedSizeEntry() + ";";
    }
}
