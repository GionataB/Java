package mathematics;

public class Arrays {

	public static <T> String printVals(T[] arr){
		 String repr = "{ ";
		 for(T t: arr){
			 repr += t.toString() + ", ";
		 }
		 repr += "}";
		 return repr;
	}
}
