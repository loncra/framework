package io.github.loncra.framework.minio.test;

import io.github.loncra.framework.commons.CacheProperties;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.TimeProperties;
import io.github.loncra.framework.commons.enumerate.NameEnum;
import io.github.loncra.framework.commons.minio.Bucket;
import io.github.loncra.framework.commons.minio.FileObject;
import io.github.loncra.framework.commons.minio.MoveFileObject;
import io.github.loncra.framework.minio.ConsoleApiMinioAsyncClient;
import io.github.loncra.framework.minio.MinioAsyncTemplate;
import io.minio.GetObjectResponse;
import io.minio.ListObjectsArgs;
import io.minio.ObjectWriteResponse;
import io.minio.Result;
import io.minio.messages.Item;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * minio 模版单元测试
 *
 * @author maurice.chen
 */
@SpringBootTest
public class MinioTemplateTest {

    public static final String DEFAULT_TEST_BUCKET = "minio.test";

    public static final String DEFAULT_TEST_FILE = "classpath:/787963DE-9662-4245-ABC7-8E6673F114F5.jpeg";

    private static final String BIG_FILE_PATH = "C:/Users/olale/OneDrive/图片/181_1673795222.mp4";

    @Autowired
    private MinioAsyncTemplate minioAsyncTemplate;

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    @AfterEach
    public void uninstall() throws Exception {
        Bucket bucket = Bucket.of(DEFAULT_TEST_BUCKET);

        if (minioAsyncTemplate.isBucketExist(bucket).get()) {

            Iterable<Result<Item>> iterable = minioAsyncTemplate.listObjects(ListObjectsArgs.builder().bucket(bucket.getBucketName()).build());

            for (Result<Item> r : iterable) {
                Item item = r.get();
                minioAsyncTemplate.deleteObject(FileObject.of(bucket, item.objectName()), false).get();
            }

            minioAsyncTemplate.deleteBucket(bucket);
        }
    }

    @Test
    void testMakeBucketIfNotExists() throws Exception {

        Bucket bucket = Bucket.of(DEFAULT_TEST_BUCKET);

        Assertions.assertTrue(minioAsyncTemplate.makeBucketIfNotExists(bucket).get());
        Assertions.assertFalse(minioAsyncTemplate.makeBucketIfNotExists(bucket).get());

        Map<String, Object> bucketInfo = minioAsyncTemplate.buckets(StringUtils.EMPTY).get();

        List<Map<String, Object>> buckets = CastUtils.cast(bucketInfo.get(ConsoleApiMinioAsyncClient.BUCKETS_API_NAME));
        Assertions.assertTrue(buckets.stream().anyMatch(m -> m.get(NameEnum.FIELD_NAME).equals(DEFAULT_TEST_BUCKET)));

        Map<String, Object> newBucket = minioAsyncTemplate.buckets(DEFAULT_TEST_BUCKET).get();
        Assertions.assertEquals(DEFAULT_TEST_BUCKET, newBucket.get(NameEnum.FIELD_NAME));

        try {
            minioAsyncTemplate.makeBucketIfNotExists(Bucket.of("Minio$$$Error.Test")).get();
        }
        catch (Exception e) {
            Assertions.assertTrue(IllegalArgumentException.class.isAssignableFrom(e.getClass()));
        }
    }

    @Test
    public void testDelete() throws Exception {
        Bucket bucket = Bucket.of(DEFAULT_TEST_BUCKET);
        minioAsyncTemplate.makeBucketIfNotExists(bucket).get();

        FileObject deleteFile = FileObject.of(bucket, "delete");
        Resource resource = resourceLoader.getResource(DEFAULT_TEST_FILE);
        minioAsyncTemplate.upload(
                deleteFile,
                resource.getInputStream(),
                resource.contentLength(),
                MediaType.IMAGE_JPEG_VALUE
        ).get();

        ListObjectsArgs listObjectsArgs = ListObjectsArgs
                .builder()
                .bucket(bucket.getBucketName())
                .prefix("delete")
                .build();

        Iterable<Result<Item>> iterable = minioAsyncTemplate.listObjects(listObjectsArgs);
        Assertions.assertEquals("delete", iterable.iterator().next().get().objectName());

        minioAsyncTemplate.deleteObject(deleteFile, false).get();
        iterable = minioAsyncTemplate.listObjects(listObjectsArgs);
        Assertions.assertFalse(iterable.iterator().hasNext());

        resource = resourceLoader.getResource(DEFAULT_TEST_FILE);
        minioAsyncTemplate.upload(
                FileObject.of(bucket, "folder/1"),
                resource.getInputStream(),
                resource.contentLength(),
                MediaType.IMAGE_JPEG_VALUE
        ).get();

        resource = resourceLoader.getResource(DEFAULT_TEST_FILE);
        minioAsyncTemplate.upload(
                FileObject.of(bucket, "folder/2"),
                resource.getInputStream(),
                resource.contentLength(),
                MediaType.IMAGE_JPEG_VALUE
        ).get();

        listObjectsArgs = ListObjectsArgs
                .builder()
                .bucket(bucket.getBucketName())
                .prefix("folder/")
                .build();

        iterable = minioAsyncTemplate.listObjects(listObjectsArgs);
        int i = 1;
        for (Result<Item> r : iterable) {
            Assertions.assertEquals(r.get().objectName(), "folder/" + (i++));
        }
        minioAsyncTemplate.deleteObject(FileObject.of(bucket, "folder/"), false).get();

        iterable = minioAsyncTemplate.listObjects(listObjectsArgs);
        Assertions.assertFalse(iterable.iterator().hasNext());

        resource = resourceLoader.getResource(DEFAULT_TEST_FILE);
        minioAsyncTemplate.upload(
                deleteFile,
                resource.getInputStream(),
                resource.contentLength(),
                MediaType.IMAGE_JPEG_VALUE
        ).get();

        listObjectsArgs = ListObjectsArgs
                .builder()
                .bucket(bucket.getBucketName())
                .prefix("delete")
                .build();

        iterable = minioAsyncTemplate.listObjects(listObjectsArgs);
        Assertions.assertEquals("delete", iterable.iterator().next().get().objectName());

        minioAsyncTemplate.deleteObject(deleteFile, true).get();
        Assertions.assertFalse(minioAsyncTemplate.isBucketExist(bucket).get());
    }

