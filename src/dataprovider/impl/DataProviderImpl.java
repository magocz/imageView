package dataprovider.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dataprovider.data.ImageVO;


/*
 * REV: ta klasa powinna implementowac interfejs DataPrivider/DataProvider
 */
public class DataProviderImpl{

	/*
	 * REV: te pola nie powinny byc statyczne
	 */
	private static List<ImageVO> listOfImages = new ArrayList<ImageVO>();
	/*
	 * REV: co jesli roszerzenie wyglada tak 'jPg'
	 */
	private static List<String> imageTypes = new ArrayList<String>(Arrays.asList(new String[] { ".jpg", ".png" }));

	/*
	 * REV: metody w tej klasie nie powinny byc statyczne
	 */

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
		/*
		 * REV: lepiej uzyc FilenameFilter
		 */
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
