package helper;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonIO<T>{
	private final Type TYPE;   //the type of objects to be deserialized from Json
	FileWriter fileWriter;
	FileReader fileReader;
	
	public JsonIO(Type type) {
		this.TYPE = type;
	}
	
	public void writeJson(List<T> list, String path) {
		try{
			fileWriter = new FileWriter(path);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			fileWriter.write(gson.toJson(list));
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (fileWriter != null) {
					fileWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Tải dữ liệu từ tệp JSON được chỉ định và trả về danh sách các đối tượng.
	 *
	 * @param path đường dẫn đến tệp JSON
	 * @return danh sách các đối tượng được tải từ tệp JSON
	 * @throws IOException nếu xảy ra lỗi khi đọc tệp JSON
	 *
	 * @apiNote Phương thức này sử dụng thư viện Gson để đọc dữ liệu từ tệp JSON và chuyển đổi thành danh sách các đối tượng. Tệp JSON cần tuân theo cấu trúc dữ liệu phù hợp với kiểu dữ liệu được khai báo khi khởi tạo đối tượng Gson. Nếu không thể đọc tệp JSON, phương thức sẽ in thông tin về lỗi ra đầu ra và trả về giá trị null. Hãy đảm bảo đóng tệp FileReader sau khi hoàn tất việc đọc để giải phóng tài nguyên.
	 */
	public List<T> loadJson(String path) {
		List<T> list = null;
		try{
			fileReader = new FileReader(path);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			list = gson.fromJson(fileReader, TYPE);
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (fileReader != null) {
					fileReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}