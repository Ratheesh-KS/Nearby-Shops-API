package org.nearbyshops.RESTEndpoints;

import net.coobird.thumbnailator.Thumbnails;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.nearbyshops.DAOs.DAOImages.ItemImagesDAO;
import org.nearbyshops.Globals.GlobalConstants;
import org.nearbyshops.Globals.Globals;
import org.nearbyshops.Model.Image;
import org.nearbyshops.Model.ItemImage;
import org.nearbyshops.Model.ModelEndpoint.ItemImageEndPoint;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * Created by sumeet on 28/2/17.
 */




@Path("/api/v1/ItemImage")
public class ItemImageResource {

    private ItemImagesDAO itemImagesDAO = Globals.itemImagesDAO;


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_ADMIN, GlobalConstants.ROLE_STAFF,GlobalConstants.ROLE_SHOP_ADMIN})
    public Response createItemImage(ItemImage itemImage)
    {
        int idOfInsertedRow = -1;



        idOfInsertedRow = itemImagesDAO.saveItemImage(itemImage,false);



        if(idOfInsertedRow >=1)
        {
            itemImage.setImageID(idOfInsertedRow);

            return Response.status(Response.Status.CREATED)
                    .location(URI.create("/api/v1/ItemImage/" + idOfInsertedRow))
                    .entity(itemImage)
                    .build();

        }else {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .entity(null)
                    .build();
        }

    }






    @PUT
    @Path("/{ImageID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_ADMIN, GlobalConstants.ROLE_STAFF})
    public Response updateItemImage(ItemImage itemImage, @PathParam("ImageID")int imageID)
    {




        itemImage.setImageID(imageID);
        int rowCount = itemImagesDAO.updateItemImage(itemImage);



        if(rowCount >= 1)
        {

            return Response.status(Response.Status.OK)
                    .build();
        }
        if(rowCount == 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

        return null;
    }





    @DELETE
    @Path("/{ImageID}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({GlobalConstants.ROLE_ADMIN, GlobalConstants.ROLE_STAFF})
    public Response deleteImage(@PathParam("ImageID")int imageID)
    {


        ItemImage itemImage = itemImagesDAO.getItemImageForItemID(imageID);


        int rowCount = itemImagesDAO.deleteItemImage(itemImage.getImageID());




        if(rowCount >= 1)
        {
            deleteImageFileInternal(itemImage.getImageFilename());
        }



        if(rowCount>=1)
        {

            return Response.status(Response.Status.OK)
                    .build();
        }

        if(rowCount == 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }

        return null;
    }






    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemImages(
            @QueryParam("ItemID")Integer itemID,
            @QueryParam("SortBy") String sortBy,
            @QueryParam("Limit")Integer limit, @QueryParam("Offset")int offset,
            @QueryParam("metadata_only")Boolean metaonly)
    {



        if(limit!=null && limit >= GlobalConstants.max_limit)
        {
            limit = GlobalConstants.max_limit;
        }




        ItemImageEndPoint endPoint = itemImagesDAO.getEndPointMetadata(itemID);


        List<ItemImage> list = null;


        if(metaonly==null || (!metaonly)) {

            list =
                    itemImagesDAO.getItemImages(
                            itemID,
                            sortBy,limit,offset
                    );

            endPoint.setResults(list);
        }




        if(limit!=null)
        {
            endPoint.setLimit(limit);
            endPoint.setOffset(offset);
            endPoint.setMax_limit(GlobalConstants.max_limit);
        }



//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}


        //Marker

        return Response.status(Response.Status.OK)
                .entity(endPoint)
                .build();
    }









    // Image Utility Methods
    public static void deleteImageFileInternal(String fileName)
    {
        boolean deleteStatus = false;

//        System.out.println("Filename: " + fileName);

        try {

            //Files.delete(BASE_DIR.resolve(fileName));
            deleteStatus = Files.deleteIfExists(BASE_DIR.resolve(fileName));

            // delete thumbnails
            Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + fileName + ".jpg"));
            Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + fileName + ".jpg"));
            Files.deleteIfExists(BASE_DIR.resolve("seven_hundred_" + fileName + ".jpg"));
            Files.deleteIfExists(BASE_DIR.resolve("nine_hundred_" + fileName + ".jpg"));


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }





    public static String saveNewImage(String serviceURL,String imageID)
    {
        try
        {
            serviceURL = serviceURL + "/api/v1/ItemImage/Image/" + imageID;

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(serviceURL)
                    .build();

            okhttp3.Response response = null;
            response = client.newCall(request).execute();
//			response.body().byteStream();
//			System.out.println();

            return uploadNewImage(response.body().byteStream());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }




    public static String uploadNewImage(InputStream in)
    {

        File theDir = new File(BASE_DIR.toString());

        // if the directory does not exist, create it
        if (!theDir.exists()) {

//            System.out.println("Creating directory: " + BASE_DIR.toString());

            boolean result = false;

            try{
                theDir.mkdir();
                result = true;
            }
            catch(Exception se){
                //handle it
            }
            if(result) {
//                System.out.println("DIR created");
            }
        }



        String fileName = "" + System.currentTimeMillis();


        try {

            // Copy the file to its location.
            long filesize = 0;

            filesize = Files.copy(in, BASE_DIR.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            if(filesize > MAX_IMAGE_SIZE_MB * 1048 * 1024)
            {
                // delete file if it exceeds the file size limit
                Files.deleteIfExists(BASE_DIR.resolve(fileName));
                return null;
            }

            createThumbnails(fileName);

            Image image = new Image();
            image.setPath(fileName);

            // Return a 201 Created response with the appropriate Location header.

        }
        catch (IOException e) {
            e.printStackTrace();

            return null;
        }

        return fileName;
    }










    // Image MEthods

    private static final java.nio.file.Path BASE_DIR = Paths.get("./data/images/ItemImages");
    private static final double MAX_IMAGE_SIZE_MB = 2;


    @POST
    @Path("/Image")
    @Consumes({MediaType.APPLICATION_OCTET_STREAM})
    @RolesAllowed({GlobalConstants.ROLE_ADMIN, GlobalConstants.ROLE_STAFF, GlobalConstants.ROLE_SHOP_ADMIN})
    public Response uploadImage(InputStream in, @HeaderParam("Content-Length") long fileSize,
                                @QueryParam("PreviousImageName") String previousImageName
    ) throws Exception
    {


        if(previousImageName!=null)
        {
            Files.deleteIfExists(BASE_DIR.resolve(previousImageName));
            Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + previousImageName + ".jpg"));
            Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + previousImageName + ".jpg"));
            Files.deleteIfExists(BASE_DIR.resolve("seven_hundred_" + previousImageName + ".jpg"));
            Files.deleteIfExists(BASE_DIR.resolve("nine_hundred_" + previousImageName + ".jpg"));
        }


        File theDir = new File(BASE_DIR.toString());

        // if the directory does not exist, create it
        if (!theDir.exists()) {

//            System.out.println("Creating directory: " + BASE_DIR.toString());

            boolean result = false;

            try{
                theDir.mkdir();
                result = true;
            }
            catch(Exception se){
                //handle it
            }
            if(result) {
//                System.out.println("DIR created");
            }
        }



        String fileName = "" + System.currentTimeMillis();

        // Copy the file to its location.
        long filesize = Files.copy(in, BASE_DIR.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

        if(filesize > MAX_IMAGE_SIZE_MB * 1048 * 1024)
        {
            // delete file if it exceeds the file size limit
            Files.deleteIfExists(BASE_DIR.resolve(fileName));

            return Response.status(Response.Status.EXPECTATION_FAILED).build();
        }


        createThumbnails(fileName);


        Image image = new Image();
        image.setPath(fileName);

        // Return a 201 Created response with the appropriate Location header.

        return Response.status(Response.Status.CREATED).location(URI.create("/api/Images/" + fileName)).entity(image).build();
    }





    private static void createThumbnails(String filename)
    {
        try {

            Thumbnails.of(BASE_DIR.toString() + "/" + filename)
                    .size(300,300)
                    .outputFormat("jpg")
                    .toFile(new File(BASE_DIR.toString() + "/" + "three_hundred_" + filename));



            Thumbnails.of(BASE_DIR.toString() + "/" + filename)
                    .size(500,500)
                    .outputFormat("jpg")
                    .toFile(new File(BASE_DIR.toString() + "/" + "five_hundred_" + filename));


            Thumbnails.of(BASE_DIR.toString() + "/" + filename)
                    .size(700,700)
                    .outputFormat("jpg")
                    .toFile(new File(BASE_DIR.toString() + "/" + "seven_hundred_" + filename));


            Thumbnails.of(BASE_DIR.toString() + "/" + filename)
                    .size(900,900)
                    .outputFormat("jpg")
                    .toFile(new File(BASE_DIR.toString() + "/" + "nine_hundred_" + filename));



        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @GET
    @Path("/Image/{name}")
    @Produces("image/jpeg")
    public InputStream getImage(@PathParam("name") String fileName) {

        //fileName += ".jpg";
        java.nio.file.Path dest = BASE_DIR.resolve(fileName);

        if (!Files.exists(dest)) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }


        try {
            return Files.newInputStream(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    @DELETE
    @Path("/Image/{name}")
    @RolesAllowed({GlobalConstants.ROLE_ADMIN, GlobalConstants.ROLE_STAFF, GlobalConstants.ROLE_SHOP_ADMIN, GlobalConstants.ROLE_SHOP_STAFF})
    public Response deleteImageFile(@PathParam("name")String fileName)
    {


        boolean deleteStatus = false;
        Response response;
//        System.out.println("Filename: " + fileName);

        try {


            //Files.delete(BASE_DIR.resolve(fileName));
            deleteStatus = Files.deleteIfExists(BASE_DIR.resolve(fileName));

            // delete thumbnails
            Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + fileName + ".jpg"));
            Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + fileName + ".jpg"));
            Files.deleteIfExists(BASE_DIR.resolve("seven_hundred_" + fileName + ".jpg"));
            Files.deleteIfExists(BASE_DIR.resolve("nine_hundred_" + fileName + ".jpg"));


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        if(!deleteStatus)
        {
            response = Response.status(Response.Status.NOT_MODIFIED).build();

        }else
        {
            response = Response.status(Response.Status.OK).build();
        }

        return response;
    }



}
