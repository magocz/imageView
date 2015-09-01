package imageFinderTest;

import org.junit.Assert;
import org.junit.Test;

import dataprovider.impl.DataProviderImpl;

public class DataProviderTestImpl {
	@Test
	public void shouldGetTwoImages(){
		// given
		String path = "../ImageViewer/testResources";
		DataProviderImpl finder = new DataProviderImpl();
		 // when
		DataProviderImpl.getImagesFromDirectory(path);
		// then
		Assert.assertEquals(2, finder.getImages().size());

	}
}
