package com.example.demo.services.impl;

import java.io.IOException;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.config.AppConstant;
import com.example.demo.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    private Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String[] uploadImage(MultipartFile image) {

        if (image != null && !image.getOriginalFilename().isEmpty()) {

            // random id pour cloudinary id
            String filename = UUID.randomUUID().toString();

            // cloudinary upload image with form image select and id -> return cloud url of
            // the image on cloudinary
            try {
                byte[] data = new byte[image.getInputStream().available()];
                image.getInputStream().read(data);
                cloudinary.uploader().upload(data, ObjectUtils.asMap(
                        "public_id", filename));

                String fileurl = this.getUrlFromPublicId(filename);

                String[] imageData = new String[2];
                imageData[0] = filename;
                imageData[1] = fileurl;

                return imageData;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
     

    }

    @Override
    public String getUrlFromPublicId(String publicId) {

        return cloudinary
                .url()
                .transformation(
                        new Transformation<>()
                                .width(AppConstant.CONTACT_IMAGE_WIDTH)
                                .height(AppConstant.CONTACT_IMAGE_HEIGHT)
                                .crop(AppConstant.CONTACT_IMAGE_CROP))
                .generate(publicId);

    }

}