    @Test
    public void testDeleteBucket() throws Exception {
        Bucket bucket = Bucket.of(DEFAULT_TEST_BUCKET);
        minioAsyncTemplate.makeBucketIfNotExists(bucket).get();
        Map<String, Object> bucketInfo = minioAsyncTemplate.buckets(StringUtils.EMPTY).get();

        List<Map<String, Object>> buckets = CastUtils.cast(bucketInfo.get(ConsoleApiMinioAsyncClient.BUCKETS_API_NAME));
        Assertions.assertTrue(buckets.stream().anyMatch(m -> m.get(NameEnum.FIELD_NAME).equals(DEFAULT_TEST_BUCKET)));

        Assertions.assertTrue(minioAsyncTemplate.deleteBucket(bucket).get());

        Assertions.assertFalse(minioAsyncTemplate.deleteBucket(bucket).get());
    }

    @Test
    public void testMoveObject() throws Exception {
        Bucket bucket = Bucket.of(DEFAULT_TEST_BUCKET);
        FileObject moveFile = FileObject.of(bucket, "move");
        Resource resource = resourceLoader.getResource(DEFAULT_TEST_FILE);
        minioAsyncTemplate.upload(moveFile, resource.getInputStream(), resource.contentLength(), MediaType.IMAGE_JPEG_VALUE).get();

        GetObjectResponse getObjectResponse = minioAsyncTemplate.getObject(moveFile).get();

        Assertions.assertEquals("move", getObjectResponse.object());

        MoveFileObject fileObject = new MoveFileObject(moveFile, FileObject.of(bucket, "move_test/move"));

        minioAsyncTemplate.moveObject(fileObject).get();

        getObjectResponse = minioAsyncTemplate.getObject(fileObject.getTarget()).get();

        Assertions.assertEquals("move_test/move", getObjectResponse.object());
    }

    @Test
    void readAndWriteJsonValue() throws Exception {
        CacheProperties cacheProperties = new CacheProperties("minio:test:json", TimeProperties.ofDay(1));
        FileObject json = FileObject.of(DEFAULT_TEST_BUCKET, "json");
        ObjectWriteResponse response = minioAsyncTemplate.writeJsonValue(json, cacheProperties).get();

        ListObjectsArgs jsonArgs = ListObjectsArgs
                .builder()
                .bucket(json.getBucketName())
                .prefix("json")
                .build();

        Iterable<Result<Item>> iterable = minioAsyncTemplate.listObjects(jsonArgs);
        Assertions.assertEquals(iterable.iterator().next().get().objectName(), response.object());

        CacheProperties jsonValue = minioAsyncTemplate.readJsonValue(json, CacheProperties.class).get();

        Assertions.assertEquals(jsonValue.getName(), cacheProperties.getName());
        Assertions.assertEquals(jsonValue.getExpiresTime().getValue(), cacheProperties.getExpiresTime().getValue());
        Assertions.assertEquals(jsonValue.getExpiresTime().getUnit(), cacheProperties.getExpiresTime().getUnit());
    }

    @Test
    public void testUpload() throws IOException, ExecutionException, InterruptedException {
        Resource smallResource = resourceLoader.getResource(DEFAULT_TEST_FILE);
        FileObject small = FileObject.of(DEFAULT_TEST_BUCKET, "small.jpeg");
        minioAsyncTemplate.upload(small, smallResource.getInputStream(), smallResource.contentLength(), MediaType.IMAGE_JPEG_VALUE).get();
        GetObjectResponse smallObjectResponse = minioAsyncTemplate.getObject(small).get();
        Assertions.assertEquals(small.getObjectName(), smallObjectResponse.object());

        File file = new File(BIG_FILE_PATH);
        try (FileInputStream fileInputStream = new FileInputStream(BIG_FILE_PATH)) {
            FileObject big = FileObject.of(DEFAULT_TEST_BUCKET, "big.mp4");

            minioAsyncTemplate.upload(big, fileInputStream, file.length(), "video/mp4").get();
            GetObjectResponse bigObjectResponse = minioAsyncTemplate.getObject(big).get();
            Assertions.assertTrue(Strings.CS.equals(big.getObjectName(), bigObjectResponse.object()));
        }
    }

}
