package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import dataprovider.data.ImageVO;
import dataprovider.impl.DataProviderImpl;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import model.ImageSearchModel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ImageViewController {

	@FXML
	ImageView imageViewer;

	@FXML
	TableView<ImageVO> imagesTable;

	@FXML
	TableColumn<ImageVO, Integer> imageIdColumn;

	@FXML
	TableColumn<ImageVO, String> imageNameColumn;

	@FXML
	Button setDirectoryButton;

	@FXML
	Button previousButton;

	@FXML
	Button nextButton;

	@FXML
	Label imageNameLable;

	@FXML
	ScrollPane imageScrollPane;

	private final ImageSearchModel model = new ImageSearchModel();
	private ImageVO bookVO;

	public ImageViewController() {

	}

	@FXML
	private void initialize() {
		initializeResultTable();
		imagesTable.itemsProperty().bind(model.resultProperty());
	}

	private void initializeResultTable() {
		imageIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
		imageNameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
	}

	@FXML
	public void setDirectory(ActionEvent event) {
		File chooser = getDirectory();
		if (chooser != null) {
			setDirectoryAndAddImagesList(chooser.getPath());
		}
	}

	private File getDirectory() {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("JavaFX Projects");
		File defaultDirectory = new File("C:/Users/Public/Pictures/Sample Pictures");
		chooser.setInitialDirectory(defaultDirectory);
		return chooser.showDialog(null);
	}

	private void setDirectoryAndAddImagesList(String path) {
		Task<Collection<ImageVO>> backgroundTask = new Task<Collection<ImageVO>>() {
			@Override
			protected Collection<ImageVO> call() throws Exception {
				return DataProviderImpl.getImagesFromDirectory(path);
			}
		};
		backgroundTask.stateProperty().addListener(new ChangeListener<Worker.State>() {
			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
				if (newValue == State.SUCCEEDED) {
					imageViewer.setImage(null);
					model.setResult(new ArrayList<ImageVO>(backgroundTask.getValue()));
					imagesTable.getSortOrder().clear();
				}
			}
		});
		new Thread(backgroundTask).start();
	}


	@FXML
	public void displaySelectImage() {
		if (model.resultProperty().size() != 0) {
			bookVO = imagesTable.getSelectionModel().getSelectedItem();
			imageViewer.setImage(new Image(bookVO.getPath()));
			imageNameLable.setText(bookVO.getName());
		}
	}

	@FXML
	public void getSelectedImage(KeyEvent event) {
		getPreviusImageKye(event);
		getNextImageByKey(event);
	}

	private void getPreviusImageKye(KeyEvent event) {
		if ((event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.UP) && model.resultProperty().size() != 0) {
			if (1 == bookVO.getId()) {
				imagesTable.getSelectionModel().select(model.resultProperty().size());
				displaySelectImage();
				return;
			}
			imagesTable.getSelectionModel().select(bookVO.getId() - 2);
			displaySelectImage();
		}
	}

	private void getNextImageByKey(KeyEvent event) {
		if ((event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.DOWN) && model.resultProperty().size() != 0) {
			if (model.resultProperty().size() + 1 == bookVO.getId()) {
				imagesTable.getSelectionModel().select(0);
				displaySelectImage();
				return;
			}
			imagesTable.getSelectionModel().select(bookVO.getId());
			displaySelectImage();
		}
	}

	@FXML
	public void getNextImage() {
		if (model.resultProperty().size() != 0) {
			if (1 == bookVO.getId()) {
				imagesTable.getSelectionModel().select(0);
				displaySelectImage();
				return;
			}
			imagesTable.getSelectionModel().select(bookVO.getId());
			displaySelectImage();
		}
	}

	@FXML
	public void getPreviusImage() {
		if (model.resultProperty().size() != 0) {
			if (model.resultProperty().size() + 1 == bookVO.getId()) {
				imagesTable.getSelectionModel().select(model.resultProperty().size());
				displaySelectImage();
				return;
			}
			imagesTable.getSelectionModel().select(bookVO.getId()-2);
			displaySelectImage();
		}
	}

}
