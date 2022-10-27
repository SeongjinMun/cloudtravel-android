package com.example.cloudtravel.model;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;

public class S3Util {

    private String accessKey = "AKIAXR6CRQY4DH4SUJOJ";  // IAM AccessKey
    private String secretKey = "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";  // IAM SecretKey
    private Region region;          // S3 Region


    public S3Util() {
        region = Region.getRegion(Regions.AP_NORTHEAST_2);
    }

    public void uploadWithTransferUtility(
            Context context,
            String bucketName, File file,
            TransferListener listener
    ) {
        this.uploadWithTransferUtility(
                context,
                bucketName, null, file, null,
                listener
        );
    }


    public void uploadWithTransferUtility(
            Context context,
            String bucketName, String folder, File file,
            TransferListener listener
    ) {
        this.uploadWithTransferUtility(
                context,
                bucketName, folder, file, null,
                listener
        );
    }

    public void uploadWithTransferUtility(
            Context context,
            String bucketName, @Nullable String folder,
            File file, @Nullable String fileName,
            TransferListener listener
    ) {
        if (TextUtils.isEmpty(accessKey) || TextUtils.isEmpty(secretKey)) {
            throw new IllegalArgumentException(
                    "AccessKey & SecretKey must be not null"
            );
        }

        AWSCredentials awsCredentials = new BasicAWSCredentials(
                accessKey, secretKey
        );

        AmazonS3Client s3Client = new AmazonS3Client(
                awsCredentials, region
        );

        TransferUtility transferUtility = TransferUtility.builder()
                .s3Client(s3Client)
                .context(context)
                .build();

        TransferNetworkLossHandler.getInstance(context);

        TransferObserver uploadObserver = transferUtility.upload(
                (TextUtils.isEmpty(folder))
                        ? bucketName
                        : bucketName + "/" + folder,
                (TextUtils.isEmpty(fileName))
                        ? file.getName()
                        : fileName,
                file
        );

        uploadObserver.setTransferListener(listener);

    }

    public S3Util setKeys(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        return this;
    }

    public S3Util setAccessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public S3Util setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public S3Util setRegion(Regions regionName) {
        this.region = Region.getRegion(regionName);
        return this;
    }

    public S3Util setRegion(Region region) {
        this.region = region;
        return this;
    }

    public static S3Util getInstance() {
        return LHolder.instance;
    }

    private static class LHolder {
        private static final S3Util instance = new S3Util();
    }

}