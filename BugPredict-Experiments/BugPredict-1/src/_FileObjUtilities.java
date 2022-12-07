import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class _FileObjUtilities implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4805944341314777428L;

	static void writeObjectTo(String Path, Object node, long position) {
		try {
			RandomAccessFile raf = new RandomAccessFile(Path, "rw");
			raf.seek(position);
			FileOutputStream fout = new FileOutputStream(raf.getFD());
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(node);
			oos.close();
			fout.close();
			raf.close();
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	static Object readObjectFrom(String Path, long position) {
		Object node = null;
		try {
			RandomAccessFile raf = new RandomAccessFile(Path, "r");
			raf.seek(position);
			FileInputStream fin = new FileInputStream(raf.getFD());
			fin.getChannel().position(position);
			ObjectInputStream ois = new ObjectInputStream(fin);
			node = (Object) ois.readObject();
			ois.close();
			fin.close();
			raf.close();
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return node;
	}

	static long length(String Path) {
		try {
			RandomAccessFile raf = new RandomAccessFile(Path, "rw");
			long l = raf.length();
			raf.close();
			return l;
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return -1;
	}
}
