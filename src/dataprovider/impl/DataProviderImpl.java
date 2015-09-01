package dataprovider.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dataprovider.data.ImageVO;


public class DataProviderImpl{

	private static List<ImageVO> listOfImages = new ArrayList<ImageVO>();
	private static List<String> imageTypes = new ArrayList<String>(Arrays.asList(new String[] { ".jpg", ".png" }));

	public static List<ImageVO> getImagesFromDirectory(String path) {
		File folder = new File(path);
		File[] allFies = folder.listFiles();
		if(allFies != null && allFies.length != 0){
			getImagesFiles(allFies);
			return listOfImages;
		}
		listOfImages.clear();
		return null;
	}

	public static void getImagesFiles(File[] allFies) {
		listOfImages.clear();
		int id = 1;
		for (File file : allFies) {
			if (file.isFile() && isImage(file.getName())) {
				listOfImages.add(new ImageVO(file.getName(),id,file.getPath()));
				id++;
			}
		}
	}


	private static boolean isImage(String fileName) {
		for (String type : imageTypes) {
			if(fileName.endsWith(type)){
				return true;
			}
		}
		return false;
	}

	public List<ImageVO> getImages(){
		return listOfImages;
	}
}
