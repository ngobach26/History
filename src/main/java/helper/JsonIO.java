package helper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.HistoricalEntity;

public class JsonIO<T extends HistoricalEntity>{
	private final Type TYPE;   //the type of objects to be deserialized from Json
	
	public JsonIO(Type type) {
		this.TYPE = type;
	}
	
	public void writeJson(List<T> list, String path) {
		try (FileWriter fileWriter = new FileWriter(path)){
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			fileWriter.write(gson.toJson(list));
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public List<T> loadJson(String path) {
		List<T> list = null;
		try (FileReader fileReader = new FileReader(path)){
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			list = gson.fromJson(fileReader, TYPE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
